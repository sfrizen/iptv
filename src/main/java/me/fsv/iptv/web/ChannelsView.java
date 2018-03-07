package me.fsv.iptv.web;

import me.fsv.iptv.model.Channel;
import org.primefaces.model.TreeNode;

import javax.annotation.ManagedBean;
import java.util.List;

@ManagedBean
public class ChannelsView {

    private TreeNode root;
    private List<Channel> channels;
    private Channel selectedChannel;

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public Channel getSelectedChannel() {
        return selectedChannel;
    }

    public void setSelectedChannel(Channel selectedChannel) {
        this.selectedChannel = selectedChannel;
    }
}
