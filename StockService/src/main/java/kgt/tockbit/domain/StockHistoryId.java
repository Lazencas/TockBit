package kgt.tockbit.domain;
import java.io.Serializable;
import java.util.Objects;

public class StockHistoryId implements Serializable {
    private String stockName;
    private String date;

    public StockHistoryId() {
    }

    public StockHistoryId(String stockName, String date) {
        this.stockName = stockName;
        this.date = date;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockHistoryId)) return false;
        StockHistoryId that = (StockHistoryId) o;
        return Objects.equals(getStockName(), that.getStockName()) &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStockName(), getDate());
    }
}
