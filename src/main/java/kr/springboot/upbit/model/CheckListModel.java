package kr.springboot.upbit.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "t_check_list")
public class CheckListModel {

    /**
     * 확인 끝난 코인들 등록 텀은 15분
     */

    @Id
    @GeneratedValue
    private Long idx;

    private String coinName;

    private String marketName;

    private Double openingPrice;

    private Double tradePrice;

    private Double gapPercent;

    private Boolean alarmStatus; // 메세지 전송 상태

    private LocalDateTime candleDateTimeUtc;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public CheckListModel(Long idx, String coinName, String marketName, Double openingPrice, Double tradePrice, Double gapPercent, Boolean alarmStatus, LocalDateTime candleDateTimeUtc, LocalDateTime createdAt) {
        this.idx = idx;
        this.coinName = coinName;
        this.marketName = marketName;
        this.openingPrice = openingPrice;
        this.tradePrice = tradePrice;
        this.gapPercent = gapPercent;
        this.alarmStatus = alarmStatus;
        this.candleDateTimeUtc = candleDateTimeUtc;
        this.createdAt = createdAt;
    }
}
