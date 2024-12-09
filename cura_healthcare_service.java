package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.bouncycastle.asn1.crmf.SinglePubInfo.web;

public class cura_healthcare_service {
    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver(new ChromeOptions());
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() throws InterruptedException, IOException {
        String baseUrl="https://katalon-demo-cura.herokuapp.com/";
        driver.get(baseUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Clicking on Make appointment button
        driver.findElement(By.id("btn-make-appointment")).click();


        //Login
        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.id("btn-login")).click();

        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        //WebDriverWait wait=new WebDriverWait(driver,10);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='combo_facility']")));

        //Make Appointment page
        Select s=new Select(driver.findElement(By.name("facility")));
        s.selectByVisibleText("Seoul CURA Healthcare Center");

        List<WebElement> l=s.getOptions();
        int length=l.size();

        for (int i = 0; i < length; i++) {
            String options=l.get(i).getText();
            System.out.println(options);
        }

        Thread.sleep(10);
        System.out.println(s);

        driver.findElement(By.id("chk_hospotal_readmission")).click();
        driver.findElement(By.id("radio_program_medicare")).click();
        WebElement date=driver.findElement(By.id("txt_visit_date"));
        date.clear();
        date.sendKeys("09/12/2024");
        driver.findElement(By.id("txt_comment")).sendKeys("I have fever");
        driver.findElement(By.id("btn-book-appointment")).click();

        Thread.sleep(20);

        TakesScreenshot scrshot=(TakesScreenshot)driver;
        File source=scrshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source,new File("./SeleniumScreenshots/Screen.png"));
        System.out.println("Screenshot is captured");

        System.out.println("Appointment booked successfully!!!");





        driver.quit();


    }
}
