
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class AddToCartTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }
    @Test public void testAddBookToCart() throws InterruptedException {
        driver.get("https://www.ebay.com");
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-ac")));
        searchBox.sendKeys("book");
        driver.findElement(By.xpath("//*[@id='gh-search-btn']/span")).click();
        wait.until(ExpectedConditions.titleContains("Book"));
        driver.findElement(By.xpath("//li[contains(@id,'item')]//div/following::div[3]")).click();

//        List<WebElement> results = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("li.s-item"), 0));
//        System.out.println(results);
//        results.get(0).findElement(By.cssSelector("a.s-item_link")).click();
        for (String winHandle : driver.getWindowHandles())
        {
            driver.switchTo().window(winHandle);
        }
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Add to cart')]")));
        addToCartBtn.click();
        driver.findElement(By.xpath("//button[@aria-label='Close overlay']")).click();
        WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".gh-cart__icon")));
        String countText = cartCount.getText();
        Assert.assertTrue(Integer.parseInt(countText) > 0, "Cart count should be greater than 0");
    }
    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }
}


