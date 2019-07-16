package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String url = "https://shop.wuage.com/lfsztwz05691/page/contactinfo.htm?psa=W9.p10.c69.34";

        //Launch website
        driver.navigate().to(url);
        driver.manage().window().maximize();

        // Click on Radio Button
        driver.findElement(By.linkText("点击查看")).click();
        //driver.find_element_by_class_name("style1") .click();
        Select dropdowm = new Select(driver.findElement(By.name("cdownpaymentunit")));
        dropdowm.selectByValue("d");

        System.out.println("The Output of the IsSelected " + driver.findElement(By.name("cdownpaymentunit")).isSelected());
        System.out.println("The Output of the IsEnabled " + driver.findElement(By.name("cdownpaymentunit")).isEnabled());
        System.out.println("The Output of the IsDisplayed " + driver.findElement(By.name("cdownpaymentunit")).isDisplayed());

        //driver.close();

        //Close the Browser.

    }

    
}
