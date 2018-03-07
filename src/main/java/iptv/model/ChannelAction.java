package iptv.model;

public class ChannelAction {

    private Channel channel;
    private Action action;

    public ChannelAction(Channel channel, Action action) {
        this.channel = channel;
        this.action = action;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ChannelAction{" +
                "channel=" + channel +
                ", action=" + action +
                '}';
    }

    public enum Action {
        ADD, DELETE, UPDATE
    }
}
