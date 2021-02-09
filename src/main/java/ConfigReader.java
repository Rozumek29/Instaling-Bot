import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConfigReader {
    public String getLogin(){
        return getPassFromJson("login");
    }
    public String getPassword(){
        return getPassFromJson("password");
    }
    
    private String getPassFromJson(String x){
        JSONParser parser = new JSONParser();
        String querry = null;
        try {
            Object obj = parser.parse(new FileReader("login.json", StandardCharsets.UTF_8));
            
            JSONObject jsonObject = (JSONObject) obj;

            querry = jsonObject.get(x).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return querry;
    }

    public String getTranslation(String word) throws Exception {
        String querry = "";
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("words.json", StandardCharsets.UTF_8));

            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.containsKey(word)){
                querry = jsonObject.get(word).toString();
            }else {
                throw new Exception("No word translations in database");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return querry;
    }

    public void setTranslation(String word, String translation){
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(new FileReader("words.json", StandardCharsets.UTF_8));

            JSONObject jsonObject = (JSONObject) obj;

            jsonObject.put(word, translation);

            try (FileWriter fileWriter = new FileWriter("words.json", StandardCharsets.UTF_8)) {
                fileWriter.write(jsonObject.toJSONString());
                fileWriter.flush();
            }
            catch (IOException e) {
            e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
