package de.haizon.pixelcloud.master.console.setups.node;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.http.HttpFetcher;
import de.haizon.pixelcloud.master.backend.json.JsonLib;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupEnd;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupInput;
import de.haizon.pixelcloud.master.console.setups.interfaces.ISetup;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GroupSetupNode extends ISetup {

    private String name, template, versionType, version, serviceType;
    private int maxPlayers, maxServices, minServices, percentageToStartNewService, startPort;

    public GroupSetupNode() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                JsonLib jsonLib = JsonLib.empty();
                jsonLib.append("name", name);
                jsonLib.append("template", template);
                jsonLib.append("versionType", versionType);
                jsonLib.append("version", version);
                jsonLib.append("serviceType", serviceType);
                jsonLib.append("maxPlayers", maxPlayers);
                jsonLib.append("maxServices", maxServices);
                jsonLib.append("minServices", minServices);
                jsonLib.append("percentageToStartNewService", percentageToStartNewService);
                jsonLib.append("startPort", startPort);

                jsonLib.saveAsFile(new File("groups", name + ".json"));

            }
        });

        List<String> versionTypes = new LinkedList<>();
        HttpFetcher.fetchJson("").getArray("types").forEach(version -> versionTypes.add(version.getAsString()));

        setSetupInputs(new SetupInput("Please enter the name of the group") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                if (input.contains(" ")) {
                    CloudMaster.getInstance().getCloudLogger().severe("The name of the group can't contain spaces!");
                    return false;
                } else if (CloudMaster.getInstance().getFileManager().fileExists("groups", input + ".json")) {
                    CloudMaster.getInstance().getCloudLogger().severe("A group with this name already exists!");
                    return false;
                }
                name = input;
                return true;
            }
        }, new SetupInput("Please enter the template of the group") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                if (input.contains(" ")) {
                    CloudMaster.getInstance().getCloudLogger().severe("The template of the group can't contain spaces!");
                    return false;
                }
                if (input.equalsIgnoreCase("create")) {
                    if (CloudMaster.getInstance().getFileManager().folderExists("templates/" + name)) {
                        CloudMaster.getInstance().getCloudLogger().severe("A template with this name already exists!");
                        return false;
                    }
                    CloudMaster.getInstance().getFileManager().createFolder("templates/" + name);
                    template = name;
                    return true;
                }
                if (!CloudMaster.getInstance().getFileManager().fileExists("templates", input + ".json")) {
                    CloudMaster.getInstance().getCloudLogger().severe("The template doesn't exist!");
                    return false;
                }
                return CloudMaster.getInstance().getFileManager().folderExists("templates/" + input);
            }
        }, new SetupInput("Please enter the version type of the group") {
            @Override
            public List<String> getSuggestions() {
                return versionTypes;
            }

            @Override
            public boolean handle(String input) {
                if (!versionTypes.contains(input)) {
                    CloudMaster.getInstance().getCloudLogger().severe("The version type doesn't exist!");
                    return false;
                }
                versionType = input;
                return versionTypes.contains(input);
            }
        });

    }
}
