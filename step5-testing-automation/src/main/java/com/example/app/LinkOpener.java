package com.example.app;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LinkOpener {

    public boolean openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                return true;
            } else {
                System.out.println("Desktop browsing not supported");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error opening link " + url + ": " + e.getMessage());
            return false;
        }
    }

    public boolean isValidUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}