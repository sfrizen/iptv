package me.fsv.iptv.service;

import me.fsv.iptv.model.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class RemoteListHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RemoteListHandler.class);

    @Autowired
    private PlayListParser playListParser;
    @Value("${list.url}")
    private URL url;

    public List<Channel> getChannels() {
        Scanner scanner = null;

        try {
            InputStream inputStream = Objects.requireNonNull(url, "URL can't be null").openStream();
            scanner = new Scanner(inputStream);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

        LOG.info("Loaded remote list from {}: {}", url, scanner);
        return playListParser.parse(scanner);
    }

}
