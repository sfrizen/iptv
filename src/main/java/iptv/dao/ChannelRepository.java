package iptv.dao;

import iptv.model.Channel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ChannelRepository extends CrudRepository<Channel, Long> {
    List<Channel> findAll();

    List<Channel> findAllByOrderByOrdAsc();
}
