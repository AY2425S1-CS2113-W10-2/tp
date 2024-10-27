package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.constants.Assertions;
import seedu.exchangecoursemapper.constants.Logs;
import seedu.exchangecoursemapper.courses.Course;
import seedu.exchangecoursemapper.exception.Exception;
import seedu.exchangecoursemapper.storage.Storage;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.exchangecoursemapper.constants.JsonKey.PU_COURSE_CODE_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.NUS_COURSE_CODE_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.PU_COURSE_NAME_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.NUS_COURSE_NAME_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.COURSES_ARRAY_LABEL;
import static seedu.exchangecoursemapper.constants.Messages.LINE_SEPARATOR;
import static seedu.exchangecoursemapper.constants.Messages.LIST_RELEVANT_PU;


public class AddCoursesCommand extends PersonalTrackerCommand {

    private static final Logger logger = Logger.getLogger(AddCoursesCommand.class.getName());


    private static boolean isValidCourseMapping(String nusCourseInput, String puCourseInput,
                                                JsonArray courses, String pu) {

        for (int i = 0; i < courses.size(); i++) {
            logger.log(Level.INFO, Logs.FIND_COURSE_MAPPING);
            JsonObject course = courses.getJsonObject(i);
            String puCourseCode = course.getString(PU_COURSE_CODE_KEY).toLowerCase();
            String nusCourseCode = course.getString(NUS_COURSE_CODE_KEY).toLowerCase();

            if (puCourseCode.equals(puCourseInput) && nusCourseCode.equals(nusCourseInput)) {
                return true;
            }
        }

        System.out.println("Invalid course mapping!");
        displayAvailableMappings(courses,pu);
        return false;
    }

    private static void displayAvailableMappings(JsonArray courses,String pu) {
        System.out.println("The available mappings for " + pu + " are:");
        System.out.println(LINE_SEPARATOR);

        for (int i = 0; i < courses.size(); i++) {
            JsonObject course = courses.getJsonObject(i);
            String puCourseCode = course.getString(PU_COURSE_CODE_KEY).toLowerCase();
            String nusCourseCode = course.getString(NUS_COURSE_CODE_KEY).toLowerCase();
            String nusCourseName = course.getString(NUS_COURSE_NAME_KEY).toLowerCase();
            String puCourseName = course.getString(PU_COURSE_NAME_KEY).toLowerCase();

            System.out.println(nusCourseCode + " " + nusCourseName + " | "
                    + puCourseCode + " " + puCourseName + System.lineSeparator());
        }
        System.out.println(LINE_SEPARATOR);
    }

    private static JsonArray getPUCourseList(String pu, JsonObject jsonObject) {
        JsonArray courses;
        logger.log(Level.INFO, Logs.FIND_PARTNER_UNIVERSITY);
        String matchPu = jsonObject.keySet()
                .stream()
                .filter(key -> key.equalsIgnoreCase(pu))
                .findFirst()
                .orElse(null);

        logger.log(Level.INFO, Logs.UNIVERSITY_FOUND);
        if (matchPu != null) {
            courses = jsonObject.getJsonObject(matchPu).getJsonArray(COURSES_ARRAY_LABEL);
        } else {
            logger.log(Level.INFO, Logs.INVALID_UNIVERSITY_INPUT);
            System.out.println(Logs.INVALID_UNIVERSITY_INPUT);

            logger.log(Level.INFO, Logs.DISPLAY_PARTNER_UNIVERSITIES);
            System.out.println(LINE_SEPARATOR);
            System.out.println(LIST_RELEVANT_PU);
            System.out.println(LINE_SEPARATOR);
            return null;
        }
        return courses;
    }

