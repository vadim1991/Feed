package com.feed.processor;

import com.feed.model.GlobalConfig;
import com.feed.model.TransferEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.feed.util.OwnFileUtils.moveFiles;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Component(value = "moveFilesProcessor")
@Slf4j
public class MoveFilesProcessor implements Processor {

    @Override
    public void process(TransferEntry transferEntry) {
        GlobalConfig config = transferEntry.getConfig();
        moveFiles(transferEntry.getErrorFileList(), config.getErrorPath());
        moveFiles(transferEntry.getProcessedFileList(), config.getProcessedPath());
    }

}
