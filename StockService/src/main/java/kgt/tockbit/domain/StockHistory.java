package kgt.tockbit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(StockHistoryId.class)
public class StockHistory {


    @Id
    private String stockName;
    @Id
    private String date;
    private String stockCode;

    private String open;
    private String high;
    private String low;
    //종가
    private String close;

    //거래량
    private Long tradeVolumes;

    //시가총액
//    private Long capitalization;


    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public Long getTradeVolumes() {
        return tradeVolumes;
    }

    public void setTradeVolumes(Long tradeVolumes) {
        this.tradeVolumes = tradeVolumes;
    }

    public enum StockType{
        KOSPI,

        KOSDAQ
    }

}
