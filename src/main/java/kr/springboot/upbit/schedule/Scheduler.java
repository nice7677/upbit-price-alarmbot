package kr.springboot.upbit.schedule;


import com.fasterxml.jackson.core.JsonProcessingException;
import kr.springboot.upbit.service.UpbitService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
public class Scheduler {

    private final UpbitService upbitService;

    @Scheduled(cron = "1 * * * * *")
    public void scheduleFixedRateTask() throws URISyntaxException, ExecutionException, JsonProcessingException {

        upbitService.coinCheck();

    }



}