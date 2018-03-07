package me.fsv.iptv.service;

import me.fsv.iptv.model.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class RemoteListHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteListHandler.class);
    private static final String REMOTE_LIST_URL =
            "https://edem.tv/playlists/uplist/c77ca231eafcd71c5701b64b9dd03a6d/edem_pl.m3u8";

    @Autowired
    private PlayListParser playListParser;

    public List<Channel> getChannels() {
        Scanner scanner = null;

        URL url = null;
        try {
            url = new URL(REMOTE_LIST_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream = Objects.requireNonNull(url, "URL can't be null").openStream();
            scanner = new Scanner(inputStream);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

//        try {
//            scanner = new Scanner(new File("/Users/sfrizen/iptv/edem_pl.m3u8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        LOG.info("Loaded remote list from {}: {}", REMOTE_LIST_URL, scanner);
        return playListParser.parse(scanner);
    }

}
