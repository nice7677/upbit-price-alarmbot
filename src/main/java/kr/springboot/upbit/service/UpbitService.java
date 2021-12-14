package kr.springboot.upbit.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.springboot.upbit.coinlist.UpbitCoinList;
import kr.springboot.upbit.commons.Commons;
import kr.springboot.upbit.model.CheckListModel;
import kr.springboot.upbit.repository.CheckListRepository;
import kr.springboot.upbit.response.UpbitCoinListModel;
import kr.springboot.upbit.response.UpbitResponseModel;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UpbitService {

    @Autowired
    CheckListRepository checkListRepository;

    @Autowired
    SeleniumService seleniumService;

    @Autowired
    ResourceLoader resourceLoader;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void coinCheck() throws URISyntaxException, ExecutionException, JsonProcessingException {

        UpbitCoinList upbitCoinList = new UpbitCoinList();
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(LocalDateTime.now().minusMinutes(1).plusHours(1) + " 개쏘는거 찾는중..");

        for (int i = 0; i < upbitCoinList.UPBIT_COIN_LIST.size(); i++) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Accept", "application/json");
            headerMap.put("Authorization", upbitRequestHeaderToken());
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            String result = null;
            try {
                result = client.sendAsync(
                        HttpRequest.newBuilder(
                                new URI(getAddress(upbitCoinList.UPBIT_COIN_LIST.get(i).toString()))).GET().setHeader("Accept", "application/json").build(),  //GET방식 요청
                        HttpResponse.BodyHandlers.ofString()
                ).thenApply(HttpResponse::body)
                        .get();
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<UpbitResponseModel> upbitResponseModelList = Arrays.asList(objectMapper.readValue(result, UpbitResponseModel[].class));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime koreaTime = LocalDateTime.parse(upbitResponseModelList.get(1).getCandleDateTimeUtc(), formatter);

            Double gapPrice = upbitResponseModelList.get(1).getTradePrice() - upbitResponseModelList.get(1).getOpeningPrice();
            if (gapPrice > 0) {
                Double gapPriceDouble = gapPrice;
                Double openingPriceDouble = Double.valueOf(upbitResponseModelList.get(1).getOpeningPrice());
                Double gapPercent = (double) Math.round(gapPriceDouble / openingPriceDouble * 100 * 100) / 100;
                if (upbitResponseModelList.get(1).getOpeningPrice() < 35) {
                    if (gapPercent > 1.5) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 100) {
                    if (gapPercent > 2) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 200) {
                    if (gapPercent > 1.2) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 500) {
                    if (gapPercent > 1) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 1000) {
                    if (gapPercent > 1.3) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 3000) {
                    if (gapPercent > 0.9) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 7000) {
                    if (gapPercent > 0.8) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 10000) {
                    if (gapPercent > 0.7) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 50000) {
                    if (gapPercent > 0.6) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 100000) {
                    if (gapPercent > 0.5) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 1000000) {
                    if (gapPercent > 0.6) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                } else if (upbitResponseModelList.get(1).getOpeningPrice() < 1000000000) {
                    if (gapPercent > 0.3) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
                }
                if (upbitResponseModelList.get(1).getMarket().equals("KRW-DOGE")) {
                    if (gapPercent > 0.6) {
                        sendMsg(upbitResponseModelList.get(1).getMarket(), upbitResponseModelList.get(1), koreaTime);
                        //msg
                    }
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
    void sendMsg(String name, UpbitResponseModel upbitResponseModel, LocalDateTime koreaTime) {

        String marketName = name;
        if (lastSendMsgTimeChecker(name)) {

            UpbitCoinList upbitCoinList = new UpbitCoinList();

            ObjectMapper objectMapper = new ObjectMapper();
            List<UpbitCoinListModel> upbitCoinListModelList = null;

            try {
                upbitCoinListModelList = Arrays.asList(objectMapper.readValue(upbitCoinList.coinListJson, UpbitCoinListModel[].class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < upbitCoinListModelList.size(); i++) {
                if (upbitCoinListModelList.get(i).getMarket().equals(name)) {
                    name = upbitCoinListModelList.get(i).getKoreanName();
                }
            }

            telegramSendMsg(marketName, upbitResponseModel, name);

//            HttpRequest request = HttpRequest.newBuilder()
//                    .GET()
//                    .uri(URI.create("https://api.telegram.org//sendMessage?chat_id=-&text=" + name + "%20개쏘냐?"))
//                    .build();
//            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            CheckListModel checkListModel = CheckListModel.builder()
                    .coinName(name)
                    .marketName(marketName)
                    .candleDateTimeUtc(koreaTime)
                    .createdAt(LocalDateTime.now())
                    .openingPrice(upbitResponseModel.getOpeningPrice())
                    .tradePrice(upbitResponseModel.getTradePrice())
                    .build();

            checkListRepository.save(checkListModel);
            log.info("개쏘는 " + name + " 코인");
        }

    }

    @Async
    void telegramSendMsg(String marketName, UpbitResponseModel upbitResponseModel, String name) {
        String photoName = seleniumService.seleniumRunning(marketName);
        Commons commons = new Commons();
        File file = new File(commons.imagePath + photoName);
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
//        HttpPost requestPost = new HttpPost("https://api.telegram.org//sendPhoto?chat_id=&caption=" + name + priceGapString + "%20개쏘냐?");
        // 그룹
        HttpPost requestPost = new HttpPost("https://api.telegram.org//sendPhoto?chat_id=-&caption=" + name + priceGapString + "%20개쏘냐?");
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

    private String upbitRequestHeaderToken() {
        String accessKey = "";
        String secretKey = "";

        String queryString = "query string 생성";

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            md.update(queryString.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        return authenticationToken;
    }

}
