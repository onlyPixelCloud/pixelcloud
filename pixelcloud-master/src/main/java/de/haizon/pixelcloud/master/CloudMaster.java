package de.haizon.pixelcloud.master;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.console.IConsoleSender;
import de.haizon.pixelcloud.master.backend.database.DatabaseNode;
import de.haizon.pixelcloud.master.backend.dependencies.Dependency;
import de.haizon.pixelcloud.master.backend.dependencies.DependencyLoader;
import de.haizon.pixelcloud.master.backend.files.FileManager;
import de.haizon.pixelcloud.master.backend.manager.CloudGroupManager;
import de.haizon.pixelcloud.master.console.ConsoleManager;
import de.haizon.pixelcloud.master.console.ConsoleSender;
import de.haizon.pixelcloud.master.console.command.CommandManager;
import de.haizon.pixelcloud.master.console.SetupWrapper;
import de.haizon.pixelcloud.master.logger.CloudLogger;
import de.haizon.pixelcloud.networking.server.CloudServer;

import java.io.IOException;

public class CloudMaster {

    private static CloudMaster instance;

    private final CloudLogger cloudLogger;
    private final ConsoleManager consoleManager;
    private final CommandManager commandManager;
    private final IConsoleSender consoleSender;
    private final DependencyLoader dependencyLoader;
    private final FileManager fileManager;

    private SetupWrapper setupWrapper;
    private DatabaseNode databaseNode;
    private CloudGroupManager cloudGroupManager;

    private CloudServer cloudServer;

    public static void main(String[] args) {
        try {
            new CloudMaster();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CloudMaster() throws IOException {
        instance = this;

        fileManager = new FileManager();
        fileManager.createDirectories(
                "logs",
                "dependencies",
                "modules",
                "storage",
                "storage/servers/templates",
                "storage/servers/temp",
                "storage/servers/static",
                "templates/EVERY_SERVER",
                "templates/EVERY_PROXY"
        );

        consoleManager = new ConsoleManager();
        cloudLogger = new CloudLogger();
        dependencyLoader = new DependencyLoader();

        dependencyLoader.load(new Dependency("com.google.collections", "google-collections", "1.0"));
        dependencyLoader.load(new Dependency("com.google.code.gson", "gson", "2.10"));
        dependencyLoader.load(new Dependency("org.jline", "jline-reader", "3.19.0"));
        dependencyLoader.load(new Dependency("org.jline", "jline-terminal", "3.19.0"));
        dependencyLoader.load(new Dependency("org.fusesource.jansi", "jansi", "2.3.2"));
        dependencyLoader.load(new Dependency("org.json", "json", "20201115"));
        dependencyLoader.load(new Dependency("mysql", "mysql-connector-java", "8.0.23"));
        dependencyLoader.load(new Dependency("commons-io", "commons-io", "2.11.0"));
        dependencyLoader.load(new Dependency("org.reflections", "reflections", "0.9.12"));
        dependencyLoader.load(new Dependency("org.objenesis", "objenesis", "3.2"));

        //TODO: Inject dependencies
        dependencyLoader.injectClasses();

        cloudLogger.clear();
        cloudLogger.cleared(" ".repeat(20));
        cloudLogger.cleared("     PixelCloud Master " + Color.CYAN.getColor() + "v1.0.0-91c5dd48" + Color.RESET.getColor());
        cloudLogger.cleared("     coded by " + Color.CYAN.getColor() + "Haizoooon" + Color.RESET.getColor());
        cloudLogger.cleared(" ".repeat(20));
        cloudLogger.cleared("     " + Color.GREEN.getColor() + "✔ " + Color.RESET.getColor() + " Successfully loaded " + dependencyLoader.getDependencies().size() + " dependencies");

        consoleSender = new ConsoleSender();
        setupWrapper = new SetupWrapper();
        commandManager = new CommandManager();

        if(!fileManager.fileExists("storage", "database.json")){
//            new DatabaseSetupNode();
            return;
        }
        databaseNode = new DatabaseNode();
        if(databaseNode.connect()){
            cloudLogger.cleared("     " + Color.GREEN.getColor() + "✔ " + Color.RESET.getColor() + " Successfully connected to database");
        } else {
            cloudLogger.cleared("     " + Color.RED.getColor() + "✘ " + Color.RESET.getColor() + " Failed to connect to database");
        }

        cloudGroupManager = new CloudGroupManager();
        if(cloudGroupManager.loadGroups() > 0){
            cloudLogger.cleared("     " + Color.GREEN.getColor() + "✔ " + Color.RESET.getColor() + " Successfully loaded " + cloudGroupManager.getCloudGroups().size() + " cloud groups");
        }

        cloudServer = new CloudServer(cloudLogger);

        cloudLogger.cleared(" ".repeat(20));

    }

    public CloudServer getCloudServer() {
        return cloudServer;
    }

    public CloudGroupManager getCloudGroupManager() {
        return cloudGroupManager;
    }

    public DatabaseNode getDatabaseNode() {
        return databaseNode;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public DependencyLoader getDependencyLoader() {
        return dependencyLoader;
    }

    public IConsoleSender getConsoleSender() {
        return consoleSender;
    }

    public SetupWrapper getSetupWrapper() {
        return setupWrapper;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public CloudLogger getCloudLogger() {
        return cloudLogger;
    }

    public static CloudMaster getInstance() {
        return instance;
    }
}
