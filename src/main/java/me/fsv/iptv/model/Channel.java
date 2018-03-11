package me.fsv.iptv.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", sequenceName = "channel_sequence")
    private Long id;
    private Integer ord;
    private Boolean ignore = false;
    private Integer code;
    private String name;
    private String newName;
    private String url;
    @ManyToOne
    private Group group;

}
