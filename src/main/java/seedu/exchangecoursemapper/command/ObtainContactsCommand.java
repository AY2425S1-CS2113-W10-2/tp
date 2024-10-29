package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.constants.Assertions;
import seedu.exchangecoursemapper.constants.Logs;
import seedu.exchangecoursemapper.exception.Exception;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.exchangecoursemapper.constants.Regex.BACKSLASH;
import static seedu.exchangecoursemapper.constants.Regex.SPACE;
import static seedu.exchangecoursemapper.constants.JsonKey.EMAIL_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.NUMBER_KEY;

public class ObtainContactsCommand extends CheckInformationCommand {
    private static final Logger logger = Logger.getLogger(ObtainContactsCommand.class.getName());

    @Override
    public void execute(String userInput) {
        logger.log(Level.INFO, Logs.EXECUTING_COMMAND);
        try {
            JsonObject jsonObject = super.createJsonObject();
            logger.log(Level.INFO, Logs.SUCCESS_READ_JSON_FILE);
            assert jsonObject != null : Assertions.NULL_JSON_FILE;
            assert !jsonObject.isEmpty() : Assertions.EMPTY_JSON_FILE;
            String schoolName = getSchoolName(userInput).toLowerCase();
            String contactType = getContactType(userInput);
            String matchingSchool = findMatchingSchool(jsonObject, schoolName);
            JsonObject schoolInfo = jsonObject.getJsonObject(matchingSchool);
            if (schoolInfo == null) {
                return;
            }
            handleContactType(schoolInfo, matchingSchool, contactType);
        } catch (IOException e) {
            logger.log(Level.WARNING, Logs.FAILURE_READ_JSON_FILE);
            System.err.println(Exception.fileReadError());
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        logger.log(Level.INFO, Logs.COMPLETE_EXECUTION);
    }

    public String getSchoolName(String userInput) {
        String inputWithoutCommand = userInput.substring(userInput.indexOf(SPACE) + 1).trim();
        String[] inputParts = inputWithoutCommand.split(BACKSLASH);
        assert inputParts.length > 0 : Assertions.EMPTY_SCHOOL_NAME;
        return inputParts[0].trim();
    }

    public String getContactType(String userInput) {
        String inputWithoutCommand = userInput.substring(userInput.indexOf(SPACE) + 1).trim();
        String[] inputParts = inputWithoutCommand.split(BACKSLASH);

        if (inputParts.length != 2) {
            System.out.println(Exception.invalidInputFormat());
            throw new IllegalArgumentException(Exception.invalidInputFormat());
        }
        return inputParts[1].trim();
    }

    /**
     * Handles output of different contact types and outputs a string of the details
     *
     * @return a String representing the email/number of the specified partner university.
     * @throws IOException if the file at {@code FILE_PATH} cannot be found or read.
     */
    public void handleContactType(JsonObject schoolInfo, String schoolName, String contactType) {
        switch (contactType) {
        case EMAIL_KEY:
            String email = schoolInfo.getString(EMAIL_KEY);
            System.out.println("Email for " + schoolName + ": " + email);
            break;
        case NUMBER_KEY:
            String number = schoolInfo.getString(NUMBER_KEY);
            System.out.println("Phone number for " + schoolName + ": " + number);
            break;
        default:
            logger.warning("Invalid contact type requested: " + contactType);
            System.out.println(Exception.invalidContactType());
        }
    }

    public String findMatchingSchool(JsonObject jsonObject, String schoolName) {
        assert jsonObject != null : Assertions.NULL_JSON_OBJECT;
        assert schoolName != null : Assertions.NULL_SCHOOL_NAME;
        for (String key : jsonObject.keySet()) {
            if (key.toLowerCase().equals(schoolName)) {
                return key;
            }
        }
        System.out.println("Unknown university - " + schoolName);
        return schoolName;
    }
}
