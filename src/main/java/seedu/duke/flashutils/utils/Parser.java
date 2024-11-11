package seedu.duke.flashutils.utils;

import seedu.duke.flashutils.commands.AddCommand;
import seedu.duke.flashutils.commands.Command;
import seedu.duke.flashutils.commands.DeleteCommand;
import seedu.duke.flashutils.commands.EditCommand;
import seedu.duke.flashutils.commands.FlashbangCommand;
import seedu.duke.flashutils.commands.InvalidCommand;
import seedu.duke.flashutils.commands.QuitCommand;
import seedu.duke.flashutils.commands.SearchCommand;
import seedu.duke.flashutils.commands.ViewAllCommand;
import seedu.duke.flashutils.commands.ViewCommand;


import seedu.duke.flashutils.types.Card;
import seedu.duke.flashutils.types.FlashBook;
import seedu.duke.flashutils.types.FlashCardSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private enum CommandType { Add, Delete, Edit, View, FlashBang, Quit, Invalid, Search }

    private static CommandType parseCommandType(String input) {
        String commandKeyword = "^(\\badd\\b|\\bdelete\\b|\\bedit\\b|\\bview\\b|\\bflashbang\\b|\\bquit\\b" +
                "|\\bsearch\\b)";
        Pattern commandPattern = Pattern.compile(commandKeyword);
        Matcher matcher = commandPattern.matcher(input);
        if (matcher.find()) {
            return switch (matcher.group(1).toLowerCase()) {
            case "add" -> CommandType.Add;
            case "delete" -> CommandType.Delete;
            case "edit" -> CommandType.Edit;
            case "view" -> CommandType.View;
            case "flashbang" -> CommandType.FlashBang;
            case "search" -> CommandType.Search;
            case "quit" -> CommandType.Quit;
            default -> CommandType.Invalid;
            };
        }
        return CommandType.Invalid;
    }

    public static Command parseCommand(String input) {
        CommandType commandType = parseCommandType(input);
        return switch (commandType) {
        case Add -> createAddCommand(input);
        case Delete -> createDeleteCommand(input);
        case Edit -> createEditCommand(input);
        case View -> createViewCommand(input);
        case FlashBang -> createFlashbangCommand(input);
        case Search -> createSearchCommand(input);
        case Quit -> createQuitCommand();
        default -> new InvalidCommand();
        };
    }

    public static Command createAddCommand(String input) {
        Pattern addPattern = Pattern.compile("--m\\s+(.+?)(?:\\s+--t\\s+(.+))?\\s+--q\\s+(.+?)\\s+--a\\s+(.+)");
        Matcher matcher = addPattern.matcher(input);
        if (matcher.find()) {
            String moduleName = matcher.group(1);
            String topic = matcher.group(2);
            FlashCardSet module = FlashBook.getInstance().getFlashCardSet(moduleName);
            String question = matcher.group(3);
            String answer = matcher.group(4);
            if (topic == null) {
                topic = "";
            }
            assert !(module == null || question == null || answer == null);
            return new AddCommand(module, new Card(question, answer, topic));
        } else {
            return new InvalidCommand();
        }
    }

    public static Command createDeleteCommand(String input) {
        Pattern deletePattern = Pattern.compile("--m\\s+(.+?)\\s+--i\\s+(\\d+)");
        Matcher matcher = deletePattern.matcher(input);
        if (matcher.find()) {
            String moduleName = matcher.group(1);
            FlashCardSet module = FlashBook.getInstance().getFlashCardSet(moduleName);
            int index = Integer.parseInt(matcher.group(2));
            return new DeleteCommand(module, index);
        } else {
            return new InvalidCommand();
        }
    }

    public static Command createEditCommand(String input) {
        Pattern editPattern = Pattern.compile("--m\\s+(.+?)\\s+--i\\s+(\\d+)\\s+(--q\\s+(.+?)\\s+--a\\s+(.+))?");
        Matcher matcher = editPattern.matcher(input);

        if (matcher.find()) {
            String moduleName = matcher.group(1);
            FlashCardSet module = FlashBook.getInstance().getFlashCardSet(moduleName);
            int index = Integer.parseInt(matcher.group(2));

            // Check if new question and answer are provided in the input
            if (matcher.group(4) != null && matcher.group(5) != null) {
                // Use the provided question and answer
                String newQuestion = matcher.group(4);
                String newAnswer = matcher.group(5);
                return new EditCommand(module, index, newQuestion, newAnswer);
            } else {
                // No question and answer provided; create EditCommand with prompts
                return new EditCommand(module, index);
            }
        } else {
            return new InvalidCommand();
        }
    }

    public static Command createViewCommand(String input) {
        Pattern viewModulePattern = Pattern.compile("--m\\s+(.+)");
        Matcher matcher = viewModulePattern.matcher(input);
        Pattern viewAllModulePattern = Pattern.compile("--all");
        Matcher matcherAll = viewAllModulePattern.matcher(input);
        if (matcher.find()) {
            String moduleName = matcher.group(1);
            FlashCardSet module = FlashBook.getInstance().getFlashCardSet(moduleName);
            return new ViewCommand(module);
        } else if (matcherAll.find()) {
            return new ViewAllCommand();
        } else {
            return new InvalidCommand();
        }
    }

    public static Command createFlashbangCommand(String input) {
        Pattern flashbangPattern = Pattern.compile("--m\\s+(.+?)\\s+(--t\\s+.+)?");
        Matcher matcher = flashbangPattern.matcher(input);
        if (matcher.find()) {
            String moduleName = matcher.group(1);
            String timer = matcher.group(2) != null ? matcher.group(2).trim() : "";
            FlashCardSet module = FlashBook.getInstance().getFlashCardSet(moduleName);
            if (!timer.isEmpty()) {
                try{
                    long milliseconds = parseTimer(timer);
                    return new FlashbangCommand(module, milliseconds);
                } catch (IllegalArgumentException e){
                    Ui.printResponse(e.getMessage());
                }
            }
            return new FlashbangCommand(module);
        } else {
            return new InvalidCommand();
        }
    }
    public static Command createSearchCommand(String input) {
        Pattern searchPattern = Pattern.compile("--m\\s+(.+?)(\\s+/t)?\\s+--s\\s+(.+)");
        Matcher searchMatcher = searchPattern.matcher(input);
        if (searchMatcher.find()) {
            String module = searchMatcher.group(1);
            boolean byTopic = searchMatcher.group(2).trim().equals("/t");
            System.out.println(searchMatcher.group(2));
            String searchTerm = searchMatcher.group(3);
            assert (!(module == null || searchTerm == null));
            return new SearchCommand(searchTerm, byTopic, FlashBook.getInstance().getFlashCardSet(module));
        } else {
            return new InvalidCommand();
        }
    }

    private static long parseTimer(String timer) {
        timer = timer.trim().toLowerCase();

        String[] parts = timer.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid timer format. Expected format: '<number> <unit>'");
        }

        // Parse the number part
        double value;
        try {
            value = Double.parseDouble(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + parts[0]);
        }

        // Determine the unit part
        String unit = parts[1];

        return switch (unit) {
        case "second", "seconds" -> (long) (value * 1000);
        case "minute", "minutes" -> (long) (value * 1000 * 60);
        default -> throw new IllegalArgumentException("Unsupported time unit: " + unit);
        };
    }

    public static Command createQuitCommand() {
        return new QuitCommand();
    }
}
