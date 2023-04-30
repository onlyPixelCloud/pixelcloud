package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.api.Command;
import de.haizon.pixelcloud.master.api.ICommandHandler;
import de.haizon.pixelcloud.api.console.IConsoleSender;
import org.jline.reader.Candidate;

import java.util.List;

@Command(name = "help", aliases = {"?", "h"}, description = "Shows all commands")
public class HelpCommand implements ICommandHandler {

    @Override
    public void handle(IConsoleSender iCommandSender, String[] args) {

        CloudMaster.getInstance().getCloudLogger().cleared();
        CloudMaster.getInstance().getCommandManager().getCommandHandlers().forEach((command, iCommandHandler) -> {
            iCommandSender.sendMessage(command.name() + " - " + command.description());
        });
        CloudMaster.getInstance().getCloudLogger().cleared();

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
