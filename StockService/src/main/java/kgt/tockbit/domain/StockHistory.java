package kgt.tockbit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class StockHistory {

    @Id
    private Long id;

    private String date;

    @Enumerated(EnumType.STRING)
    private StockType type;

//    private String stockCode;

//    private BigDecimal current;
    //종목코드
    private String itemCode;

    private String stockName;
    private BigDecimal opening;
    private BigDecimal high;
    private BigDecimal low;
    //종가
    private BigDecimal closePrice;

    //거래량
    private Long tradeVolumes;

    //시가총액
//    private Long capitalization;


    public StockType getType() {
        return type;
    }

    public void setType(StockType type) {
        this.type = type;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
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

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal previousClose) {
        this.closePrice = previousClose;
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
