package me.fsv.iptv.web;

import me.fsv.iptv.model.Channel;
import me.fsv.iptv.service.LocalListHandler;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelsViewDataProvider {

    @Autowired
    private LocalListHandler localListHandler;


    public TreeNode getGroups() {
        TreeNode root = new DefaultTreeNode();

        localListHandler.getGroups().forEach(group -> {
            root.getChildren().add(new DefaultTreeNode(group));
        });

        return root;
    }

    public List<Channel> getChannels() {
        return localListHandler.getChannels();

    }
}
