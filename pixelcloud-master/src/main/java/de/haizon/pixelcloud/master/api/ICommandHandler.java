package de.haizon.pixelcloud.master.api;

import de.haizon.pixelcloud.api.console.IConsoleSender;
import org.jline.reader.Candidate;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public interface ICommandHandler {

    void handle(IConsoleSender iCommandSender, String[] args);

    List<Candidate> getSuggestions();

}
