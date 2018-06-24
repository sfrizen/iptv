package me.fsv.iptv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {
    @Autowired
    private Updater updater;
    @Scheduled(cron = "0 * * * * *")
    public void update() {
        updater.update();
    }
}
