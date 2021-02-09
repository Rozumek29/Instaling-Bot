import org.junit.experimental.theories.Theories;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Bot {
    public static void main(String[] args) throws InterruptedException {
        ConfigReader reader = new ConfigReader();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://instaling.pl/teacher.php?page=login");
        driver.findElement(By.name("log_email")).sendKeys(reader.getLogin());
        driver.findElement(By.name("log_password")).sendKeys(reader.getPassword());
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/form/div/div[3]/button")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"session_button\"]")).click();
        if (driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).isDisplayed()){
            driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).click();
        }else {
            driver.findElement(By.xpath("//*[@id=\"start_session_button\"]")).click();
        }
        do{
            Thread.sleep(500);
            String question = driver.findElement(By.className("translations")).getText().toString();
            try {
                Thread.sleep(1000);
                String Answer = reader.getTranslation(question);
                driver.findElement(By.xpath("//*[@id=\"answer\"]")).sendKeys(Answer);
                Thread.sleep(3000);
                new Actions(driver).moveToElement(driver.findElement(By.id("check"))).click().perform();
                Thread.sleep(500);
                new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@id=\"nextword\"]"))).click().perform();
            }catch (Exception e){
                if (e.getMessage().equalsIgnoreCase("No word translations in database")){
                    Thread.sleep(1000);
                    new Actions(driver).moveToElement(driver.findElement(By.id("check"))).click().perform();
                    Thread.sleep(500);
                    String translation = driver.findElement(By.xpath("//*[@id=\"word\"]")).getText();
                    reader.setTranslation(question, translation);
                    Thread.sleep(1000);
                    driver.findElement(By.xpath("//*[@id=\"nextword\"]")).click();
                }
                else {
                    System.out.println("Diffrend Error");
                }
            }
        }while (!driver.findElement(By.xpath("//*[@id=\"summary\"]/table/caption")).isDisplayed());
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@id=\"return_mainpage\"]"))).click().perform();
        System.out.println("Dzienna sesja zakończona");
        System.out.println("Autor -> Rozumek29");
        driver.quit();
    }

}
