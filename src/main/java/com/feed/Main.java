package com.feed;

import com.feed.model.GlobalConfig;
import com.feed.processor.FeedStrategy;
import com.feed.processor.Strategy;
import com.feed.schedule.ScheduleTask;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class Main {

    public static void main(String[] args) {
//        GlobalConfig globalConfig = GlobalConfig.getInstance();
//        globalConfig.setErrorPath("e:\\Development tools\\Feeds\\error");
//        globalConfig.setIncomingPath("e:\\Development tools\\Feeds\\in");
//        globalConfig.setProcessingPath("e:\\Development tools\\Feeds\\processing");
//        globalConfig.setProcessedPath("e:\\Development tools\\Feeds\\processed");
//        globalConfig.setSchedulePeriod(1000);
//        Strategy feedStrategy = new FeedStrategy();
//        Timer timer = new Timer();
//        ScheduleTask scheduleTask = new ScheduleTask(feedStrategy);
//        timer.scheduleAtFixedRate(scheduleTask, 0, globalConfig.getSchedulePeriod());
    }

}
