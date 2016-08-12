package com.feed;

import com.feed.model.GlobalConfig;
import com.feed.processor.Strategy;
import com.feed.schedule.MoveToProcessingAction;
import com.feed.schedule.ProcessingAction;
import com.feed.schedule.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.concurrent.ForkJoinPool;

import static com.feed.util.ConsoleUtil.enterDirectory;
import static com.feed.util.ConsoleUtil.enterSchedulePeriod;

@Component
@Slf4j
public class ConsoleLauncher {

    @Autowired
    private Strategy strategy;

    public void start() {
        log.info("Application is started...");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String incomingDir = enterDirectory("Enter directory for incoming files:");
        String processingDir = enterDirectory("Enter directory for temporary processing files:");
        String errorDir = enterDirectory("Enter error directory for files:");
        String processedDir = enterDirectory("Enter directory for processed files:");
        long period = enterSchedulePeriod("Enter schedule period in sec:");
        GlobalConfig globalConfig = GlobalConfig.builder()
                .incomingPath(incomingDir)
                .errorPath(errorDir)
                .processedPath(processedDir)
                .processingPath(processingDir)
                .schedulePeriod(period * 1000)
                .build();
        log.info("Configurations: {}", globalConfig);
        Timer timer = new Timer();
        ScheduleTask moveToIncomingTask = new ScheduleTask(globalConfig.getIncomingPath(),
                files -> forkJoinPool.invoke(new MoveToProcessingAction(files, globalConfig.getProcessingPath())));

        ScheduleTask processingTask = new ScheduleTask(globalConfig.getProcessingPath(),
                files -> forkJoinPool.invoke(new ProcessingAction(strategy, files, globalConfig)));
        timer.scheduleAtFixedRate(moveToIncomingTask, 0, globalConfig.getSchedulePeriod());
        timer.scheduleAtFixedRate(processingTask, 0, globalConfig.getSchedulePeriod());
        log.info("Timers are started...");
    }

//    public static GlobalConfig getGlobalConfig() {
//        return GlobalConfig.builder()
//                .incomingPath("e:\\TemporaryProjects\\feeds\\in")
//                .errorPath("e:\\TemporaryProjects\\feeds\\error")
//                .processedPath("e:\\TemporaryProjects\\feeds\\processed")
//                .processingPath("e:\\TemporaryProjects\\feeds\\processing")
//                .schedulePeriod(1000)
//                .build();
//    }

}
