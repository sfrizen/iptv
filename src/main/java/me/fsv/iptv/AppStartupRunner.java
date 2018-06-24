package me.fsv.iptv;

import me.fsv.iptv.service.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {
    @Autowired
    private Updater updater;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        updater.update();
    }
}
