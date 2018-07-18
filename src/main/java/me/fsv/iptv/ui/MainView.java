package me.fsv.iptv.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route("")
@UIScope
@SpringComponent
public class MainView extends VerticalLayout {

    @Autowired
    private ChannelRepository channels;
    @Autowired
    private GroupRepository groups;
    private final Grid<Channel> grid;
    private final Tabs tabs;

    @PostConstruct
    private void init() {
        loadGroups();
        tabs.setSelectedIndex(0);
        loadChannels();
    }

    public MainView() {
        grid = new Grid<>();

        tabs = new Tabs();
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.addSelectedChangeListener(event -> loadChannels());

        grid.addColumn(Channel::getId).setHeader("Id").setFlexGrow(0).setWidth("75px");
        grid.addColumn(Channel::getName).setHeader("Name").setFlexGrow(0).setResizable(true);
        grid.addColumn(Channel::getGroup).setHeader("Group").setResizable(true);
        grid.addColumn(Channel::getUrl).setHeader("Url").setResizable(true);
        grid.addComponentColumn(channel -> new Checkbox(!channel.getIgnore()))
                .setHeader("Ignore").setFlexGrow(0).setWidth("75px");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        Button button = new Button(VaadinIcon.REFRESH.create(), buttonClickEvent -> {
            Notification.show("Done", 2000, Notification.Position.TOP_END);
        });

        add(button, tabs, grid);
        setSizeFull();
    }

    private void loadChannels() {
        grid.setItems(channels.findAllByGroupNameOrderByNameAsc(tabs.getSelectedTab().getLabel()));
    }

    private void loadGroups() {
        groups.findAllByOrderByOrdAsc().forEach(group -> tabs.add(new Tab(group.getName())));
    }
}
