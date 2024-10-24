package seedu.exchangecoursemapper.exception;

public class Exception extends Throwable {
    public Exception(String message) {
        super(message);
    }

    public static String fileReadError() {
        return "Error reading the file.";
    }

    public static String emptyUniversityName() {
        return "Please provide a University name.";
    }

    public static String noInputAfterAdd() {
        return "Please provide the nus course code, " +
                "name of partner university (PU) and the PU course code.";
    }

    public static String missingKeyword() {
        return "Please provide all of the valid commands: /pu, /coursepu!";
    }

    public static String adjacentInputError() {
        return "Commands shouldn't be adjacent to one another!";
    }

    public static String invalidCourseCodes() {
        return "Please provide a valid NUS course code or PU or PU's course code!";
    }

    public static String missingNusCourseCode() {
        return "Please provide the course code you would like to search for.";
    }

    public static String filterCoursesLimitExceeded() {
        return "Please note that we can only filter for only one NUS Course!";
    }

    public static String noInputAfterDelete() {
        return "Please provide the index of the course plan you would like to delete.";
    }

    public static String invalidCourseListIndex() {
        return "Please provide a valid index of the course plan you would like to delete.";
    }

    public static String invalidContactType() {
        return "Invalid contact type.";
    }

    public static String invalidInputFormat() {
        return "Invalid input format";
    }

}
