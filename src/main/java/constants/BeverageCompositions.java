package constants;

import enums.BeverageType;
import enums.Ingredient;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.JsonFileReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BeverageCompositions {
    public static final String BEVERAGE_COMPOSITIONS_FILE_NAME = "beverage_compositions.json";
    public static Map<BeverageType, Map<Ingredient, Integer>> beverageCompositions = new HashMap<>();

    // there may exist better ways of doing this but due to time shortage doing like this
    static {
        loadBeverageCompositions();
    }

    public static void loadBeverageCompositions() {
        try {
            JSONObject beverageCompositionsJsonObject = JsonFileReader.readJsonObjectFromFile(BeverageCompositions.BEVERAGE_COMPOSITIONS_FILE_NAME);
            parseBeverageCompositions(beverageCompositionsJsonObject);
//            System.out.println(beverageCompositions);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseBeverageCompositions(JSONObject beverageCompositionsJsonObject) {
        for (Object beverageTypeObject : beverageCompositionsJsonObject.keySet()) {
            BeverageType beverageType = getBeverageType((String) beverageTypeObject);
            JSONObject beverageComposition = (JSONObject) beverageCompositionsJsonObject.get(beverageTypeObject);
            Map<Ingredient, Integer> ingredientsAmounts = new HashMap<>();
            for (Object ingredientObject : beverageComposition.keySet()) {
                Ingredient ingredient = getIngredient((String) ingredientObject);
                Integer amount = ((Long) beverageComposition.get(ingredientObject)).intValue();
                ingredientsAmounts.put(ingredient, amount);
            }
            beverageCompositions.put(beverageType, ingredientsAmounts);
        }
    }

    private static Ingredient getIngredient(String ingredientObject) {
        return Ingredient.valueOf(ingredientObject.toUpperCase());
    }

    private static BeverageType getBeverageType(String beverageTypeString) {
        return BeverageType.valueOf(beverageTypeString.toUpperCase());
    }
}
