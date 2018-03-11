package me.fsv.iptv.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class PlayList {

    @Id
    private Long id;
    private String name;
    private String host;
    private String key;

}
