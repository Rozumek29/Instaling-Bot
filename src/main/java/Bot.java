import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot {

    public static String chromeDriverPath = "chromedriver.exe";
    public static String loginPath = "login.json";
    public static String wordsPath = "words.json";
    public static String logPath = "log/lastest.log";

    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger("lastest");

        //Setup path to the ChromeDriver.
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        //Setup driver
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--mute-audio");
        WebDriver driver = new ChromeDriver(chromeOptions);

        FilesControler controler = new FilesControler();
        controler.createLogger();

        driver.get("https://instaling.pl/teacher.php?page=login");
        logger.log(Level.INFO, "'Installing' page has been launched");

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
            logger.log(Level.INFO, "logged in successfully as user " + login);
            Thread.sleep(300);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());;
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
        }

        //Starting daily session.
        driver.findElement(By.xpath("//*[@id=\"session_button\"]")).click();
        if (driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).isDisplayed()){
            driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).click();
        }else {
            driver.findElement(By.xpath("//*[@id=\"start_session_button\"]")).click();
        }
        logger.log(Level.INFO, "Starting daily session");

        int i = 0;
        Map<String, String> answers = controler.getTranslations();

        while (true){
            Thread.sleep(500);
            String question = driver.findElement(By.className("translations")).getText().toString();
            try {
                if(driver.findElement(By.id("know_new")).isDisplayed()){
                    new Actions(driver).moveToElement(driver.findElement(By.id("know_new"))).click().perform();
                    Thread.sleep(250);
                    new Actions(driver).moveToElement(driver.findElement(By.id("skip"))).click().perform();
                    Thread.sleep(250);
                }
                if (answers.containsKey(question)){
                    String answer = answers.get(question);
                    driver.findElement(By.xpath("//*[@id=\"answer\"]")).sendKeys(answer);
                    Thread.sleep(2500);
                    new Actions(driver).moveToElement(driver.findElement(By.id("check"))).click().perform();
                    Thread.sleep(500);
                    answers.remove(question);
                    i++;
                    logger.log(Level.INFO, "Question -> " + question + " | Answer -> " + answer);
                    new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@id=\"nextword\"]"))).click().perform();
                }else {
                    if (question.isEmpty()){
                        throw new Exception("End Session");
                    }else {
                        throw new Exception("No word translations in database");
                    }
                }
            }catch (Exception e){
                if (e.getMessage().equalsIgnoreCase("No word translations in database")){
                    Thread.sleep(1000);
                    new Actions(driver).moveToElement(driver.findElement(By.id("check"))).click().perform();
                    Thread.sleep(500);
                    String translation = driver.findElement(By.xpath("//*[@id=\"word\"]")).getText().toString();
                    answers.put(question, translation);
                    logger.log(Level.INFO, "Added answer '"+translation+"' for question " + question);
                    controler.addNewWord(question, translation);
                    Thread.sleep(1000);
                    driver.findElement(By.xpath("//*[@id=\"nextword\"]")).click();
                }else{
                    System.out.println("Dzienna sesja zakończona");
                    System.out.println("Autor -> Rozumek29");
                    Thread.sleep(5000);
                    driver.quit();
                    logger.log(Level.INFO, "Daily session ended.");
                    logger.log(Level.INFO, "Autor -> Rozumek29");
                    Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
                    break;
                }
            }
        }
    }
}