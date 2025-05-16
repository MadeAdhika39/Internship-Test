package com.periplus.automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ShoppingCartTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://www.periplus.com/";
    private static final String EMAIL    = "testdeletion89@gmail.com";
    private static final String PASSWORD = "Test123!";

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--start-maximized");
        driver = new ChromeDriver(opts);
        wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test(priority = 1)
    public void login() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("email"))).sendKeys(EMAIL);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);

        String before = driver.getCurrentUrl();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-login"))).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(before)));

        Assert.assertNotEquals(driver.getCurrentUrl(), BASE_URL, 
            "TC_001 Failed: Still on homepage after login");
    }

    @Test(priority = 2, dependsOnMethods = "login")
    public void addHarryPotterToCart() throws InterruptedException {
        driver.get(BASE_URL);
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(By.id("filter_name")));
        search.clear();
        search.sendKeys("Harry Potter");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));

        WebElement firstTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("(//div[contains(@class,'single-product')]//h3/a)[1]")
        ));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", firstTitle);
        Thread.sleep(300);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", firstTitle);

        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.btn-add-to-cart")
        ));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
        try {
            addBtn.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addBtn);
        }
    }
    
    @Test(priority = 3, dependsOnMethods = "addHarryPotterToCart")
    public void addGameOfThronesToCart() throws InterruptedException {
        // Search for Game of Thrones
        driver.get(BASE_URL);
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(By.id("filter_name")));
        search.clear();
        search.sendKeys("Game of Thrones");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // wait to dismiss preloader
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));

        // Click first product’s title via XPath
        WebElement firstTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("(//div[contains(@class,'single-product')]//h3/a)[1]")
        ));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", firstTitle);
        Thread.sleep(300);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", firstTitle);

        // Wait for and click Add to Cart button
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.btn-add-to-cart")
        ));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
        try {
            addBtn.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addBtn);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class,'modal-text') and contains(text(),'Success add to cart')]")
        ));
    }

    @Test(priority = 4, dependsOnMethods = "addGameOfThronesToCart")
    public void verifyProductInCart() {
        // dismiss overlay
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("Notification-Modal")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));

        // click cart
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("#show-your-cart a")));
        cartIcon.click();

        
        wait.until(ExpectedConditions.urlContains("/checkout/cart"));

        
        String page = driver.getPageSource().toLowerCase();
        Assert.assertTrue(page.contains("harry potter"),
            "TC_004 Failed: 'Harry Potter' not present in cart page.");
    }

    @Test(priority = 5, dependsOnMethods = "verifyProductInCart")
    public void cartPersistenceAfterRefresh() {
        // dismiss overlays
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("Notification-Modal")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));

        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("#show-your-cart a")));
        cartIcon.click();

        wait.until(ExpectedConditions.urlContains("/checkout/cart"));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.urlContains("/checkout/cart"));

        String page = driver.getPageSource().toLowerCase();
        Assert.assertTrue(page.contains("harry potter"),
            "TC_005 Failed: 'Harry Potter' not present after refresh.");
    }

@Test(priority = 6, dependsOnMethods = "cartPersistenceAfterRefresh")
public void deleteAllProductsFromCart() {
    // Dismiss overlays
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("notification-modal-header")));
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));

    // Open cart page
    WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#show-your-cart a")));
    cartIcon.click();
    wait.until(ExpectedConditions.urlContains("/checkout/cart"));

    //loop until all books deleted from cart
    while (true) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));

        List<WebElement> removes = driver.findElements(
            By.xpath("//a[contains(@class,'btn-cart-remove') and normalize-space(text())='Remove']")
        );
        if (removes.isEmpty()) {
            break;  
        }

        WebElement firstRemove = removes.get(0);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", firstRemove);
        wait.until(ExpectedConditions.elementToBeClickable(firstRemove));
        try {
            firstRemove.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", firstRemove);
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("notification-modal-header")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));
    }

    //wait untilall item is deleted confirmed
    wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'your shopping cart is empty')]")
    ));
}
    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
    }
}
