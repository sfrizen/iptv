package me.fsv.iptv.service;

import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.ChannelAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static me.fsv.iptv.App.EMAIL;

@Service
public class ChannelsComparator {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelsComparator.class);

    public List<ChannelAction> compare(List<Channel> localChannels, List<Channel> remoteChannels) {
        List<ChannelAction> channelActions = new ArrayList<>();
        localChannels.addAll(remoteChannels);
        Map<Integer, List<Channel>> groupedChannels = localChannels.stream()
                .collect(Collectors.groupingBy(Channel::getCode));

        groupedChannels.forEach((name, channels) -> {
            if (channels.size() == 1) {
                if (channels.get(0).getId() == null) {
                    //added
                    Channel remote = channels.get(0);
                    LOG.info("Channel ({}) '{}' not found in local list. Adding to local.", remote.getCode(), remote.getName());
                    channelActions.add(new ChannelAction(remote, ChannelAction.Action.ADD));
                } else {
                    //removed
                    Channel local = channels.get(0);
                    LOG.info("Channel ({}) '{}' not found in remote list. Deleting from local.", local.getCode(), local.getName());
                    channelActions.add(new ChannelAction(local, ChannelAction.Action.DELETE));
                }
            } else if (channels.size() == 2) {
                Channel local = null;
                Channel remote = null;
                if (channels.get(0).getId() != null && channels.get(1).getId() == null) {
                    local = channels.get(0);
                    remote = channels.get(1);
                } else if (channels.get(0).getId() == null && channels.get(0).getId() != null) {
                    local = channels.get(1);
                    remote = channels.get(0);
                } else if (channels.get(0).getId() == null && channels.get(1).getId() == null) {
                    LOG.warn(EMAIL, "Duplicates came from remote list {}", channels);
                } else if (channels.get(0).getId() != null && channels.get(1).getId() != null) {
                    LOG.warn(EMAIL, "Duplicates found in local list {}", channels);
                }

                if (local != null && remote != null) {
                    if (!local.getName().equals(remote.getName())) {
                        //name changed
                        local.setName(remote.getName());
                        LOG.info("Channel name changed from '{}' to '{}'. Updating in local.", local.getName(), remote.getName());
                        channelActions.add(new ChannelAction(local, ChannelAction.Action.UPDATE));
                    } else {
                        LOG.info("Channel ({}) '{}' is equal in local and remote list. Skipping.", local.getCode(), local.getName());
                    }
                }
            } else {
                LOG.warn(EMAIL, "!!! More than 1 channels in local and remote lists with the same code {}", channels);
            }
        });
        LOG.info("Channels to be updated: {}", channelActions.size());
        return channelActions;
    }

}
