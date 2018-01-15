package ct.leshchenko.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by Vitalii Leshchenko on 10.01.2018.
 */
public class CurrencyRate {

    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("base_ccy")
    private String baseCurrency;

    private double buy;
    private double sale;

    public CurrencyRate() {

    }

    public CurrencyRate(String currency, String baseCurrency, double buy, double sale) {
        this.currency = currency;
        this.baseCurrency = baseCurrency;
        this.buy = buy;
        this.sale = sale;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return Double.compare(that.buy, buy) == 0 &&
                Double.compare(that.sale, sale) == 0 &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(baseCurrency, that.baseCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, baseCurrency, buy, sale);
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "currency='" + currency + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}
