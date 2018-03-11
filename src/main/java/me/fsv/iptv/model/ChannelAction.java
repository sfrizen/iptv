package me.fsv.iptv.model;

import lombok.Data;

@Data
public class ChannelAction {

    private Channel channel;
    private Action action;

    public ChannelAction(Channel channel, Action action) {
        this.channel = channel;
        this.action = action;
    }

    public enum Action {
        ADD, DELETE, UPDATE
    }

}
