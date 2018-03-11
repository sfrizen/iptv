package me.fsv.iptv.web;

import me.fsv.iptv.model.Channel;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class ChannelsView {

    @Autowired
    private ChannelsViewDataProvider channelsViewDataProvider;

    private TreeNode nodes;
    private List<Channel> channels;
    private Channel selectedChannel;

    @PostConstruct
    private void init() {
        nodes = channelsViewDataProvider.getNodes();
    }

    public TreeNode getNodes() {
        return nodes;
    }

    public void setNodes(TreeNode nodes) {
        this.nodes = nodes;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public Channel getSelectedChannel() {
        return selectedChannel;
    }

    public void setSelectedChannel(Channel selectedChannel) {
        this.selectedChannel = selectedChannel;
    }
}
