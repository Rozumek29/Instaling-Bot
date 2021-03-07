import com.google.gson.Gson;
import mslinks.ShellLink;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FilesControler {
    File dir, login, words, settings, driver, autorun;
    String userDir = System.getProperty("user.home");

    FilesControler() {
        dir = new File(userDir + "/InstalingBot");
        login = new File(dir + "/login.json");
        words = new File(dir + "/words.json");
        settings = new File(dir + "/settings.json");
        driver = new File(dir + "/chromedriver.exe");
        autorun = new File(userDir+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\Notyfication.jar");
}

    public void dirsExist() {
        if (!dir.exists()) {
            dir.mkdir();
            try {
                FileUtils.copyURLToFile(getClass().getResource("/login.json"), login);
                FileUtils.copyURLToFile(getClass().getResource("/words.json"), words);
                FileUtils.copyURLToFile(getClass().getResource("/settings.json"), settings);
                FileUtils.copyURLToFile(getClass().getResource("/chromedriver.exe"), driver);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        checkAutoRun();
    }

    public void checkAutoRun(){
        if (getSettingsBoolean("Notyfication") && (!autorun.exists())){
            try {
                FileUtils.copyURLToFile(getClass().getResource("/Notyfication.jar"), autorun);
                Runtime.getRuntime().exec("cmd.exe /c java -jar "+userDir+"\\AppData\\Roaming\\Microsoft\\Windows\\Start_Menu\\Programs\\Startup\\Notyfication.jar");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (!(getSettingsBoolean("Notyfication")) && autorun.exists()){
            autorun.delete();
        }
    }

    public void setSettings(boolean rememberMe, double delay, String browser, String browserPath, boolean nt_on, int nt_h, int nt_min) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(settings, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;

            jsonObject.put("RememberMe", rememberMe);
            jsonObject.put("Delay", delay);
            jsonObject.put("Browser", browser);
            jsonObject.put("Browser Path", browserPath);
            jsonObject.put("Notyfication", nt_on);
            jsonObject.put("Hour", nt_h);
            jsonObject.put("Min", nt_min);

            try (FileWriter writer = new FileWriter(settings, StandardCharsets.UTF_8)) {
                writer.write(jsonObject.toJSONString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Double getDelay() {
        JSONParser parser = new JSONParser();
        double delay = 2500;

        try {
            Object object = parser.parse(new FileReader(settings, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;
            delay = Double.parseDouble(jsonObject.get("Delay").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return delay;
    }

    public boolean getSettingsBoolean(String s) {
        JSONParser parser = new JSONParser();
        boolean x = true;
        try {
            Object object = parser.parse(new FileReader(settings, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;
            x = (boolean) jsonObject.get(s);
            return x;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }

    public void savePass(String login, String password) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(this.login, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;

            jsonObject.put("Login", login);
            jsonObject.put("Password", password);

            try (FileWriter writer = new FileWriter(this.login, StandardCharsets.UTF_8)) {
                writer.write(jsonObject.toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getLogin(String s) {
        JSONParser parser = new JSONParser();
        String x = "";
        try {
            Object object = parser.parse(new FileReader(login, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;

            x = jsonObject.get(s).toString();
            return x;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }
    public String getSettings(String s) {
        JSONParser parser = new JSONParser();
        String x = "";
        try {
            Object object = parser.parse(new FileReader(settings, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;

            x = jsonObject.get(s).toString();
            return x;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }

    public Map<String, String> getTranslations() {

        JSONParser parser = new JSONParser();

        try {
            Object object = parser.parse(new FileReader(words, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;

            Map<String, String> map = new Gson().fromJson(jsonObject.toJSONString(), Map.class);
            return map;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addNewWord(String question, String translation) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(words, StandardCharsets.UTF_8));

            JSONObject jsonObject = (JSONObject) object;

            jsonObject.put(question, translation);

            try (FileWriter fileWriter = new FileWriter(words, StandardCharsets.UTF_8)) {
                fileWriter.write(jsonObject.toJSONString());
                fileWriter.flush();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}