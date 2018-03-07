package me.fsv.iptv.service;

import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.ChannelAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChannelsComparator {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelsComparator.class);

    public List<ChannelAction> compare(List<Channel> localChannels, List<Channel> remoteChannels) {
        List<ChannelAction> channelActions = new ArrayList<>();
        localChannels.addAll(remoteChannels);
        Map<String, List<Channel>> grouppedChannels = localChannels.stream()
                .collect(Collectors.groupingBy(Channel::getName));

        grouppedChannels.forEach((name, channels) -> {
            if (channels.size() == 1) {
                if (channels.get(0).getId() == null) {
                    //added
                    Channel remote = channels.get(0);
                    LOG.info("Channel '{}' not found in local list. Adding to local '{}'", remote.getName(), remote);
                    channelActions.add(new ChannelAction(remote, ChannelAction.Action.ADD));
                } else {
                    //removed
                    Channel local = channels.get(0);
                    LOG.info("Channel '{}' not found in remote list. Deleting from local '{}'", local.getName(), local);
                    channelActions.add(new ChannelAction(local, ChannelAction.Action.DELETE));
                }
            } else if (channels.size() == 2) {
                Channel local = channels.get(0).getId() != null ? channels.get(0) : channels.get(1);
                Channel remote = channels.get(0).getId() == null ? channels.get(0) : channels.get(1);
                if (!local.getCode().equals(remote.getCode())) {
                    //code changed
                    local.setCode(remote.getCode());
                    LOG.info("Channel '{}' code changed in remote list. Updating in local '{}'", local.getName(), local);
                    channelActions.add(new ChannelAction(local, ChannelAction.Action.UPDATE));
                } else {
                    LOG.info("Channel '{}' is equal in local and remote list. Skipping '{}'", local.getName(), local);
                }
            } else {
                LOG.error("Not 1 or 2 equal channels {}", channels);
            }
        });
        LOG.info("Channels to be updated ({}): {}", channelActions.size(), channelActions);
        return channelActions;
    }

}
