package kr.springboot.upbit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.springboot.upbit.coinlist.UpbitCoinList;
import kr.springboot.upbit.commons.Commons;
import kr.springboot.upbit.model.CheckListModel;
import kr.springboot.upbit.repository.CheckListRepository;
import kr.springboot.upbit.dto.response.UpbitCoinListDto;
import kr.springboot.upbit.dto.response.UpbitResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Log4j2
@Service
public class UpbitService {

    private final CheckListRepository checkListRepository;

    private final SeleniumService seleniumService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void coinCheck() throws URISyntaxException, ExecutionException, JsonProcessingException {

        log.info(LocalDateTime.now().minusMinutes(1).plusHours(1) + " 진우가 원하는 코인 찾는중..");

        for (int i = 0; i < UpbitCoinList.UPBIT_COIN_LIST.size(); i++) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Accept", "application/json");
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            String result = null;
            try {
                result = client.sendAsync(
                        HttpRequest.newBuilder(
                                new URI(getAddress(UpbitCoinList.UPBIT_COIN_LIST.get(i).toString()))).GET().setHeader("Accept", "application/json").build(),  //GET방식 요청
                        HttpResponse.BodyHandlers.ofString()
                ).thenApply(HttpResponse::body)
                        .get();
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<UpbitResponseDto> upbitResponseModelList = Arrays.asList(objectMapper.readValue(result, UpbitResponseDto[].class));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime koreaTime = LocalDateTime.parse(upbitResponseModelList.get(1).getCandleDateTimeUtc(), formatter);

            double gapPrice = upbitResponseModelList.get(1).getTradePrice() - upbitResponseModelList.get(1).getOpeningPrice();
            if (gapPrice > 0) {
                double gapPriceDouble = gapPrice;
                double openingPriceDouble = upbitResponseModelList.get(1).getOpeningPrice();
                double gapPercent = (double) Math.round(gapPriceDouble / openingPriceDouble * 100 * 100) / 100;
                if (upbitResponseModelList.get(1).getOpeningPrice() < 35) {
                    if (gapPercent > 1.5) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 100) {
                    if (gapPercent > 2) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 200) {
                    if (gapPercent > 1.2) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 500) {
                    if (gapPercent > 1) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 1000) {
                    if (gapPercent > 1.3) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 3000) {
                    if (gapPercent > 0.9) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 7000) {
                    if (gapPercent > 0.8) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 10000) {
                    if (gapPercent > 0.7) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 50000) {
                    if (gapPercent > 0.6) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 100000) {
                    if (gapPercent > 0.5) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 1000000) {
                    if (gapPercent > 0.6) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 1000000000) {
                    if (gapPercent > 0.3) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                    }
                }
                if (upbitResponseModelList.get(1).getMarket().equals("KRW-DOGE")) {
//                    if (gapPercent > 0.6) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
//                    }
                }
            }
        }
    }

    private Boolean lastSendMsgTimeChecker(String name) {

        CheckListModel checkListModel = checkListRepository.findFirstByMarketNameOrderByIdxDesc(name);
        if (checkListModel == null) {
            return true; // 보내기
        }

        Duration duration = Duration.between(checkListModel.getCreatedAt(), LocalDateTime.now());

        if (duration.getSeconds() < 630) {
            return false; // 보낼수 없음
        }

        return true; // 보낼수 있음

    }

    @Async
    void sendMsg(String name, UpbitResponseDto upbitResponseModel, LocalDateTime koreaTime) {

        String marketName = name;
        if (lastSendMsgTimeChecker(name)) {

            List<UpbitCoinListDto> upbitCoinListModelList = null;

            try {
                upbitCoinListModelList = Arrays.asList(objectMapper.readValue(UpbitCoinList.COIN_LIST_JSON, UpbitCoinListDto[].class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < upbitCoinListModelList.size(); i++) {
                if (upbitCoinListModelList.get(i).getMarket().equals(name)) {
                    name = upbitCoinListModelList.get(i).getKoreanName();
                }
            }

            telegramSendMsg(marketName, upbitResponseModel, name);

            CheckListModel checkListModel = CheckListModel.builder()
                    .coinName(name)
                    .marketName(marketName)
                    .candleDateTimeUtc(koreaTime)
                    .createdAt(LocalDateTime.now())
                    .openingPrice(upbitResponseModel.getOpeningPrice())
                    .tradePrice(upbitResponseModel.getTradePrice())
                    .build();

            checkListRepository.save(checkListModel);
            log.info("진우가 찾는 " + name + " 코인");
        }

    }

    @Async
    void telegramSendMsg(String marketName, UpbitResponseDto upbitResponseModel, String name) {
        String photoName = seleniumService.seleniumRunning(marketName);
        File file = new File(Commons.IMAGE_PATH + photoName);
        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("photo", fileBody);
        HttpEntity entity = builder.build();

        String priceGapString = "(" + upbitResponseModel.getOpeningPrice() + "원 -> " + upbitResponseModel.getTradePrice() + "원)";
        try {
            priceGapString = URLEncoder.encode(priceGapString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 개인
        HttpPost requestPost = new HttpPost("https://api.telegram.org//sendPhoto?chat_id=&caption=" + name + priceGapString + "%20진우가%20찾는%20코인?");
        requestPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            client.execute(requestPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAddress(String name) {
        String address = "https://api.upbit.com/v1/candles/minutes/1?market=" + name + "&count=2";
        return address;
    }

}
