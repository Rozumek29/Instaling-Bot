import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FilesControler {
    File dir, login, words, settings, chromedriver;

    FilesControler() {
        String userDir = System.getProperty("user.home");
        dir = new File(userDir + "/InstalingBot");
        login = new File(dir + "/login.json");
        words = new File(dir + "/words.json");
        settings = new File(dir + "/settings.json");
    }

    public void dirExist() {
        if (!dir.exists()) {
            dir.mkdir();
            try {
                FileUtils.copyURLToFile(getClass().getResource("/login.json"), login);
                FileUtils.copyURLToFile(getClass().getResource("/words.json"), words);
                FileUtils.copyURLToFile(getClass().getResource("/settings.json"), settings);
                File driver = new File(dir + "/chromedriver.exe");
                FileUtils.copyURLToFile(getClass().getResource("/chromedriver.exe"), driver);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSettings(Boolean rememberMe, double delay, String browser, String browserPath) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(settings, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;

            jsonObject.put("RememberMe", rememberMe);
            jsonObject.put("Delay", delay);
            jsonObject.put("Browser", browser);
            jsonObject.put("Browser Path", browserPath);

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

    public boolean getSelected() {
        JSONParser parser = new JSONParser();
        boolean selected = true;
        try {
            Object object = parser.parse(new FileReader(settings, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;
            selected = (boolean) jsonObject.get("RememberMe");
            return selected;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selected;
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