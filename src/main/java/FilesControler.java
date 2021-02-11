import net.bytebuddy.implementation.bytecode.Throw;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FilesControler {
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
}
