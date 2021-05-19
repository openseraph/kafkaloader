package it.polimi.kafkaloader.adapter;

import it.polimi.kafkaloader.adapter.util.DirectoryComparator;
import it.polimi.kafkaloader.deserializer.EventFile;
import it.polimi.kafkaloader.domain.Event;
import it.polimi.kafkaloader.port.InputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.PriorityQueue;

public class DatasetAdapter implements InputPort {

    private static final Logger logger = LoggerFactory.getLogger(DatasetAdapter.class);

    private File inputFolder;

    private PriorityQueue<File> directories;
    private PriorityQueue<File> files;

    public DatasetAdapter(String inputFolder) {
        this.inputFolder = new File(inputFolder);

        File[] directories = this.inputFolder.listFiles(File::isDirectory);
        this.directories = new PriorityQueue<>(1, new DirectoryComparator());
        this.directories.addAll(Arrays.asList(directories));

        this.files = new PriorityQueue<>();
    }

    @Override
    public boolean hasNext() {
        return !(this.files.isEmpty() && this.directories.isEmpty());
    }

    @Override
    public Event next() {
        if (this.files.isEmpty()) {
            File directory = this.directories.poll();
            File[] files = directory.listFiles(File::isFile);
            this.files.addAll(Arrays.asList(files));
        }
        EventFile eventFile = new EventFile(this.files.poll());
        return eventFile.getEvent();
    }
}
