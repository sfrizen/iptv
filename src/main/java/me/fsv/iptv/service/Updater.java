package me.fsv.iptv.service;

import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.ChannelAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static me.fsv.iptv.App.EMAIL;

@Service
public class Updater {
    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    @Autowired
    private LocalListHandler localListHandler;
    @Autowired
    private RemoteListHandler remoteListHandler;
    @Autowired
    private ChannelsComparator channelsComparator;

    public void update() {
        LOG.info(EMAIL, "Starting updater {}", LocalDateTime.now());
        List<Channel> remoteChannels = remoteListHandler.getChannels();
        if (remoteChannels.size() > 0) {
            List<Channel> localChannels = localListHandler.getChannels();
            List<ChannelAction> mergedChannels = channelsComparator.compare(localChannels, remoteChannels);
            localListHandler.update(mergedChannels);
        } else {
            LOG.warn(EMAIL, "Remote list is empty");
        }
        LOG.info(EMAIL, "Update finished {}", LocalDateTime.now());
    }
}
