package seedu.exchangecoursemapper.ui;
import seedu.exchangecoursemapper.parser.Parser;
import seedu.exchangecoursemapper.storage.Storage;

public class UI {
    public void displayGreeting() {
        String greetingMessage = "Welcome to ExchangeCourseMapper! The easiest way to plan your exchange courses.";
        String banner = """
                 ________ _______ _____   ______ _______ _______ _______\s
                |  |  |  |    ___|     |_|      |       |   |   |    ___|
                |  |  |  |    ___|       |   ---|   -   |       |    ___|
                |________|_______|_______|______|_______|__|_|__|_______|
                """;
        System.out.println(banner);
        System.out.println(greetingMessage);
    }

    public void displayExitMessage() {
        String exitMessage = "All the best in planning for your exchange, hope we helped!";
        System.out.println(exitMessage);
    }

    /**
     * Runs the main chat loop of the application. It continuously takes user input
     * and processes it through the {@code Parser} until the user types "bye".
     *
     * @param parser The {@code Parser} object that processes user input.
     */
    public void runChat(Parser parser, Storage storage) {
        String userInput;
        do {
            userInput = parser.getUserInput();
            parser.processUserInput(userInput, storage);
        } while (!userInput.equalsIgnoreCase("bye"));
    }
}
