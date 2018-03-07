package iptv.service;

import iptv.model.Channel;
import iptv.model.ChannelAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Updater {

    @Autowired
    private LocalListHandler localListHandler;
    @Autowired
    private RemoteListHandler remoteListHandler;
    @Autowired
    private ChannelsComparator channelsComparator;

    public void update() {
        List<Channel> localChannels = localListHandler.getChannels();
        List<Channel> remoteChannels = remoteListHandler.getChannels();

        List<ChannelAction> mergedChannels = channelsComparator.compare(localChannels, remoteChannels);

        localListHandler.update(mergedChannels);
    }


}
