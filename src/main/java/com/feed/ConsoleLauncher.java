package com.feed;

import com.feed.model.GlobalConfig;
import com.feed.processor.Strategy;
import com.feed.schedule.MoveToProcessingAction;
import com.feed.schedule.ProcessingAction;
import com.feed.schedule.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ForkJoinPool;

import static com.feed.util.ConsoleUtil.*;

@Component
@Slf4j
public class ConsoleLauncher {

    @Autowired
    private Strategy strategy;

    public void start() {
        log.info("Application is started...");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        GlobalConfig globalConfig = fillGlobalConfig();
        log.info("Configurations: {}", globalConfig);
        Timer timer = new Timer();
        ScheduleTask moveToIncomingTask = new ScheduleTask(globalConfig.getIncomingPath(),
                files -> forkJoinPool.invoke(new MoveToProcessingAction(files, globalConfig.getProcessingPath())));

        ScheduleTask processingTask = new ScheduleTask(globalConfig.getProcessingPath(),
                files -> forkJoinPool.invoke(new ProcessingAction(strategy, files, globalConfig)));

        timer.scheduleAtFixedRate(moveToIncomingTask, 0, globalConfig.getSchedulePeriod());
        timer.scheduleAtFixedRate(processingTask, 0, globalConfig.getSchedulePeriod());
        log.info("Timers are started...");
        print("Application is started...");
    }

    private GlobalConfig fillGlobalConfig() {
        Set<String> folders = new HashSet<>();
        String incomingDir = enterDirectory("Enter directory for incoming files:", folders);
        String processingDir = enterDirectory("Enter directory for temporary processing files:", folders);
        String errorDir = enterDirectory("Enter error directory for files:", folders);
        String processedDir = enterDirectory("Enter directory for processed files:", folders);
        long period = enterSchedulePeriod("Enter schedule period in sec:");
        return GlobalConfig.builder()
                .incomingPath(incomingDir)
                .errorPath(errorDir)
                .processedPath(processedDir)
                .processingPath(processingDir)
                .schedulePeriod(period * 1000)
                .build();
    }

}
