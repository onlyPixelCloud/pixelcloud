package de.haizon.pixelcloud.master.console.setups.node;

import de.haizon.pixelcloud.master.backend.json.JsonLib;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupEnd;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupInput;
import de.haizon.pixelcloud.master.console.setups.interfaces.ISetup;

import java.io.File;
import java.net.InetAddress;;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class DatabaseSetupNode extends ISetup {

    private String host, database, username, password;
    private int port;

    public DatabaseSetupNode() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                JsonLib jsonLib = JsonLib.empty();
                jsonLib.append("host", host);
                jsonLib.append("database", database);
                jsonLib.append("username", username);
                jsonLib.append("password", password);
                jsonLib.append("port", port);

                jsonLib.saveAsFile(new File("storage", "database.json"));

            }
        });

        setSetupInputs(new SetupInput("Please enter the host of the database") {
            @Override
            public List<String> getSuggestions() {
                try {
                    return Arrays.asList("localhost", "127.0.0.1", InetAddress.getLocalHost().getHostAddress());
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public boolean handle(String input) {
                host = input;
                return true;
            }
        }, new SetupInput("Please enter the database name") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                database = input;
                return true;
            }
        }, new SetupInput("Please enter the username of the database") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                username = input;
                return true;
            }
        }, new SetupInput("Please enter the password of the database") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                password = input;
                return true;
            }
        }, new SetupInput("Please enter the port of the database") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                if(input.matches("[0-9]+")) {
                    port = Integer.parseInt(input);
                    return true;
                }
                return false;
            }
        });

    }
}
