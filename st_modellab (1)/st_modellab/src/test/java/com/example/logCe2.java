package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class logCe2 {
        WebDriver driver;
        Logger log = Logger.getLogger(logCe2.class);

        @BeforeTest
        public void setup() {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.get("https://www.opentable.com");
                driver.manage().window().maximize();
                PropertyConfigurator.configure(
                                "C:\\Users\\Admin\\Music\\st_modellab\\log4j\\log4j.properties");
        }

        @Test(priority = 0)
        public void testCase1() throws IOException, InterruptedException {
                FileInputStream fs = new FileInputStream("C:\\Users\\Admin\\Music\\Model lab1.xlsx");
                XSSFWorkbook work = new XSSFWorkbook(fs);
                XSSFSheet sheet = work.getSheet("Sheet1");
                XSSFRow row = sheet.getRow(0);
                String location = row.getCell(0).getStringCellValue();
                driver.findElement(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[1]/div/input"))
                                .sendKeys(location);
                log.info("The data from sheet is passed successfully");
                driver.findElement(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[2]/button"))
                                .click();
                Thread.sleep(2000);
                log.info("The page has been redirected and displayed the list of hotels in the Bangalore");
                driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/section/div[6]/div/label[4]/span[2]"))
                                .click();
                Thread.sleep(2000);
                log.info("The Asian filter has been applied and the page shows results based on that filter");
                driver.findElement(By.partialLinkText("Far & East")).click();
                log.info("A new tab has been opened");
                Thread.sleep(2000);
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File("screenshot.png"));
                String currWindow = driver.getWindowHandle();
                Set<String> windows = driver.getWindowHandles();
                for (String window : windows) {
                        if (!currWindow.equals(window)) {
                                driver.switchTo().window(window);
                                break;
                        }
                }
                log.info("The window has been switched");
                Thread.sleep(4000);
                Select partySize = new Select(
                                driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpPartySizePicker\"]")));
                partySize.selectByIndex(3);
                Thread.sleep(2000);
                driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker\"]/div/div/span")).click();
                Thread.sleep(4000);
                while (true) {
                        String curr = driver
                                        .findElement(By.xpath(
                                                        "//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div"))
                                        .getText();
                        System.out.println(curr);
                        if (curr.equals("November 2024")) {
                                driver.findElement(By.xpath(
                                                "//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/table/tbody/tr[3]/td[2]/button"))
                                                .click();
                                break;
                        }
                        driver.findElement(
                                        By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div/div[2]/button[2]"))
                                        .click();
                }
                Select time = new Select(
                                driver.findElement(By.xpath("//*[@id=\"restProfileSideBartimePickerDtpPicker\"]")));
                time.selectByVisibleText("6:30 PM");
                Thread.sleep(2000);
                log.info("Make a reservation section has been completed");
                driver.findElement(By.xpath("//*[@id=\"baseApp\"]/div/header/div[2]/div[2]/div[1]/button")).click();
                Thread.sleep(5000);
                File screenshotFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshotFile1, new File("screenshot1.png"));
        }

        @AfterTest
        public void quit() {
                driver.quit();
        }
}