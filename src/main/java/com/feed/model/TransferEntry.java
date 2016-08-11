package com.feed.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Getter
@Setter
@ToString
public class TransferEntry {

    private List<File> incomingFileList;
    private List<File> validFileList;
    private List<File> errorFileList;
    private List<File> processedFileList;
    private List<EntryModel> incomingEntries;
    private GlobalConfig config;

    public TransferEntry(GlobalConfig config) {
        incomingFileList = new ArrayList<>();
        validFileList = new ArrayList<>();
        errorFileList = new ArrayList<>();
        processedFileList = new ArrayList<>();
        incomingEntries = new ArrayList<>();
        this.config = config;
    }
}
