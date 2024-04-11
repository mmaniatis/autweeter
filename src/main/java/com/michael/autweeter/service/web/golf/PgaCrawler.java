package com.michael.autweeter.service.web.golf;

import com.michael.autweeter.service.web.BodyTextFetcher;
import com.michael.autweeter.service.web.WebCrawler;
import org.openqa.selenium.WebDriver;

import java.util.function.Predicate;

public class PgaCrawler extends WebCrawler {
    public PgaCrawler(WebDriver webDriver,
                      String url,
                      BodyTextFetcher bodyTextFetcher) {
        super(webDriver, url, bodyTextFetcher);
    }

    public Predicate<String> getAttributeFilter() {
        return ele -> (ele != null && ele.contains("/article/"));
    }
}
