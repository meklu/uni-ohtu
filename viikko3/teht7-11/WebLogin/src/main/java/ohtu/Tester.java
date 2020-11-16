package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        // for some god-forsaken reason, the chromium executable wants to use
        // its wayland backend by default, even within an x11 session???
        options.addArguments("--ozone-platform=x11");
        WebDriver driver = new ChromeDriver(options);
        WebElement element = null;
        Random r = new Random();

        driver.get("http://localhost:4567");

// login validly
//        sleep(2);
//
//        element = driver.findElement(By.linkText("login"));
//        element.click();
//
//        sleep(2);
//
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("pekka");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("akkep");
//        element = driver.findElement(By.name("login"));

// invalid password
//        sleep(2);
//
//        element = driver.findElement(By.linkText("login"));
//        element.click();
//
//        sleep(2);
//
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("pekka");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("aeiou");
//        element = driver.findElement(By.name("login"));
//
//        sleep(2);
//        element.submit();

// register new user and log out
        sleep(2);

        element = driver.findElement(By.linkText("register new user"));
        element.click();

        sleep(2);

        element = driver.findElement(By.name("username"));
        // note that the usernames here generated became invalid with the newer
        // authentication service changes as non-alphabetic characters are
        // considered invalid
        element.sendKeys("pate" + r.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("etappi");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("etappi");
        element = driver.findElement(By.name("signup"));

        sleep(2);
        element.submit();

        sleep(2);

        element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();

        sleep(2);

        element = driver.findElement(By.linkText("logout"));
        element.click();

        sleep(3);

        driver.quit();
    }

    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }
}
