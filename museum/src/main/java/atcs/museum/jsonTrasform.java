package atcs.museum;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class jsonTrasform {
    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try {     
            Object obj = parser.parse(new FileReader("resource/Data/T4CH_data.json"));

            JSONObject jsonObject =  (JSONObject) obj;

            Long numberVisitor = (Long) jsonObject.get("number");
            System.out.println(numberVisitor);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}