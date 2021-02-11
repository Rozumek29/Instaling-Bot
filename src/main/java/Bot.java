import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Bot {

    public static String chromeDriverPath = "src/main/java/chromedriver.exe";
    public static String loginPath = "src/main/java/login.json";
    public static String wordsPath = "src/main/java/words.json";

    public static void main(String[] args) throws InterruptedException {

        //Setup path to the ChromeDriver.
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        //Setup driver
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--mute-audio");
        WebDriver driver = new ChromeDriver(chromeOptions);

        FilesControler controler = new FilesControler();

        driver.get("https://instaling.pl/teacher.php?page=login");

        //Login
        try {
            String login = controler.getLogin("login");
            String password = controler.getLogin("password");
            if (login.isEmpty() || password.isEmpty()){
                throw new Exception("no login details in the login.json file.");
            }

            driver.findElement(By.name("log_email")).sendKeys(login);
            driver.findElement(By.name("log_password")).sendKeys(password);
            driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/form/div/div[3]/button")).click();
            Thread.sleep(300);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());;
        }

        //Starting daily session.
        driver.findElement(By.xpath("//*[@id=\"session_button\"]")).click();
        if (driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).isDisplayed()){
            driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).click();
        }else {
            driver.findElement(By.xpath("//*[@id=\"start_session_button\"]")).click();
        }



        // driver.quit();
    }
}
