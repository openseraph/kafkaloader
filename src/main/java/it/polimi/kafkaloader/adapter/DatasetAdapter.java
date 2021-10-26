package it.polimi.kafkaloader.adapter;

import it.polimi.kafkaloader.adapter.util.FileComparator;
import it.polimi.kafkaloader.domain.Event;
import it.polimi.kafkaloader.port.InputPort;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class DatasetAdapter implements InputPort {

    private static final Logger logger = LoggerFactory.getLogger(DatasetAdapter.class);

    private File inputFolder;

    private PriorityQueue<File> files;

    public DatasetAdapter(String inputFolder) {
        this.inputFolder = new File(inputFolder);

        File[] files = this.inputFolder.listFiles(File::isFile);
        this.files = new PriorityQueue<>(1, new FileComparator());
        this.files.addAll(Arrays.asList(files));
    }

    @Override
    public boolean hasNext() {
        return !(this.files.isEmpty());
    }

    @Override
    public List<Event> next() throws IOException {
        return deserialize(this.files.poll());
    }

    private static List<Event> deserialize(File file) throws IOException {
        String timestamp = FilenameUtils.removeExtension(file.getName());
        String[] bodies = Files.readString(file.toPath()).split("\n");
        ArrayList<Event> events = new ArrayList<Event>();
        for (String body: bodies) {
            events.add(new Event(Instant.ofEpochMilli(Long.valueOf(timestamp)), body));
        }
        return events;
    }
}
