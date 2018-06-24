package me.fsv.iptv;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class App {
    public static final Marker EMAIL = MarkerFactory.getMarker("email");

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
