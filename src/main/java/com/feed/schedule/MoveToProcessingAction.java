package com.feed.schedule;

import java.io.File;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import static com.feed.util.OwnFileUtils.renameAndMove;

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
        renameAndMove(files, dir);
    }
}
