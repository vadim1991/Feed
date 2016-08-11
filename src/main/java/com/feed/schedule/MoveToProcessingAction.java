package com.feed.schedule;

import com.feed.util.OwnFileUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Vadym_Vlasenko on 10.08.2016.
 */
public class MoveToProcessingAction extends RecursiveAction {

    private List<File> files;
    private String directory;

    public MoveToProcessingAction(List<File> files, String directory) {
        this.files = files;
        this.directory = directory;
    }

    @Override
    protected void compute() {
        File dir = new File(directory);
        OwnFileUtils.renameAndMove(files, dir);
    }
}
