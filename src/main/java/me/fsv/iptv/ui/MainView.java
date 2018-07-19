package me.fsv.iptv.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import me.fsv.iptv.dao.ChannelRepository;
import me.fsv.iptv.dao.GroupRepository;
import me.fsv.iptv.model.Channel;
import me.fsv.iptv.model.Group;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route("")
@UIScope
@SpringComponent
public class MainView extends VerticalLayout {

    private final Grid<Channel> grid = new Grid<>();
    private final Tabs tabs = new Tabs();
    private ChannelRepository channelRepository;
    private Set<Group> groups;

    public MainView(GroupRepository groupRepository, ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;

        groups = new HashSet<>(groupRepository.findAllByOrderByOrdAsc());

        groups.forEach(group -> tabs.add(new Tab(group.getName())));
        grid.setItems(loadChannels());

        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.addSelectedChangeListener(event -> grid.setItems(loadChannels()));

        grid.addColumn(Channel::getId).setHeader("Id").setFlexGrow(0).setWidth("75px");
        grid.addColumn(Channel::getName).setHeader("Name").setFlexGrow(1).setResizable(true);
        grid.addComponentColumn(this::getGroupComboBox).setHeader("Group").setResizable(true);
        grid.addColumn(Channel::getUrl).setHeader("Url").setResizable(true);
        grid.addComponentColumn(channel -> new Checkbox(!channel.getIgnore()))
                .setHeader("Ignore").setFlexGrow(0).setWidth("75px");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        Button button = new Button(VaadinIcon.REFRESH.create(), event ->
                Notification.show("Done", 2000, Notification.Position.TOP_END));

        add(button, tabs, grid);
        setSizeFull();
    }

    private ComboBox<Group> getGroupComboBox(Channel channel) {
        ComboBox<Group> groupComboBox = new ComboBox<>(null, groups);
        groupComboBox.setItemLabelGenerator(Group::getName);
        groupComboBox.setValue(channel.getGroup());
        groupComboBox.addValueChangeListener(event -> {
            if (event.getValue() != event.getOldValue()) {
                channel.setGroup(event.getValue());
                channelRepository.save(channel);
                Notification.show("Moved to " + channel.getGroup());
                loadChannels();
            }
        });
        return groupComboBox;
    }

    private List<Channel> loadChannels() {
        return channelRepository.findAllByGroupNameOrderByNameAsc(tabs.getSelectedTab().getLabel());
    }
}
