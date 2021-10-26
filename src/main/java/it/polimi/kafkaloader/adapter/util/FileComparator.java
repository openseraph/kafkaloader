package it.polimi.kafkaloader.adapter.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File> {
    @Override
    public int compare(File file1, File file2) {
        return Long.compare(
                Long.valueOf(FilenameUtils.removeExtension(file1.getName())),
                Long.valueOf(FilenameUtils.removeExtension(file2.getName()))
        );
    }
}
