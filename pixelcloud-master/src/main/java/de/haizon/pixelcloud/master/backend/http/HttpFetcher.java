package de.haizon.pixelcloud.master.backend.http;

import de.haizon.pixelcloud.master.backend.json.JsonLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpFetcher {

    public static JsonLib fetchJson(String urlString) {
        return JsonLib.fromJsonString(fetch(urlString));
    }

    public static String fetch(String urlString) {
        try {
            var url = new URL(urlString);
            var reader = new BufferedReader(new InputStreamReader(url.openStream()));
            var line = new StringBuilder();
            reader.lines().forEach(line::append);
            reader.close();
            return line.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
