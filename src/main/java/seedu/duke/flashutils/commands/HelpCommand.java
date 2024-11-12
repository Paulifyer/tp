package seedu.duke.flashutils.commands;

public class HelpCommand extends Command {
    @Override
    public CommandResult execute() {
        String availableCommands = "Available Commands: \n"
                + " 1. Add a flashcard: \n"
                + " \t add --m [Module Name] {--t [Topic] (optional)} --q [Question] --a [Answer] \n"
                + " 2. View all flashcards of a module: \n"
                + " \t view --m [Module Name] \n"
                + " 3. View all flashcards: \n"
                + " \t view --all\n"
                + " 4. Delete a flashcard: \n"
                + " \t delete --m [Module Name] --i [Index] \n"
                + " \t delete --m [Module Name] \n"
                + " 5. Edit a flashcard: \n"
                + " \t edit --m [Module Name] --i [Index] --q [New Question] --a [New Answer] \n"
                + " 6. Flashbang - view all the flashcards of a module without seeing the answers: \n"
                + " \t flashbang --m [Module Name] --t [time] [unit (second/seconds/minute/minutes)]\n"
                + " 7. Search for flashcards: \n"
                + " \t search --m [Module Name] {/t (optional)} --s [Search Term] \n"
                + " 8. Quit the app: \n"
                + " \t quit";

        return new CommandResult(availableCommands);
    }
}
