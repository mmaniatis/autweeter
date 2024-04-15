package com.michael.autweeter.service.web;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@AllArgsConstructor
public abstract class WebCrawler {

    WebDriver webDriver;
    String url;
    BodyTextFetcher bodyTextFetcher;
    /**
     * Filter the attributes of the given URL based on a predicate supplied. It will vary per URL.
     * todo: Consider a map that has url -> predicate?
    * */
    public abstract Predicate<String> getAttributeFilter();

    public List<String> fetch() throws Exception {
        List<String> result = new ArrayList<>();
        webDriver.get(url);
        List<WebElement> links = getAllElementsByTag("a");

        List<String> attributesToSearch = links.stream()
                .map(link -> link.getAttribute("href"))
                .filter(getAttributeFilter()).toList();

        IntStream.range(0, 6).forEach(i -> { //todo: try catch in each
            System.out.printf("Currently fetching %s...\n", attributesToSearch.get(i));
            result.add(bodyTextFetcher.getTextFromAttribute(webDriver, attributesToSearch.get(i)));
            System.out.printf("Completed fetching %s...\n", attributesToSearch.get(i));
            System.out.printf("%.2f percent complete..\n", (((double) i + 1) / (double) attributesToSearch.size()) * 100.00);
        });

        webDriver.close();

        return result;
    }

    private List<WebElement> getAllElementsByTag(String tag) {
        return webDriver.findElements(By.tagName(tag));
    }


}
