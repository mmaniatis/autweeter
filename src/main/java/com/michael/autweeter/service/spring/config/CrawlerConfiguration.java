package com.michael.autweeter.service.spring.config;

import com.michael.autweeter.service.web.BodyTextFetcher;
import com.michael.autweeter.service.web.WebCrawler;
import com.michael.autweeter.service.web.golf.PgaCrawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlerConfiguration {

    @Bean
    WebDriver chromeDriver() {
        var options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver",
                getClass().getClassLoader().getResource("chromedriver").getPath());
        options.addArguments("--window-size=1920x1080");
        options.addArguments("--incognito");
        options.addArguments("--headless");

        return new ChromeDriver(options);
    }

    @Bean
    WebCrawler pgaCrawler(WebDriver chromeDriver) {
        return new PgaCrawler(chromeDriver, "https://www.pgatour.com/news", new BodyTextFetcher());
    }
}
