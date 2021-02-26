import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StartBotController implements Initializable {
    FilesControler controler = new FilesControler();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void Run(String login, String password, double delay) throws Exception {
        //Setup driver
        System.setProperty("webdriver.chrome.driver", controler.dir+"/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        if (controler.getSettings("Browser").equalsIgnoreCase("Other")){
            chromeOptions.setBinary(controler.getSettings("Browser Path"));
        }
        chromeOptions.addArguments("--mute-audio");
        WebDriver driver = new ChromeDriver(chromeOptions);

        //Login
        driver.get("https://instaling.pl/teacher.php?page=login");
        try {
            if (login.isEmpty() || password.isEmpty()){
                throw new Exception("No login details, please insert login and password into user profile");
            }
            driver.findElement(By.name("log_email")).sendKeys(login);
            driver.findElement(By.name("log_password")).sendKeys(password);
            driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/form/div/div[3]/button")).click();
            Thread.sleep(300);
        }catch (Exception e){
            e.printStackTrace();
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
        }
        //Starting daily session.
        driver.findElement(By.xpath("//*[@id=\"session_button\"]")).click();
        if (driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).isDisplayed()){
            driver.findElement(By.xpath("//*[@id=\"continue_session_button\"]")).click();
        }else {
            driver.findElement(By.xpath("//*[@id=\"start_session_button\"]")).click();
        }

        int i = 0;
        Map<String, String> answers = controler.getTranslations();

        while (true) {
            Thread.sleep(500);
            String question = driver.findElement(By.className("translations")).getText().toString();
            try {
                if (driver.findElement(By.id("know_new")).isDisplayed()) {
                    new Actions(driver).moveToElement(driver.findElement(By.id("know_new"))).click().perform();
                    Thread.sleep(250);
                    new Actions(driver).moveToElement(driver.findElement(By.id("skip"))).click().perform();
                    Thread.sleep(250);
                }
                if (answers.containsKey(question)) {
                    String answer = answers.get(question);
                    driver.findElement(By.xpath("//*[@id=\"answer\"]")).sendKeys(answer);
                    Thread.sleep((long) delay);
                    new Actions(driver).moveToElement(driver.findElement(By.id("check"))).click().perform();
                    Thread.sleep(500);
                    answers.remove(question);
                    i++;
                    new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@id=\"nextword\"]"))).click().perform();
                } else {
                    if (question.isEmpty()) {
                        throw new Exception("End Session");
                    } else {
                        throw new Exception("No word translations in database");
                    }
                }
            } catch (Exception e) {
                if (e.getMessage().equalsIgnoreCase("No word translations in database")) {
                    Thread.sleep(1000);
                    new Actions(driver).moveToElement(driver.findElement(By.id("check"))).click().perform();
                    Thread.sleep(500);
                    String translation = driver.findElement(By.xpath("//*[@id=\"word\"]")).getText().toString();
                    answers.put(question, translation);
                    controler.addNewWord(question, translation);
                    Thread.sleep(1000);
                    driver.findElement(By.xpath("//*[@id=\"nextword\"]")).click();
                } else {
                    Thread.sleep(5000);
                    driver.quit();
                    Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
                    break;
                }
            }
        }
    }
    String login, password;
    public void StartBot(MouseEvent mouseEvent) throws Exception {
        if (controler.getSelected()){
            login = controler.getLogin("Login");
            password = controler.getLogin("Password");
        }else {
            login = UserProfileUI.temp_login;
            password = UserProfileUI.temp_password;
        }

        double double_delay = controler.getDelay();
        int delay = (int) double_delay;
        Run(login, password, delay);
    }
}
