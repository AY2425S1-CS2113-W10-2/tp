package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.exception.Exception;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class ListSchoolCommand extends Command {
    @Override
    public void execute(String userInput) {
        try (JsonReader jsonReader = Json.createReader(new FileReader(FILE_PATH))) {
            JsonObject jsonObject = jsonReader.readObject();
            displaySchoolList(jsonObject);
        } catch (IOException e) {
            System.err.println(Exception.fileReadError());
        }
    }

    private static void displaySchoolList(JsonObject jsonObject) {
        Set<String> universityNames = jsonObject.keySet();
        for (String universityName : universityNames) {
            System.out.println(universityName);
        }
    }
}