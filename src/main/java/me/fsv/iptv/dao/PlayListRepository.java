package me.fsv.iptv.dao;

import me.fsv.iptv.model.PlayList;
import org.springframework.data.repository.CrudRepository;

public interface PlayListRepository extends CrudRepository<PlayList, Long> {
    PlayList findByName(String name);
}
