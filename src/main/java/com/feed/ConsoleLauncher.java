package com.feed;

import com.feed.model.GlobalConfig;
import com.feed.processor.FeedStrategy;
import com.feed.schedule.MoveToProcessingAction;
import com.feed.schedule.ProcessingAction;
import com.feed.schedule.ScheduleTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.concurrent.ForkJoinPool;

import static com.feed.util.ConsoleUtil.print;

@Slf4j
public class ConsoleLauncher {

    public static void main(String[] args) {
        try {
            start();
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
        }
    }

    public static void start() {
        log.debug("Debug");
        log.info("INFO");
        log.error("Error");
        print("Hello. This console file feed");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FeedStrategy feedStrategy = new FeedStrategy();
//        String incomingDir = enterDirectory("Enter directory for incoming files:");
//        String processingDir = enterDirectory("Enter directory for temporary processing files:");
//        String errorDir = enterDirectory("Enter error directory for files:");
//        String processedDir = enterDirectory("Enter directory for processed files:");
//        long period = enterSchedulePeriod("Enter schedule period in sec:");
        GlobalConfig globalConfig = getGlobalConfig();
//                .incomingPath(incomingDir)
//                .errorPath(errorDir)
//                .processedPath(processedDir)
//                .processingPath(processingDir)
//                .schedulePeriod(period)
//                .build();
        Timer timer = new Timer();
        ScheduleTask moveToIncomingTask = new ScheduleTask(globalConfig.getIncomingPath(),
                files -> forkJoinPool.invoke(new MoveToProcessingAction(files, globalConfig.getProcessingPath())));

        ScheduleTask processingTask = new ScheduleTask(globalConfig.getProcessingPath(),
                files -> forkJoinPool.invoke(new ProcessingAction(feedStrategy, files, globalConfig)));
        timer.scheduleAtFixedRate(moveToIncomingTask, 0, globalConfig.getSchedulePeriod());
        timer.scheduleAtFixedRate(processingTask, 0, globalConfig.getSchedulePeriod());
    }

    public static GlobalConfig getGlobalConfig() {
        return GlobalConfig.builder()
                .incomingPath("d:\\IdeaProjects\\feeds\\in")
                .errorPath("d:\\IdeaProjects\\feeds\\error")
                .processedPath("d:\\IdeaProjects\\feeds\\processed")
                .processingPath("d:\\IdeaProjects\\feeds\\processing")
                .schedulePeriod(1000)
                .build();
    }

}