    @Override
    public void execute(String userInput, Storage storage) {
        try {
            JsonObject jsonObject = super.createJsonObject();
            logger.log(Level.INFO, Logs.SUCCESS_READ_JSON_FILE);
            assert jsonObject != null : Assertions.NULL_JSON_FILE;
            assert !jsonObject.isEmpty() : Assertions.EMPTY_JSON_FILE;

            logger.log(Level.INFO, Logs.TRIM_STRING);
            String description = trimString(userInput);
            logger.log(Level.INFO, Logs.PARSE_ADD_COMMANDS);
            String[] descriptionSubstrings = parseAddCommand(description);

            assert descriptionSubstrings.length == 3 : Assertions.MISSING_FIELDS;
            logger.log(Level.INFO, Logs.EXTRACT_COURSES);
            String nusCourse = descriptionSubstrings[0].trim().toLowerCase();
            String pu = descriptionSubstrings[1].trim().toLowerCase();
            String puCourse = descriptionSubstrings[2].trim().toLowerCase();

            logger.log(Level.INFO, Logs.FORMAT);
            boolean isValidInput = isValidInput(nusCourse, pu, puCourse, jsonObject);

            if (isValidInput) {
                logger.log(Level.INFO, Logs.ADD_APPROVED_MAPPING);
                Course courseToStore = new Course(puCourse, nusCourse, pu);
                storage.addCourse(courseToStore);
                printAddMessage(courseToStore);
            } else {
                System.out.println(Logs.ADD_NEW_COURSE_MAPPING);
            }

        } catch (IllegalArgumentException | IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public String trimString(String string) {
        String trimmedString = string.trim();

        assert !trimmedString.isEmpty() : Assertions.MISSING_USER_INPUT;
        String[] outputSubstrings = trimmedString.split(" ", 2);

        if (outputSubstrings.length < 2 || outputSubstrings[1].trim().isEmpty()) {
            logger.log(Level.WARNING, Logs.MISSING_INPUT_AFTER_KEYWORD);
            throw new IllegalArgumentException(Exception.noInputAfterAdd());
        }

        logger.log(Level.INFO, Logs.RETURN_TRIMMED_INPUT);
        return outputSubstrings[1];
    }

    public String[] parseAddCommand(String input) {

        input = input.replaceAll("(?i)/pu", "/pu").
                replaceAll("(?i)/coursepu", "/coursepu")
                .trim()
                .replaceAll(" +", " ");

        if ((!input.contains("/pu") || !input.contains("/coursepu"))) {
            logger.log(Level.WARNING, Logs.MISSING_KEYWORDS);
            throw new IllegalArgumentException(Exception.missingKeyword());
        }

        if (input.contains("/pu/coursepu") || input.contains("/coursepu/pu")) {
            logger.log(Level.WARNING, Logs.ADJACENT_KEYWORDS);
            throw new IllegalArgumentException(Exception.adjacentInputError());
        }

        String[] inputSubstrings = input.split(" /coursepu | /pu ");

        if (inputSubstrings.length < 3) {
            logger.log(Level.WARNING, Logs.INVALID_COURSE_CODE);
            throw new IllegalArgumentException(Exception.invalidCourseCodes());
        }

        return inputSubstrings;
    }

    public void printAddMessage(Course addCourse) {
        System.out.println("You have successfully added the course: " + addCourse.formatOutput());
    }

    public boolean isValidInput(String nusCourseInput, String pu,
                                String puCourseInput, JsonObject jsonObject) {

        assert nusCourseInput != null : "NUS course should not be empty";
        assert pu != null : "Partner university should not be empty";
        assert puCourseInput != null : "Partner university course should not be empty";
        assert jsonObject != null : "JSON object should not be empty";

        logger.log(Level.INFO, Logs.CHECK_UNIVERSITY);
        JsonArray courses = getPUCourseList(pu, jsonObject);

        if (courses == null) {
            logger.log(Level.WARNING, Logs.INVALID_UNIVERSITY_INPUT);
            return false;
        }

        logger.log(Level.INFO, Logs.CHECK_COURSE_MAPPING);
        return isValidCourseMapping(nusCourseInput, puCourseInput, courses,pu);
    }
}
