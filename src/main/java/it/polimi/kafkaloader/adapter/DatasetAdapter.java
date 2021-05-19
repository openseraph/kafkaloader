package it.polimi.kafkaloader.adapter;

import it.polimi.kafkaloader.adapter.util.DirectoryComparator;
import it.polimi.kafkaloader.domain.Event;
import it.polimi.kafkaloader.port.InputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
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
    public Event next() throws IOException {
        if (this.files.isEmpty()) {
            File directory = this.directories.poll();
            File[] files = directory.listFiles(File::isFile);
            this.files.addAll(Arrays.asList(files));
        }

        return deserialize(this.files.poll());
    }

    private static Event deserialize(File file) throws IOException {
        String timestamp = file.getParentFile().getName();
        String body = Files.readString(file.toPath());

        return new Event(Instant.ofEpochSecond(Long.valueOf(timestamp)), body);
    }
}
