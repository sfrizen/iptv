package me.fsv.iptv.web;

import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.Group;
import me.fsv.iptv.service.LocalListHandler;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ChannelsViewDataProvider {

    @Autowired
    private LocalListHandler localListHandler;


    public TreeNode getNodes() {
        TreeNode rootNode = new DefaultTreeNode();
        List<Channel> channels = localListHandler.getChannels();
        List<Group> groups = localListHandler.getGroups();

        groups.forEach(group -> {
            TreeNode groupNode = new DefaultTreeNode("group", group, rootNode);
            channels.stream()
                    .filter(channel -> group.getId().equals(channel.getGroup().getId()))
                    .sorted(Comparator.comparingInt(Channel::getOrd))
                    .forEach(channel -> new DefaultTreeNode("channel", channel, groupNode));
        });

        return rootNode;
    }

}
