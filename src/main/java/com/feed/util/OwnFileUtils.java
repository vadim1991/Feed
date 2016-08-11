package com.feed.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static org.apache.commons.io.FileUtils.moveFile;

/**
 * Created by Vadym_Vlasenko on 11.08.2016.
 */
@Slf4j
public class OwnFileUtils {

    public static void renameAndMove(List<File> files, File directory) {
        for (File file : files) {
            try {
                renameAndMove(file, directory);
            } catch (IOException e) {
                log.error("File {} cannot be moved due {}", file.getName(), e.getMessage());
            }
        }
    }

    public static void moveFiles(List<File> files, String directoryPath) {
        File directory = new File(directoryPath);
        for (File file : files) {
            try {
                FileUtils.moveFileToDirectory(file, directory, true);
            } catch (IOException e) {
                log.error("File {} cannot be moved due {}", file.getName(), e.getMessage());
            }
        }
    }

    public static void renameAndMove(File file, File directory) throws IOException {
        String oldName = file.getName();
        String extension = FilenameUtils.getExtension(oldName);
        StringBuilder newNameBuilder = new StringBuilder(FilenameUtils.removeExtension(oldName));
        newNameBuilder
                .append("_")
                .append(currentTimeMillis())
                .append(".")
                .append(extension);
        moveFile(file, new File(directory, newNameBuilder.toString()));
    }

}
