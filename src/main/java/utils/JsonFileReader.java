package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {
    public static JSONObject readJsonObjectFromFile(String fileName) throws IOException, ParseException {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        String file_path = JsonFileReader.class.getClassLoader().getResource(fileName).getFile();
        FileReader reader = new FileReader(file_path);
        //Read JSON file
        Object obj = jsonParser.parse(reader);
        JSONObject beverageCompositionsJsonObject = (JSONObject) obj;

//        System.out.println(beverageCompositionsJsonObject);
        return beverageCompositionsJsonObject;
    }
}
