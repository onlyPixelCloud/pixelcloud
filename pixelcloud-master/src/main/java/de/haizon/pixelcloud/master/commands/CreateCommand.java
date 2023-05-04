package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.console.IConsoleSender;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.api.Command;
import de.haizon.pixelcloud.master.api.ICommandHandler;
import org.jline.reader.Candidate;

import java.util.Arrays;
import java.util.List;

@Command(
        name = "create",
        description = "Create a new service",
        aliases = {"c", "new"}
)
public class CreateCommand implements ICommandHandler {

    @Override
    public void handle(IConsoleSender iCommandSender, String[] args) {

        if(args.length < 2) {
            CloudMaster.getInstance().getCloudLogger().cleared();
            iCommandSender.sendMessage("create group");
            iCommandSender.sendMessage("create template <name>");
            CloudMaster.getInstance().getCloudLogger().cleared();
            return;
        }

        if(args.length == 2){
            if(args[1].equalsIgnoreCase("group")){
//                new GroupSetupNode();
                return;
            }
            if(args[1].equalsIgnoreCase("template")){
                iCommandSender.sendMessage("Please specify a template name!");
            }
        } else if(args.length == 3){
            if(args[1].equalsIgnoreCase("template")){
                String name = args[2];
                if(CloudMaster.getInstance().getFileManager().folderExists("templates/" + name)){
                    iCommandSender.sendMessage("A template with this name already exists!");
                    return;
                }
                CloudMaster.getInstance().getFileManager().createFolder("templates/" + name);
                iCommandSender.sendMessage("Template " + Color.CYAN.getColor() + name + Color.RESET.getColor() + " was created!");
            }
        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return Arrays.asList(new Candidate("group"), new Candidate("template"));
    }

}
