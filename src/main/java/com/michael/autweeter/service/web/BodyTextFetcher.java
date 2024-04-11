package com.michael.autweeter.service.web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BodyTextFetcher {

    public String getTextFromAttribute(WebDriver webDriver, String attribute) {
        String bodyText = "";

        ((JavascriptExecutor) webDriver).executeScript("window.open('" + attribute + "');");
        String originalHandle = webDriver.getWindowHandle();
        for (String handle : webDriver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                webDriver.switchTo().window(handle);
                bodyText = webDriver.findElement(By.tagName("body")).getText();
                webDriver.close();
                webDriver.switchTo().window(originalHandle);
            }
        }
        return bodyText;
    }
}
