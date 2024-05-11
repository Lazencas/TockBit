package kgt.tockbit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stocks {
    private String stockType;
    private String stockEndType;
    @Id
    private String itemCode;
    private String reutersCode;

    private String stockName;
    private String sosok;
    private String closePrice;
    private String compareToPreviousClosePrice;
    private String fluctuationsRatio;
    private String accumulatedTradingVolume;
    private String accumulatedTradingValue;
    private String localTradedAt;
    private String marketValue;
    private String nav;
    private String threeMonthEarningRate;
    private String marketStatus;
    private String endUrl;

    // getters and setters

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockEndType() {
        return stockEndType;
    }

    public void setStockEndType(String stockEndType) {
        this.stockEndType = stockEndType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getReutersCode() {
        return reutersCode;
    }

    public void setReutersCode(String reutersCode) {
        this.reutersCode = reutersCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getSosok() {
        return sosok;
    }

    public void setSosok(String sosok) {
        this.sosok = sosok;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getCompareToPreviousClosePrice() {
        return compareToPreviousClosePrice;
    }

    public void setCompareToPreviousClosePrice(String compareToPreviousClosePrice) {
        this.compareToPreviousClosePrice = compareToPreviousClosePrice;
    }

    public String getFluctuationsRatio() {
        return fluctuationsRatio;
    }

    public void setFluctuationsRatio(String fluctuationsRatio) {
        this.fluctuationsRatio = fluctuationsRatio;
    }

    public String getAccumulatedTradingVolume() {
        return accumulatedTradingVolume;
    }

    public void setAccumulatedTradingVolume(String accumulatedTradingVolume) {
        this.accumulatedTradingVolume = accumulatedTradingVolume;
    }

    public String getAccumulatedTradingValue() {
        return accumulatedTradingValue;
    }

    public void setAccumulatedTradingValue(String accumulatedTradingValue) {
        this.accumulatedTradingValue = accumulatedTradingValue;
    }

    public String getLocalTradedAt() {
        return localTradedAt;
    }

    public void setLocalTradedAt(String localTradedAt) {
        this.localTradedAt = localTradedAt;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getThreeMonthEarningRate() {
        return threeMonthEarningRate;
    }

    public void setThreeMonthEarningRate(String threeMonthEarningRate) {
        this.threeMonthEarningRate = threeMonthEarningRate;
    }

    public String getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(String marketStatus) {
        this.marketStatus = marketStatus;
    }

    public String getEndUrl() {
        return endUrl;
    }

    public void setEndUrl(String endUrl) {
        this.endUrl = endUrl;
    }
}