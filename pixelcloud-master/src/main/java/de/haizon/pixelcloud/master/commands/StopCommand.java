package de.haizon.pixelcloud.master.commands;

import de.haizon.pixelcloud.api.console.IConsoleSender;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.api.Command;
import de.haizon.pixelcloud.master.api.ICommandHandler;
import org.jline.reader.Candidate;

import java.util.List;

@Command(name = "stop", description = "Stops the cloud")
public class StopCommand implements ICommandHandler {
    @Override
    public void handle(IConsoleSender iCommandSender, String[] args) {

        iCommandSender.sendMessage("Stopping cloud...");

        //TODO: stop all the services

        try {
            CloudMaster.getInstance().getCloudServer().getNettyServer().shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CloudMaster.getInstance().getConsoleManager().stopThread();
        System.exit(0);


    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
