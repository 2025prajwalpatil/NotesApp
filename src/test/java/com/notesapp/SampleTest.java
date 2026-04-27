package com.notesapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.Test;

public class SampleTest {

    @Test
    public void testGoogle() {

        System.out.println("Selenium Test Running");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.google.com");

        System.out.println("Title: " + driver.getTitle());

        driver.quit();
    }
}