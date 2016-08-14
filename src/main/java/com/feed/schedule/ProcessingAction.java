package com.feed.schedule;

import com.feed.model.GlobalConfig;
import com.feed.model.TransferEntry;
import com.feed.processor.Strategy;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import static com.feed.util.ConsoleUtil.printError;
import static com.feed.util.OwnFileUtils.moveFiles;

/**
 * Created by Vadym_Vlasenko on 11.08.2016.
 */
@Slf4j
public class ProcessingAction extends RecursiveAction {

    private Strategy strategy;
    private List<File> files;
    private GlobalConfig globalConfig;

    public ProcessingAction(Strategy strategy, List<File> files, GlobalConfig globalConfig) {
        this.strategy = strategy;
        this.files = files;
        this.globalConfig = globalConfig;
    }

    @Override
    protected void compute() {
        TransferEntry transferEntry = new TransferEntry(globalConfig);
        transferEntry.setIncomingFileList(files);
        transferEntry.setIncomingFileList(files);
        try {
            strategy.runProcess(transferEntry);
        } catch (Exception e) {
            log.error("Error is occurred {} due {}", e.getMessage(), e.getCause());
            printError("Error is occurred, shutdown program and see error logs.");
            moveFiles(transferEntry.getErrorFileList(), transferEntry.getConfig().getErrorPath());
        }
    }
}
