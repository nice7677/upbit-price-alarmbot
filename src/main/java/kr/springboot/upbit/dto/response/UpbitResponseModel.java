package kr.springboot.upbit.dto.response;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "market",
        "candle_date_time_utc",
        "candle_date_time_kst",
        "opening_price",
        "high_price",
        "low_price",
        "trade_price",
        "timestamp",
        "candle_acc_trade_price",
        "candle_acc_trade_volume",
        "unit"
})
@Generated("jsonschema2pojo")
public class UpbitResponseModel {

    @JsonProperty("market")
    private String market;
    @JsonProperty("candle_date_time_utc")
    private String candleDateTimeUtc;
    @JsonProperty("candle_date_time_kst")
    private String candleDateTimeKst;
    @JsonProperty("opening_price")
    private Double openingPrice;
    @JsonProperty("high_price")
    private Double highPrice;
    @JsonProperty("low_price")
    private Double lowPrice;
    @JsonProperty("trade_price")
    private Double tradePrice;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("candle_acc_trade_price")
    private Double candleAccTradePrice;
    @JsonProperty("candle_acc_trade_volume")
    private Double candleAccTradeVolume;
    @JsonProperty("unit")
    private Integer unit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("market")
    public String getMarket() {
        return market;
    }

    @JsonProperty("market")
    public void setMarket(String market) {
        this.market = market;
    }

    @JsonProperty("candle_date_time_utc")
    public String getCandleDateTimeUtc() {
        return candleDateTimeUtc;
    }

    @JsonProperty("candle_date_time_utc")
    public void setCandleDateTimeUtc(String candleDateTimeUtc) {
        this.candleDateTimeUtc = candleDateTimeUtc;
    }

    @JsonProperty("candle_date_time_kst")
    public String getCandleDateTimeKst() {
        return candleDateTimeKst;
    }

    @JsonProperty("candle_date_time_kst")
    public void setCandleDateTimeKst(String candleDateTimeKst) {
        this.candleDateTimeKst = candleDateTimeKst;
    }

    @JsonProperty("opening_price")
    public Double getOpeningPrice() {
        return openingPrice;
    }

    @JsonProperty("opening_price")
    public void setOpeningPrice(Double openingPrice) {
        this.openingPrice = openingPrice;
    }

    @JsonProperty("high_price")
    public Double getHighPrice() {
        return highPrice;
    }

    @JsonProperty("high_price")
    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    @JsonProperty("low_price")
    public Double getLowPrice() {
        return lowPrice;
    }

    @JsonProperty("low_price")
    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    @JsonProperty("trade_price")
    public Double getTradePrice() {
        return tradePrice;
    }

    @JsonProperty("trade_price")
    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("candle_acc_trade_price")
    public Double getCandleAccTradePrice() {
        return candleAccTradePrice;
    }

    @JsonProperty("candle_acc_trade_price")
    public void setCandleAccTradePrice(Double candleAccTradePrice) {
        this.candleAccTradePrice = candleAccTradePrice;
    }

    @JsonProperty("candle_acc_trade_volume")
    public Double getCandleAccTradeVolume() {
        return candleAccTradeVolume;
    }

    @JsonProperty("candle_acc_trade_volume")
    public void setCandleAccTradeVolume(Double candleAccTradeVolume) {
        this.candleAccTradeVolume = candleAccTradeVolume;
    }

    @JsonProperty("unit")
    public Integer getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}