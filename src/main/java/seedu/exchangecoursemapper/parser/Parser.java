package seedu.exchangecoursemapper.parser;

import seedu.exchangecoursemapper.command.FilterCoursesCommand;
import seedu.exchangecoursemapper.command.ListCommandsCommand;
import seedu.exchangecoursemapper.command.ListSchoolCommand;
import seedu.exchangecoursemapper.command.ListUniCoursesCommand;
import seedu.exchangecoursemapper.command.AddCoursesCommand;
import seedu.exchangecoursemapper.command.DeleteCoursesCommand;
import seedu.exchangecoursemapper.command.ObtainContactsCommand;
import seedu.exchangecoursemapper.storage.Storage;
import seedu.exchangecoursemapper.ui.UI;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.exchangecoursemapper.constants.Commands.COMMANDS;
import static seedu.exchangecoursemapper.constants.Commands.LISTINGSCHOOLS;
import static seedu.exchangecoursemapper.constants.Commands.FILTER_COURSES;
import static seedu.exchangecoursemapper.constants.Commands.SET;
import static seedu.exchangecoursemapper.constants.Commands.ADD_COURSES;
import static seedu.exchangecoursemapper.constants.Commands.DELETE_COURSES;
import static seedu.exchangecoursemapper.constants.Commands.BYE;
import static seedu.exchangecoursemapper.constants.Commands.OBTAIN;
import static seedu.exchangecoursemapper.constants.Commands.COMMAND_WORD_INDEX;
import static seedu.exchangecoursemapper.constants.Logs.RECEIVED_INPUT;
import static seedu.exchangecoursemapper.constants.Logs.NULL_INPUT;
import static seedu.exchangecoursemapper.constants.Logs.EMPTY_INPUT_DETAILS;
import static seedu.exchangecoursemapper.constants.Logs.INVALID_INPUT;
import static seedu.exchangecoursemapper.constants.Messages.INVALID_COMMAND_MESSAGE;
import static seedu.exchangecoursemapper.constants.Regex.SPACE;

public class Parser {

    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    private final Scanner scanner = new Scanner(System.in);

    private final UI mapperUI = new UI();

    public String getUserInput() {
        return scanner.nextLine();
    }

    public void processUserInput(String userInput, Storage storage) {
        // Assert that userInput is not null
        assert userInput != null : NULL_INPUT;

        String input = userInput.trim();
        String[] inputDetails = input.split(SPACE);

        // Log user input
        logger.log(Level.INFO, RECEIVED_INPUT, input);

        // Assert that inputDetails array is not empty
        assert inputDetails.length > 0 : EMPTY_INPUT_DETAILS;

        String command = inputDetails[COMMAND_WORD_INDEX];

        if (input.equals(LISTINGSCHOOLS)) {
            new ListSchoolCommand().execute(input);
        } else if (command.equals(COMMANDS)) {
            new ListCommandsCommand().execute(input);
        } else if (command.equals(FILTER_COURSES)) {
            new FilterCoursesCommand().execute(input);
        } else if (command.equals(ADD_COURSES)) {
            new AddCoursesCommand().execute(input, storage);
        } else if (command.equals(DELETE_COURSES)) {
            new DeleteCoursesCommand().execute(input, storage);
        } else if (command.equals(SET)) {
            new ListUniCoursesCommand().execute(input);
        } else if (command.equals(OBTAIN)) {
            new ObtainContactsCommand().execute(input);
        } else if (command.equals(BYE)) {
            mapperUI.displayExitMessage();
        } else {
            logger.log(Level.WARNING, INVALID_INPUT, command);
            System.out.println(INVALID_COMMAND_MESSAGE);
        }
    }
}
