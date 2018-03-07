package me.fsv.iptv.service;

import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PlayListParser {

    private static final Logger LOG = LoggerFactory.getLogger(PlayListParser.class);
    private static final String URL_PATTERN = "^http://([\\w.]+)/iptv/(\\w+)/(\\d+)/index.m3u8";
    private static final Pattern PATTERN = Pattern.compile(URL_PATTERN);

    private List<Channel> channels;
    private Map<String, Group> groups;

    public List<Channel> parse(Scanner scanner) {
        channels = new ArrayList<>();
        groups = new HashMap<>();

        if (scanner != null) {
            scanner = scanner.useDelimiter("[\r\n]+");

            Channel channel = null;

            while (scanner.hasNext()) {
                String row = scanner.next().trim();

                if (row.startsWith("#EXTINF:")) {
                    addChannel(channel);
                    channel = new Channel();
                    channel = processChannelRow(channel, row);
                }

                if (row.startsWith("#EXTGRP:")) {
                    processGroupRow(channel, row);
                }

                if (row.startsWith("http")) {
                    processUrlRow(channel, row);
                }

            }

            addChannel(channel);
        }

        return channels;
    }

    private void addChannel(Channel channel) {
        if (channel != null) {
            channels.add(channel);
        }
    }

    private Channel processChannelRow(Channel channel, String row) {
        String[] rowItems = row.split(",");
        channel.setName(rowItems[rowItems.length - 1].trim());
        channel.setNewName(channel.getName());
        channel.setOrd(Integer.MAX_VALUE);
        return channel;
    }

    private void processGroupRow(Channel channel, String row) {
        if (channel != null) {
            if (channel.getGroup() != null) {
                LOG.error("Duplicate group in channel descriptor: {}", channel);
            }
            String[] rowItems = row.split(":");
            String groupName = rowItems[rowItems.length - 1].trim();
            Group group = groups.get(groupName);
            if (group == null) {
                group = new Group();
                group.setName(groupName);
                group.setNewName(groupName);
                group.setOrd(Integer.MAX_VALUE);
            }
            channel.setGroup(group);
        } else {
            LOG.error("Processing group descriptor while channel is null: {}", row);
        }
    }

    private void processUrlRow(Channel channel, String row) {
        if (channel != null) {
            if (channel.getUrl() != null) {
                LOG.error("Duplicate URL in channel descriptor: {}", channel);
            }
            channel.setUrl(row);
            channel.setCode(getChannelCode(row));
        }
    }

    private Integer getChannelCode(String url) {
        Matcher matcher = PATTERN.matcher(url);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(3));
        } else {
            throw new IllegalArgumentException("No channel code in the given URL: " + url);
        }
    }

}
