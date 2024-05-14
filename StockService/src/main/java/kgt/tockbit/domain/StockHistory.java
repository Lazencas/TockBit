package kgt.tockbit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String stockCode;

    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    //종가
    private BigDecimal close;

    //거래량
    private Long tradeVolumes;

    //시가총액
//    private Long capitalization;


    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal opening) {
        this.open = opening;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal previousClose) {
        this.close = previousClose;
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
