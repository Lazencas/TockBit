package kgt.tockbit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Stock {

    @Id
    private Long id;

    private String date;

//    private String stockCode;

//    private BigDecimal current;
    private BigDecimal opening;
    private BigDecimal high;
    private BigDecimal low;
    //종가
    private BigDecimal previousClose;

    //거래량
    private Long tradeVolumes;

    //시가총액
//    private Long capitalization;


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


    public BigDecimal getOpening() {
        return opening;
    }

    public void setOpening(BigDecimal opening) {
        this.opening = opening;
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

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public Long getTradeVolumes() {
        return tradeVolumes;
    }

    public void setTradeVolumes(Long tradeVolumes) {
        this.tradeVolumes = tradeVolumes;
    }

}
