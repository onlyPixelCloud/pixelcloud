package de.haizon.pixelcloud.master.backend.database;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.json.JsonLib;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseNode {

    private String host, database, username, password;
    private int port;

    private Connection connection;

    public DatabaseNode() {

        JsonLib jsonLib = JsonLib.fromJsonFile(new File("storage", "database.json"));

        if(jsonLib == null) {
            CloudMaster.getInstance().getCloudLogger().severe("The database.json file does not exist or is invalid!");
            return;
        }

        host = jsonLib.getString("host");
        database = jsonLib.getString("database");
        username = jsonLib.getString("username");
        password = jsonLib.getString("password");
        port = jsonLib.getInt("port");

    }

    public boolean connect(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

}
