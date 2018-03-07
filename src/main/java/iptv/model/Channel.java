package iptv.model;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", ord=" + ord +
                ", ignore=" + ignore +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", newName='" + newName + '\'' +
                ", url='" + url + '\'' +
                ", group=" + group +
                '}';
    }
}
