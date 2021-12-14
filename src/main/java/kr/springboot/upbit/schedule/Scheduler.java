package kr.springboot.upbit.schedule;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.springboot.upbit.coinlist.UpbitCoinList;
import kr.springboot.upbit.response.UpbitCoinListModel;
import kr.springboot.upbit.response.UpbitResponseModel;
import kr.springboot.upbit.service.UpbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class Scheduler {

    @Autowired
    UpbitService upbitService;

    @Scheduled(cron = "1 * * * * *")
    public void scheduleFixedRateTask() throws URISyntaxException, ExecutionException, InterruptedException, JsonProcessingException {

        upbitService.coinCheck();

    }



}