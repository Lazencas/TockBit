package kgt.tockbit.service;

import kgt.tockbit.domain.Stock;
import kgt.tockbit.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;

@Service
public class StockService {

    private final WebClient webClient;
    private final StockRepository stockRepository;


    public StockService(WebClient.Builder webClientBuilder, StockRepository stockRepository) {
        this.webClient = webClientBuilder.baseUrl("https://fchart.stock.naver.com").build();
        this.stockRepository = stockRepository;
    }

    public Flux<Stock> updateStockData(String symbol) {
        //WebClient를 사용하여 주식 데이터를 가져옴
        System.out.println("업데이트스탁데이터 체크");
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/sise.nhn")
                        .queryParam("symbol", symbol)
                        .queryParam("timeframe", "day")
                        .queryParam("count", "10")
                        .queryParam("requestType", "0")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMapMany(this::parseAndSaveStockData);//가져온 데이터를 파싱하고 저장
    }

    //XML 데이터를 파싱하고 Stock객체를 생성하여 저장하는 메소드
    private Flux<Stock> parseAndSaveStockData(String xmlData) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList items = doc.getElementsByTagName("item");
            return Flux.range(0, items.getLength())
                    .flatMap(i -> {
                        Element item = (Element) items.item(i);
                        String data = item.getAttribute("data");
                        String[] values = data.split("\\|");

                        String date = values[0];
                        BigDecimal open = new BigDecimal(values[1]);
                        BigDecimal high = new BigDecimal(values[2]);
                        BigDecimal low = new BigDecimal(values[3]);
                        BigDecimal close = new BigDecimal(values[4]);
                        Long volume = Long.parseLong(values[5]);

                        Stock stock = new Stock();
                        stock.setDate(date);
                        stock.setOpening(open);
                        stock.setHigh(high);
                        stock.setLow(low);
                        stock.setPreviousClose(close);
                        stock.setTradeVolumes(volume);

                        return Mono.just(this.stockRepository.save(stock));
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.error(e);
        }
    }


    public String getStockData() {
        String url = "https://fchart.stock.naver.com/sise.nhn?symbol=005930&timeframe=day&count=10&requestType=0";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
