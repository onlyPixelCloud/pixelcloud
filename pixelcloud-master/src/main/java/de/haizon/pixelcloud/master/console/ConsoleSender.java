package de.haizon.pixelcloud.master.console;

import de.haizon.pixelcloud.api.console.IConsoleSender;
import de.haizon.pixelcloud.master.CloudMaster;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ConsoleSender implements IConsoleSender {

    @Override
    public void sendMessage(String message) {
        CloudMaster.getInstance().getCloudLogger().info(message);
    }

}
