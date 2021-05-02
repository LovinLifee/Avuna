package net.avuna.util;

import lombok.RequiredArgsConstructor;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringJoiner;

@RequiredArgsConstructor
public class URLBuilder {

    private final String domain;
    private StringJoiner folders = new StringJoiner("/", "/", "/");
    private StringBuilder params = new StringBuilder();
    private String connType;

    public void setConnectionType(String conn) {
        connType = conn;
    }

    public void addSubfolder(String folder) {
        folders.add(folder);
    }

    public void addParameter(String parameter, String value) {
        if(params.toString().length() > 0) {
            params.append("&");
        }
        params.append(parameter);
        params.append("=");
        params.append(value);
    }

    public String getURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(connType, domain, folders.toString(), params.toString(), null);
        return uri.toURL().toString();
    }

    public String getRelativeURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(null, null, folders.toString(), params.toString(), null);
        return uri.toString();
    }
}