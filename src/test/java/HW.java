import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HW {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(HW.class);

    @Before
    public void StartUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        logger.info("Драйвер поднят");
    }

    @After
    public void End() throws InterruptedException {
        if (driver!=null)
            driver.quit();
    }

//первое задание
//    Открыть Chrome в headless режиме
//    Перейти на https://duckduckgo.com/
//    В поисковую строку ввести ОТУС
//    Проверить что в поисковой выдаче первый результат Онлайн‑курсы для профессионалов, дистанционное обучение
    @Test
    public void Part_1() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.get("https://duckduckgo.com/");
        logger.info("Зашли на сайт");
        String locator_1 = "//input[@id=\"search_form_input_homepage\"]";
        String locator_2 = "//*[@id=\"search_button_homepage\"]";
        String locator_3 = "//div[5]/div[1]/div/h2/a[1]";
        String textLocator = "ОТУС";
        driver.findElement(By.xpath(locator_1)).sendKeys(textLocator);
        logger.info("ввели otus");
        driver.findElement(By.xpath(locator_2)).click();
        logger.info("перешли на страницу по поиску");

        //проверка
        Assert.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение...",
                driver.findElement(By.xpath(locator_3)).getText());
        logger.info("проверили первую ссылку");
//        driver.quit();
    }

//второе задание
//    Открыть Chrome в режиме киоска
//    Перейти на https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818
//    Нажать на любую картинку
//    Проверить что картинка открылась в модальном окне
    @Test
    public void Part_2() throws InterruptedException {
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        driver.manage().window().fullscreen();
        Thread.sleep(5000);
        logger.info("Запушен режим киоска");

        String locator_1 = "//section[2]/div/ul[2]/li[2]/span/a/div[2]/h3";
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("scroll(0, 466);");
        Thread.sleep(1000);
        driver.findElement(By.xpath(locator_1)).click();
        Thread.sleep(1000);
        // разобраться с модальным окном - по какому локатору проверять, что оно модальное!!!

        // как я поняла, модальное окно это такой блок с некоторыми параметрами - одно из них этo overflow : hidden
        // поэтому проверять это я буду просто проверять на наличие этого окна.

        String locator_2 = "//div[@class ='pp_pic_holder light_rounded']";
        WebElement ModalWindow = driver.findElement(By.xpath(locator_2));
        Boolean bool = ModalWindow.isDisplayed();
        Assert.assertEquals(true,bool);
        
    }

    private void auth() throws InterruptedException {
        String login = "anndan1997@gmail.ru";
        String password = "1nv1n0Ver1ta$";
        String locator = "button.header2__auth.js-open-modal";
        driver.findElement(By.cssSelector(locator)).click();
        driver.findElement(By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)")).sendKeys(login);
        driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(password);
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
        logger.info("Авторизация прошла успешно");
    }

    private void cookie() {
        Set<Cookie> cookies = driver.manage().getCookies();
        logger.info(cookies);
        logger.info("Файлы cookie");
    }

//        третье задание
//        Открыть Chrome в режиме полного экрана
//        Перейти на https://otus.ru
//        Авторизоваться под каким-нибудь тестовым пользователем(можно создать нового)
//        Вывести в лог все cookie
    @Test
    public void Part_3() throws InterruptedException {
        driver.get("https://otus.ru");
        driver.manage().window().maximize();
        Thread.sleep(1000);
        logger.info("Зашли на сайт в режиме полного экрана");
        auth();
        cookie();
    }

}
