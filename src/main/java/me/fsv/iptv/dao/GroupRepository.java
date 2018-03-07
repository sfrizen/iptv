package me.fsv.iptv.dao;

import me.fsv.iptv.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {
    @Nullable
    Group findByName(String name);

    List<Group> findAll();

    List<Group> findAllByOrderByOrdAsc();
}
