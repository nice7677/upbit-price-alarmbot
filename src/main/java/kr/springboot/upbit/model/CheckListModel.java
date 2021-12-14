package kr.springboot.upbit.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Data
@ToString
@AllArgsConstructor
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

}
