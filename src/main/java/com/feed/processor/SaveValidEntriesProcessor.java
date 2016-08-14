package com.feed.processor;

import com.feed.entity.Entry;
import com.feed.model.EntryModel;
import com.feed.model.TransferEntry;
import com.feed.service.EntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.feed.util.ConsoleUtil.printError;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Component(value = "saveEntryProcessor")
@Slf4j
public class SaveValidEntriesProcessor implements Processor {

    @Autowired
    private EntryService entryService;

    @Override
    public void process(TransferEntry transferEntry) {
        List<EntryModel> incomingEntries = transferEntry.getIncomingEntries();
        if (!incomingEntries.isEmpty()) {
            List<Entry> entries = mapEntry(incomingEntries);
            saveEntries(entries, transferEntry);
        }
    }

    private void saveEntries(List<Entry> entries, TransferEntry transferEntry) {
        try {
            entryService.batchSave(entries);
            transferEntry.getProcessedFileList().addAll(transferEntry.getValidFileList());
            log.info("{} entries were saved to DB", entries.size());
        } catch (Exception e) {
            log.error("Error is occurred in {} due: {}", getName(), e.getMessage());
            printError("Error is occurred during saving to DB. See error logs");
            transferEntry.getErrorFileList().addAll(transferEntry.getValidFileList());
        }
    }

    private List<Entry> mapEntry(List<EntryModel> entryModels) {
        return entryModels.stream()
                .map(EntryModel::toEntity)
                .collect(Collectors.toList());
    }

    public EntryService getEntryService() {
        return entryService;
    }

    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }
}
