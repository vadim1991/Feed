package com.feed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.InputStream;

@Slf4j
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-config.xml");
        ConsoleLauncher consoleLauncher = applicationContext.getBean(ConsoleLauncher.class);
        try {
            consoleLauncher.start();
        } catch (Exception e) {
            log.error("Error due: {}", e.getMessage());
        }
    }

}
