import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FilesControler {
    Logger logger = Logger.getLogger("lastest");
    FileHandler fileHandler;

    public String getLogin(String x) throws Exception {
        return getFromJson(x).toString();
    }

    private String getFromJson(String arg){
        JSONParser parser = new JSONParser();

        try {
            Object object = parser.parse(new FileReader(Bot.loginPath, StandardCharsets.UTF_8));

            JSONObject jsonObject = (JSONObject) object;

            if (jsonObject.containsKey("login")){
                return jsonObject.get(arg).toString();
            }else {
                System.out.println("TEST");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getTranslations(){

        JSONParser parser = new JSONParser();

        try {
            Object object = parser.parse(new FileReader(Bot.wordsPath, StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) object;

            Map<String, String> map = new Gson().fromJson(jsonObject.toJSONString(), Map.class);
            return map;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addNewWord(String question, String translation){
        JSONParser parser = new JSONParser();
         try {
             Object object = parser.parse(new FileReader(Bot.wordsPath, StandardCharsets.UTF_8));

             JSONObject jsonObject = (JSONObject) object;

             jsonObject.put(question, translation);

             try (FileWriter fileWriter = new FileWriter(Bot.wordsPath, StandardCharsets.UTF_8)) {
                 fileWriter.write(jsonObject.toJSONString());
                 fileWriter.flush();
             }
         } catch (ParseException | IOException e) {
             e.printStackTrace();
         }
    }
    public void createLogger(){
        try {
            fileHandler = new FileHandler(Bot.logPath);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
