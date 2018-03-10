package service;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class JsonHandler {

    public JSONArray getDataFromJson (String source){

        JSONArray jsonArray = null;
        JSONParser jsonParser = new JSONParser();

        try {
            jsonArray = (JSONArray) jsonParser.parse(source);
        } catch (org.json.simple.parser.ParseException exception) {
            System.err.println("Ошибка парсинга JSON: " + exception.getMessage());
        }

        return jsonArray;
    }
}
