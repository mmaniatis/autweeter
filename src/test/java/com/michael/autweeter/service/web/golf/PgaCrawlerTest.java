package com.michael.autweeter.service.web.golf;

import com.michael.autweeter.service.web.BodyTextFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PgaCrawlerTest {

    @Test
    public void happyPath() throws Exception {
        int EXPECTED_ARTICLES = 1;
        String EXPECTED_ARTICLE = "This is2 a sample body text!";
        var driver = mock(WebDriver.class);
        var webElement = mock(WebElement.class);
        var bodyTextFetcher = mock(BodyTextFetcher.class);

        Mockito.doNothing().when(driver).get(any());
        when(driver.findElements(any())).thenReturn(Collections.singletonList(webElement));
        when(webElement.getAttribute(any())).thenReturn("https://test.com/article/test-article");
        when(bodyTextFetcher.getTextFromAttribute(any(), any())).thenReturn(EXPECTED_ARTICLE);

        var pgaCrawler = new PgaCrawler(driver, "https://test.com/news", bodyTextFetcher);
        List<String> articles = pgaCrawler.fetch();

        Assertions.assertEquals(EXPECTED_ARTICLES, articles.size());
        Assertions.assertEquals(EXPECTED_ARTICLE, articles.get(0));
    }
}
