package it.polimi.kafkaloader.adapter.util;

import java.io.File;
import java.util.Comparator;

public class DirectoryComparator implements Comparator<File> {
    @Override
    public int compare(File file1, File file2) {
        return Long.compare(
                Long.valueOf(file1.getName()),
                Long.valueOf(file2.getName())
        );
    }
}
