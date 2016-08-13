package com.feed.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static com.feed.util.ConsoleUtil.printError;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * Created by Vadym_Vlasenko on 10.08.2016.
 */
@Slf4j
public class ScheduleTask extends TimerTask {

    private final String directory;
    private final Consumer<List<File>> task;
    private Map<Integer, File> oldFiles = new ConcurrentHashMap<>();

    public ScheduleTask(String directory, Consumer<List<File>> task) {
        this.directory = directory;
        this.task = task;
    }

    @Override
    public void run() {
        try {
            executeTask();
        } catch (Exception e) {
            log.error("Error is occurred due {}", e.getMessage());
            printError("Error is occurred. See error logs");
            cancel();
            log.error("Schedule task was canceled");
        }
    }

    private void executeTask() {
        File dir = new File(directory);
        checkDirectory(dir);
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        Map<Integer, File> allFilesMap = files.stream()
                .collect(toMap(file -> file.getName().hashCode(), file -> file));
        Set<File> newFiles = findDifference(allFilesMap.keySet(), oldFiles.keySet())
                .stream().map(allFilesMap::get).collect(toSet());
        if (!newFiles.isEmpty()) {
            long startTime = System.currentTimeMillis();
            log.info("Start executing task. Existing new files in {} directory", directory);
            task.accept(files);
            log.info("End executing task in {} directory. Processing time {} ms",
                    directory,
                    System.currentTimeMillis() - startTime);
        }
        oldFiles = allFilesMap;
    }

    private void checkDirectory(final File dir) {
        if (oldFiles.size() > 500000) {
            oldFiles.clear();
            log.info("Clear old files. Size was {}", oldFiles.size());
        }
        if (!dir.isDirectory() || !dir.exists()) {
            log.error("Dir {} is not exist", dir.getAbsolutePath());
            printError("Error is occurred. Directory is not exist. See error logs");
            cancel();
            log.error("Schedule task was canceled {}");
        }
    }

    private static <T> Set<T> findDifference(final Set<T> first, final Set<T> second) {
        Set<T> set = new TreeSet<>(first);
        set.removeAll(second);
        return set;
    }
}
