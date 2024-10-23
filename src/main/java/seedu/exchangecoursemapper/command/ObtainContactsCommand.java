package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.exception.Exception;

import java.io.IOException;
import javax.json.JsonObject;

public class ObtainContactsCommand extends Command {
    @Override
    public void execute(String userInput) {
        try  {
            JsonObject jsonObject = super.createJsonObject();
            String schoolName = getSchoolName(userInput).toLowerCase();
            String contactType = getContactType(userInput);
            String matchingSchool = findMatchingSchool(jsonObject, schoolName);

            if (matchingSchool != null) {
                JsonObject schoolInfo = jsonObject.getJsonObject(matchingSchool);
                handleContactType(schoolInfo, matchingSchool, contactType);
            } else {
                System.out.println("Error: Unknown university - " + schoolName);
            }
        } catch (IOException e) {
            System.err.println(Exception.fileReadError());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private String getSchoolName(String userInput) {
        String inputWithoutCommand = userInput.substring(userInput.indexOf(" ") + 1).trim();
        String[] inputParts = inputWithoutCommand.split(" /");
        return inputParts[0].trim();
    }

    private String getContactType(String userInput) {
        String inputWithoutCommand = userInput.substring(userInput.indexOf(" ") + 1).trim();
        String[] inputParts = inputWithoutCommand.split(" /");

        if (inputParts.length != 2) {
            throw new IllegalArgumentException("Invalid input format");
        }
        return inputParts[1].trim();
    }

    private void handleContactType(JsonObject schoolInfo, String schoolName, String contactType) {
        switch (contactType) {
        case "email":
            String email = schoolInfo.getString("email", null);
            if (email != null) {
                System.out.println("Email for " + schoolName + ": " + email);
            } else {
                System.out.println("Email not available for " + schoolName);
            }
            break;
        case "number":
            String number = schoolInfo.getString("number", null);
            if (number != null) {
                System.out.println("Phone number for " + schoolName + ": " + number);
            } else {
                System.out.println("Phone number not available for " + schoolName);
            }
            break;
        default:
            System.out.println("Invalid contact type. Please use 'email' or 'number'.");
        }
    }

    private String findMatchingSchool(JsonObject jsonObject, String schoolName) {
        for (String key : jsonObject.keySet()) {
            if (key.toLowerCase().equals(schoolName)) {
                return key;
            }
        }
        return null;
    }
}