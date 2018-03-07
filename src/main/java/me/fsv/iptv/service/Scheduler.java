package me.fsv.iptv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {

    @Autowired
    private Updater updater;

//    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void update() {
        updater.update();
    }

}
