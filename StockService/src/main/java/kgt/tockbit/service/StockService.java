package kgt.tockbit.service;

import kgt.tockbit.domain.StockHistory;
import kgt.tockbit.domain.Stocks;
import kgt.tockbit.dto.StockResponse;
import kgt.tockbit.repository.StockHistoryRepository;
import kgt.tockbit.repository.StocksRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import reactor.core.publisher.Mono;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

@Service
public class StockService {

    private final WebClient webClient;
    private final StocksRepository stockRepository;

    private final StockHistoryRepository stockHistoryRepository;

    public StockService(WebClient.Builder webClientBuilder, StocksRepository stockRepository, StockHistoryRepository stockHistoryRepository) {
        this.webClient = webClientBuilder.baseUrl("https://m.stock.naver.com/api/stocks/marketValue").build();
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }
    public void fetchAllStocks(String market) {
        int page = 1;
        int pageSize = 100;

        while (true) {
            String url = String.format("/%s?page=%d&pageSize=%d", market, page, pageSize);
            Mono<StockResponse> stockResponseMono = this.webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(StockResponse.class);

            StockResponse stockResponse = stockResponseMono.block();
            if (stockResponse.getStocks().isEmpty()) {
                break; // No more data, exit the loop
            }

            // Save the data to the database
            stockRepository.saveAll(stockResponse.getStocks());

            page++;
        }
    }

// 5년치 주식 데이터를 가져와서 데이터베이스에 저장하는 메소드
    public void fetchAndStoreFiveYearsData(){
        //데이터베이스에서 모든 주식데이터를 가져옴
        List<Stocks> stocks = stockRepository.findAll();
        int l  = 0;
        //각 주식에 대해 출력해보기
        for (Stocks stock : stocks){
            System.out.println(l+"번째 "+stock.getItemCode()+"코드"+stock.getStockName());
            l++;
            //5년치 일봉 주식 데이터를 가져오는 API의 URL
            String dayfiveYearsDataUrl = "https://fchart.stock.naver.com/sise.nhn?symbol=" + stock.getItemCode() + "&timeframe=day&count=200&requestType=0";

            //webClient를 사용하여 API에서 5년치 주식데이터를 가져옴
            String fiveYearsData = webClient.get().uri(dayfiveYearsDataUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            NodeList nodeList = null;
            //가져온 XML형식의 데이터를 파싱하여, 필요한 정보 추출
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(fiveYearsData));
                Document doc = builder.parse(is);

                nodeList = doc.getElementsByTagName("item");
            }catch (ParserConfigurationException | SAXException | IOException e){
                System.out.println("오류 발생: " + e.getMessage());
            }

            if(nodeList == null){
                continue;
            }

            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    String data = element.getAttribute("data");
                    String[] values = data.split("\\|");

        //가져온 데이터를 사용하여 StockHistory객체를 생성하고, 객체의 속성 설정
                    StockHistory stockHistory = new StockHistory();
                    stockHistory.setStockCode(stock.getItemCode());
                    stockHistory.setDate(values[0]);
                    stockHistory.setOpen(new BigDecimal(values[1]));
                    stockHistory.setHigh(new BigDecimal(values[2]));
                    stockHistory.setLow(new BigDecimal(values[3]));
                    stockHistory.setClose(new BigDecimal(values[4]));
                    stockHistory.setTradeVolumes(Long.parseLong(values[5]));

                    stockHistoryRepository.save(stockHistory);

                }

            }

        }


    }




//    public Flux<Stock> updateStockData(String symbol) {
//        //WebClient를 사용하여 주식 데이터를 가져옴
//        System.out.println("업데이트스탁데이터 체크");
//        return this.webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/sise.nhn")
//                        .queryParam("symbol", symbol)
//                        .queryParam("timeframe", "day")
//                        .queryParam("count", "10")
//                        .queryParam("requestType", "0")
//                        .build())
//                .retrieve()
//                .bodyToMono(String.class)
//                .flatMapMany(this::parseAndSaveStockData);//가져온 데이터를 파싱하고 저장
//    }

    //XML 데이터를 파싱하고 Stock객체를 생성하여 저장하는 메소드
//    private Flux<Stock> parseAndSaveStockData(String xmlData) {
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));
//
//            NodeList items = doc.getElementsByTagName("item");
//            return Flux.range(0, items.getLength())
//                    .flatMap(i -> {
//                        Element item = (Element) items.item(i);
//                        String data = item.getAttribute("data");
//                        String[] values = data.split("\\|");
//
//                        String date = values[0];
//                        BigDecimal open = new BigDecimal(values[1]);
//                        BigDecimal high = new BigDecimal(values[2]);
//                        BigDecimal low = new BigDecimal(values[3]);
//                        BigDecimal close = new BigDecimal(values[4]);
//                        Long volume = Long.parseLong(values[5]);
//
//                        Stock stock = new Stock();
//                        stock.setDate(date);
//                        stock.setOpening(open);
//                        stock.setHigh(high);
//                        stock.setLow(low);
//                        stock.setClosePrice(close);
//                        stock.setTradeVolumes(volume);
//
//                        return Mono.just(this.stockRepository.save(stock));
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Flux.error(e);
//        }
//    }
//

    public String getStockData() {
        String url = "https://fchart.stock.naver.com/sise.nhn?symbol=005930&timeframe=day&count=10&requestType=0";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }





}
