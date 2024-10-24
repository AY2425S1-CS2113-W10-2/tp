package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.exception.Exception;

import static seedu.exchangecoursemapper.constants.Regex.BACKSLASH;
import static seedu.exchangecoursemapper.constants.Regex.SPACE;


import java.io.IOException;
import javax.json.JsonObject;

public class ObtainContactsCommand extends Command {
    public static final String EMAIL = "email";
    public static final String NUMBER = "number";

    @Override
    public void execute(String userInput) {
        try  {
            JsonObject jsonObject = super.createJsonObject();
            String schoolName = getSchoolName(userInput).toLowerCase();
            String contactType = getContactType(userInput);
            String matchingSchool = findMatchingSchool(jsonObject, schoolName);
            JsonObject schoolInfo = jsonObject.getJsonObject(matchingSchool);
            handleContactType(schoolInfo, matchingSchool, contactType);
        } catch (IOException e) {
            System.err.println(Exception.fileReadError());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getSchoolName(String userInput) {
        String inputWithoutCommand = userInput.substring(userInput.indexOf(SPACE) + 1).trim();
        String[] inputParts = inputWithoutCommand.split(BACKSLASH);
        return inputParts[0].trim();
    }

    public String getContactType(String userInput) {
        String inputWithoutCommand = userInput.substring(userInput.indexOf(SPACE) + 1).trim();
        String[] inputParts = inputWithoutCommand.split(BACKSLASH);

        if (inputParts.length != 2) {
            throw new IllegalArgumentException(Exception.invalidInputFormat());
        }
        return inputParts[1].trim();
    }

    public void handleContactType(JsonObject schoolInfo, String schoolName, String contactType) {
        switch (contactType) {
        case EMAIL:
            String email = schoolInfo.getString(EMAIL);
            System.out.println("Email for " + schoolName + ": " + email);
            break;
        case NUMBER:
            String number = schoolInfo.getString(NUMBER);
            System.out.println("Phone number for " + schoolName + ": " + number);
            break;
        default:
            System.out.println(Exception.invalidContactType());
        }
    }

    public String findMatchingSchool(JsonObject jsonObject, String schoolName) {
        for (String key : jsonObject.keySet()) {
            if (key.toLowerCase().equals(schoolName)) {
                return key;
            }
        }
        System.out.println("Unknown university - " + schoolName);
        return null;
    }
}
