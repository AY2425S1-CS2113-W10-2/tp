package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.constants.Assertions;
import seedu.exchangecoursemapper.constants.Logs;
import seedu.exchangecoursemapper.exception.Exception;
import seedu.exchangecoursemapper.exception.UnknownUniversityException;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.exchangecoursemapper.constants.JsonKey.PU_COURSE_CODE_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.PU_COURSE_NAME_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.NUS_COURSE_CODE_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.NUS_COURSE_NAME_KEY;
import static seedu.exchangecoursemapper.constants.Messages.LINE_SEPARATOR;

public class ListUniCoursesCommand extends Command {
    private static final Logger logger = Logger.getLogger(ListUniCoursesCommand.class.getName());

    @Override
    public void execute(String userInput) {
        logger.log(Level.INFO, Logs.EXECUTING_COMMAND);
        try {
            JsonObject jsonObject = super.createJsonObject();
            logger.log(Level.INFO, Logs.SUCCESS_READ_JSON_FILE);
            assert jsonObject != null : Assertions.NULL_JSON_FILE;
            assert !jsonObject.isEmpty() : Assertions.EMPTY_JSON_FILE;
            String puName = getPuName(userInput);
            getUniCourses(jsonObject, puName);
        } catch (IOException e) {
            logger.log(Level.WARNING, Logs.FAILURE_READ_JSON_FILE);
            System.err.println(Exception.fileReadError());
        } catch (UnknownUniversityException e) {
            logger.log(Level.WARNING, Logs.UNKNOWN_UNIVERSITY, e.getMessage());
            System.err.println(e.getMessage());
            System.out.println(LINE_SEPARATOR);
        }
    }

    public String getPuName (String userInput) {
        assert userInput != null : Assertions.EMPTY_USER_INPUT;

        String puName = userInput.replaceFirst("set", "").trim();
        logger.log(Level.INFO, Logs.EXTRACT_PU_NAME);

        if (puName.isEmpty()) {
            logger.log(Level.WARNING, Logs.NO_PU_NAME);
            throw new IllegalArgumentException(Exception.emptyUniversityName());
        }

        return puName;
    }

    public void getUniCourses (JsonObject jsonObject, String puName) throws UnknownUniversityException {
        assert jsonObject != null : Assertions.NULL_JSON_FILE;
        assert puName != null : Assertions.EMPTY_PU_NAME;

        String lowerCasePuName = puName.toLowerCase();
        Set<String> universityNames = jsonObject.keySet();

        logger.log(Level.INFO, Logs.SEARCH_UNIVERSITY, puName);
        String universityName = findUniversityName(universityNames, lowerCasePuName);

        if (universityName == null) {
            handleUnknownUniversity(puName);
        } else {
            listCourses(jsonObject, universityName);
        }
    }

    private String findUniversityName(Set<String> universityNames, String lowerCasePuName) {
        for (String universityName : universityNames) {
            assert universityName != null && !universityName.isEmpty();
            if (universityName.toLowerCase().equals(lowerCasePuName)) {
                logger.log(Level.INFO, Logs.UNIVERSITY_FOUND, universityName);
                return universityName;
            }
        }
        return null;
    }

    private void handleUnknownUniversity(String puName) throws UnknownUniversityException {
        logger.log(Level.WARNING, Logs.UNKNOWN_UNIVERSITY, puName);
        throw new UnknownUniversityException("University not found: " + puName);
    }

    private void listCourses(JsonObject jsonObject, String universityName) {
        JsonObject universityObject = getUniversityObject(jsonObject, universityName);
        JsonArray courseArray = getCourseArray(universityObject, universityName);

        logger.log(Level.INFO, Logs.LISTING_COURSES);
        iterateCourses(courseArray);
    }

    private JsonObject getUniversityObject(JsonObject jsonObject, String universityName) {
        JsonObject universityObject = jsonObject.getJsonObject(universityName);
        assert universityObject != null : Assertions.NULL_UNIVERSITY_OBJECT;
        return universityObject;
    }

    private JsonArray getCourseArray(JsonObject universityObject, String universityName) {
        JsonArray courseArray = universityObject.getJsonArray("courses");
        if (courseArray == null) {
            logger.log(Level.WARNING, Logs.NO_COURSES_FOUND);
            throw new IllegalArgumentException("No courses found for university: " + universityName);
        }
        return courseArray;
    }

    private void iterateCourses(JsonArray courseArray) {
        for (int i = 0; i < courseArray.size(); i++) {
            JsonObject courseObject = courseArray.getJsonObject(i);
            assert courseObject != null : Assertions.NO_COURSE_OBJECT;
            printCourseDetails(courseObject);
        }
    }
    
    private void printCourseDetails(JsonObject courseObject) {
        String puCourseCode = courseObject.getString(PU_COURSE_CODE_KEY);
        String puCourseName = courseObject.getString(PU_COURSE_NAME_KEY);
        String nusCourseCode = courseObject.getString(NUS_COURSE_CODE_KEY);
        String nusCourseName = courseObject.getString(NUS_COURSE_NAME_KEY);

        System.out.println(puCourseCode + ": " + puCourseName);
        System.out.println(nusCourseCode + ": " + nusCourseName);
        System.out.println(LINE_SEPARATOR);
    }

}
