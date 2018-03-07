package me.fsv.iptv.service;

import me.fsv.iptv.dao.ChannelRepository;
import me.fsv.iptv.dao.GroupRepository;
import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.ChannelAction;
import me.fsv.iptv.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
public class LocalListHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LocalListHandler.class);
    private static final Map<ChannelAction.Action, BiConsumer<LocalListHandler, Channel>> ACTIONS = new HashMap<>();

    static {
        ACTIONS.put(ChannelAction.Action.ADD, LocalListHandler::addChannel);
        ACTIONS.put(ChannelAction.Action.DELETE, LocalListHandler::deleteChannel);
        ACTIONS.put(ChannelAction.Action.UPDATE, LocalListHandler::updateChannel);
    }

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<Channel> getChannels() {
        List<Channel> channels = channelRepository.findAllByOrderByOrdAsc();
        LOG.info("Channels loaded from database: {}", channels.size());
        return channels;
    }

    public List<Group> getGroups() {
        List<Group> groups = groupRepository.findAllByOrderByOrdAsc();
        LOG.info("Groups loaded from database: {}", groups.size());
        return groups;
    }

    @Transactional
    public void update(List<ChannelAction> channelActions) {
        LOG.info("Channels to be updated: {}", channelActions.size());
        channelActions.forEach(channelAction ->
                ACTIONS.get(channelAction.getAction()).accept(this, channelAction.getChannel()));
    }

    private void addChannel(Channel channel) {
        LOG.info("addChannel: {}", channel);
        checkGroup(channel);
        LOG.info("addChannel (after group check): {}", channel);
        channelRepository.save(channel);
    }

    private void deleteChannel(Channel channel) {
        LOG.info("deleteChannel: {}", channel);
        channelRepository.delete(channel);
    }

    private void updateChannel(Channel channel) {
        LOG.info("updateChannel: {}", channel);
        checkGroup(channel);
        LOG.info("updateChannel (after group check): {}", channel);
        channelRepository.findById(channel.getId()).ifPresent(localChannel -> {
            localChannel.setCode(channel.getCode());
            channelRepository.save(channel);
        });
    }

    private void checkGroup(Channel channel) {
        Group group = channel.getGroup();
        Group groupInDb = groupRepository.findByName(group.getName());
        if (groupInDb == null) {
            LOG.info("checkGroup (save new): {}", group);
            channel.setGroup(groupRepository.save(group));
        } else {
            channel.setGroup(groupInDb);
        }
    }

}
