package springsoft.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {

       // xpath = "//*[@class='jw-media jw-reset']/video|//meta[@itemprop='contentURL']";
        var xpath = "//meta[@itemprop='contentURL']";

        String exePath = "/home/ahmed/Downloads/chromedriver";
        System.setProperty("webdriver.chrome.driver", exePath);
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.setHeadless(true);
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setJavascriptEnabled(true);
        options.merge(dc);
        WebDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
        //
        List<String> urls = Files.readAllLines(Path.of("input"));
        urls.forEach(s -> {
            System.out.println("s = " + s);
            chromeDriver.get(s);
            WebElement webElement= chromeDriver.findElement(By.xpath(xpath));

                String result = webElement.getAttribute("src");
                if (result==null || result.equals(""))
                    result = webElement.getAttribute("content");
                System.out.println("Added : " + result);
                try {
                    Path path = Path.of("output");
                    if (!Files.exists(path))
                        Files.createFile(path);
                    Files.writeString(path, result + "\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }



        });


    }
}
