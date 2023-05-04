package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.console.IConsoleSender;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.api.Command;
import de.haizon.pixelcloud.master.api.ICommandHandler;
import org.jline.reader.Candidate;

import java.util.List;

@Command(name = "list", description = "Lists all services, groups or players")
public class ListCommand implements ICommandHandler {
    @Override
    public void handle(IConsoleSender iCommandSender, String[] args) {
        CloudMaster.getInstance().getCloudLogger().cleared();
        CloudMaster.getInstance().getCloudGroupManager().getCloudGroups().forEach(iCloudGroup -> {
            iCommandSender.sendMessage(iCloudGroup.getName() + "(" + Color.CYAN.getColor() + iCloudGroup.getMaxServiceCount() + Color.RESET.getColor() + " services)");
            for(int i = 0; i < iCloudGroup.getMaxServiceCount(); i++){
                iCommandSender.sendMessage("- " + Color.CYAN.getColor() + iCloudGroup.getName() + "-" + i + Color.RESET.getColor());
            }
        });

        CloudMaster.getInstance().getCloudLogger().cleared();
    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
