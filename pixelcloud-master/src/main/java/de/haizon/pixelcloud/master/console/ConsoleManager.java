package de.haizon.pixelcloud.master.console;

import de.haizon.pixelcloud.master.api.ICommandHandler;
import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.master.CloudMaster;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ConsoleManager {

    private final LineReader lineReader;
    private Thread thread;
    private final ConsoleCompleter consoleCompleter;

    private final String prompt =
            Color.RESET.getColor()
                    + Color.RESET.getColor() + "@"
                    + Color.CYAN.getColor() + "cloud"
                    + Color.RESET.getColor() + " » ";

    public ConsoleManager() {
        consoleCompleter = new ConsoleCompleter();
        lineReader = createLineReader();
        startThread();
    }

    private LineReader createLineReader() {

        Terminal terminal = null;
        try {
            System.setProperty("org.jline.terminal.dumb", "true");
            terminal = TerminalBuilder.builder().system(true).streams(System.in, System.out).encoding(StandardCharsets.UTF_8).dumb(true).build();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return LineReaderBuilder.builder()
                .completer(consoleCompleter)
                .terminal(terminal)
                .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                .option(LineReader.Option.AUTO_REMOVE_SLASH, false)
                .option(LineReader.Option.INSERT_TAB, false)
                .build();
    }

    public void startThread() {
        thread = new Thread(() -> {
            try {
                String line;
                while (!Thread.currentThread().isInterrupted()) {
                    line = lineReader.readLine(prompt);
                    handleInput(line);
                }
            } catch (UserInterruptException ignored) {
            }
        });
        thread.start();
    }

    public void handleInput(String input) {

        SetupWrapper setupWrapper = CloudMaster.getInstance().getSetupWrapper();

        if (setupWrapper.isRunning()) {
            if (input.equalsIgnoreCase("end")) {
                setupWrapper.cancel();
                return;
            }
            setupWrapper.handle(input);
            return;
        }

        if (input.isEmpty()) {
            return;
        }

        String[] args = input.split(" ");
        String command = args[0];
        if (CloudMaster.getInstance().getCommandManager().getCommandHandlerByName(command) == null) {
            CloudMaster.getInstance().getCloudLogger().warning("The command could not be found! Please type 'help' for help.");
            return;
        }
        ICommandHandler commandHandler = CloudMaster.getInstance().getCommandManager().getCommandHandlerByName(command);
        commandHandler.handle(CloudMaster.getInstance().getConsoleSender(), args);
    }

    public void stopThread() {
        lineReader.getTerminal().reader().shutdown();
        lineReader.getTerminal().pause();
        thread.interrupt();
    }

    public LineReader getLineReader() {
        return lineReader;
    }

    public ConsoleCompleter getConsoleCompleter() {
        return consoleCompleter;
    }

    public String getPrefix() {
        return prompt;
    }

}
