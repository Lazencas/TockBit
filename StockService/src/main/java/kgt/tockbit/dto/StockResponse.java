package kgt.tockbit.dto;

import kgt.tockbit.domain.Stocks;

import java.util.List;

public class StockResponse {
    private List<Stocks> stocks;
    private String stockListSortType;
    private String stockListCategoryType;

    public String getStockListSortType() {
        return stockListSortType;
    }

    public void setStockListSortType(String stockListSortType) {
        this.stockListSortType = stockListSortType;
    }

    public String getStockListCategoryType() {
        return stockListCategoryType;
    }

    public void setStockListCategoryType(String stockListCategoryType) {
        this.stockListCategoryType = stockListCategoryType;
    }

    // getters and setters

    public List<Stocks> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stocks> stocks) {
        this.stocks = stocks;
    }

}




