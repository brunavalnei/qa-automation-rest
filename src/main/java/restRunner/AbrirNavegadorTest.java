package restRunner;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;




public class AbrirNavegadorTest {
    static WebDriver driver;
    @Test
    @Before
    public void before() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.grocerycrud.com/v1.x/demo/my_boss_is_in_a_hurry/bootstrap");
        driver.manage().window().maximize();
//        WebElement element = driver.findElement(By.xpath("//*[@id=\"switch-version-select\"]/option[4]"));
        WebElement element = driver.findElement(By.xpath("//*[@id=\"switch-version-select\"]/option[4]"));
        element.click();
        driver.findElement(By.cssSelector((".btn-outline-dark"))).click();
        driver.findElement(By.id("field-customerName")).sendKeys("Teste Sicredi");
        driver.findElement(By.id("field-contactLastName")).sendKeys("Teste");
        driver.findElement(By.id("field-contactFirstName")).sendKeys("seu nome");
        driver.findElement(By.id("field-phone")).sendKeys("51 9999-9999");
        driver.findElement(By.id("field-addressLine1")).sendKeys("Av Assis Brasil, 3970");
        driver.findElement(By.id("field-addressLine2")).sendKeys("Torre D");
        driver.findElement(By.id("field-city")).sendKeys("Porto Alegre");
        driver.findElement(By.id("field-state")).sendKeys("RS");
        driver.findElement(By.id("field-postalCode")).sendKeys("91000-000");
        driver.findElement(By.id("field-country")).sendKeys("Brasil");
        driver.findElement(By.id("field-salesRepEmployeeNumber")).sendKeys("Fixter");
        driver.findElement(By.id("field-creditLimit")).sendKeys("200");
        driver.findElement(By.cssSelector((".btn-success"))).click();

WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"report-success\"]/p")));
        Assert.assertEquals("Your data has been successfully stored into the database. Edit Record or Go back to list", firstResult.getText());
                driver.quit();
    }


    @After
    public void after() {
//        driver.quit();
    }

//    @Test
    public void comecandoOTesteAutomatizado() {

    }
}
