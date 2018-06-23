package me.fsv.iptv.service;

import me.fsv.iptv.dao.PlayListRepository;
import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.Group;
import me.fsv.iptv.model.PlayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayListGenerator {

    @Autowired
    private LocalListHandler listHandler;
    @Autowired
    private PlayListRepository playListRepository;

    public String generate(String name) {
        List<Group> groups = listHandler.getGroups();
        List<Channel> channels = listHandler.getChannels();
        PlayList list = playListRepository.findByName(name);
        return generatePlayList(list, groups, channels);
    }

    private String generatePlayList(PlayList playList, List<Group> groups, List<Channel> channels) {
        StringBuilder res = new StringBuilder();
        groups.stream()
                .filter(group -> !group.getIgnore())
                .forEach(group -> channels.stream()
                        .filter(channel -> channel.getGroup().getId().equals(group.getId()))
                        .filter(channel -> !channel.getIgnore())
                        .sorted(Comparator.comparing(ch -> ch.getName()))
                        .forEach(channel -> res.append("#EXTINF:0,")
                                .append(channel.getNewName() != null ? channel.getNewName() : channel.getName())
                                .append("\n")
                                .append("#EXTGRP:")
                                .append(group.getName())
                                .append("\n")
                                .append(buildUrl(playList, channel))
                                .append("\n")));
        return res.toString();
    }

    private String buildUrl(PlayList playList, Channel channel) {
        return "http://" + playList.getHost() + "/iptv/" + playList.getKey() + "/" + channel.getCode() + "/index.m3u8";
    }

}
