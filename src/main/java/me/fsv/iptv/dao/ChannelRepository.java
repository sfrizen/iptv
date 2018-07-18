package me.fsv.iptv.dao;

import me.fsv.iptv.model.Channel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChannelRepository extends CrudRepository<Channel, Long> {
    List<Channel> findAll();

    List<Channel> findAllByOrderByOrdAsc();

    List<Channel> findAllByGroupNameOrderByNameAsc(String name);
}
