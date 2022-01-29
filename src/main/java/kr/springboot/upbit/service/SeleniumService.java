package kr.springboot.upbit.service;

import kr.springboot.upbit.commons.Commons;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeleniumService {

    public String seleniumRunning(String coinName) {

        String[] coinNameSplit = coinName.split("-");
        Path path = Paths.get("/usr/local/bin/chromedriver");  // 현재 package의

        System.setProperty("webdriver.chrome.driver", path.toString());

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");            // 전체화면으로 실행
        options.addArguments("--headless");            // 전체화면으로 실행
        options.addArguments("--no-sandbox");            // 전체화면으로 실행
        options.addArguments("--disable-dev-shm-usage");            // 전체화면으로 실행
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함

        // WebDriver 객체 생성
        ChromeDriver driver = new ChromeDriver(options);

        // 탭 목록 가져오기
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        // 첫번째 탭으로 전환
        driver.switchTo().window(tabs.get(0));

        // 웹페이지 요청
        driver.get("https://kr.tradingview.com/chart/?symbol=UPBIT%3A" + coinNameSplit[1] + "KRW");

        try {
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.cssSelector("div[data-name^='base']")).click();
        driver.findElement(By.cssSelector("div[id^='header-toolbar-intervals']")).click();
        driver.findElement(By.cssSelector("div[data-name^='menu-inner'] > div > div:nth-child(8)")).click();

        WebElement elm = new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("canvas")));

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        LocalDateTime now = LocalDateTime.now();

        try {
            FileUtils.copyFile(scrFile, new File(Commons.IMAGE_PATH + now.toString() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.close();
        driver.quit();

        return now.toString() + ".png";
    }

}
