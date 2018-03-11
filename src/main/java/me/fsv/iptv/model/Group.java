package me.fsv.iptv.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ChannelsGroup")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", sequenceName = "group_sequence")
    private Long id;
    private Integer ord;
    private Boolean ignore = false;
    private String name;
    private String newName;

}
