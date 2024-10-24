package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.constants.Logs;

import java.util.logging.Logger;
import java.util.logging.Level;

import static seedu.exchangecoursemapper.constants.Messages.LINE_SEPARATOR;

public class ListCommandsCommand extends Command {

    private static final Logger logger = Logger.getLogger(ListCommandsCommand.class.getName());

    @Override
    public void execute(String userInput) {
        logger.log(Level.INFO, Logs.EXECUTING_COMMAND);
        System.out.println(LINE_SEPARATOR);
        System.out.println("Here are the available commands:");
        System.out.println("filter <subject code> - Filter courses by subject code.");
        System.out.println("set <SCHOOL_NAME> - Set a partner university for course mapping.");
        System.out.println("list schools - List all available partner universities.");
        System.out.println("add <NUS_COURSE_CODE> /pu <NAME_OF_PU> /coursepu <PU_COURSE_CODE> " +
                "- Add mapped courses between NUS and partner universities.");
        System.out.println("obtain <SCHOOL_NAME> /email - Obtain partner university contact email");
        System.out.println("obtain <SCHOOL_NAME> /number - Obtain partner university contact number");
        System.out.println(LINE_SEPARATOR);
        logger.log(Level.INFO, Logs.COMPLETE_EXECUTION);
    }
}
