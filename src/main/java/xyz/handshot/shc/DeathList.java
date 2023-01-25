package xyz.handshot.shc;

import org.apache.commons.compress.utils.Lists;

import java.io.*;
import java.util.*;

public class DeathList {
    private final Set<UUID> deadPlayers = new HashSet<>();
    private final File file = new File("deaths.txt");

    public DeathList() {
        try {
            SemiHardcore.LOGGER.info(file.getAbsolutePath());
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        deadPlayers.clear();
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(UUID id) {
        deadPlayers.add(id);
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean contains(UUID id) {
        return deadPlayers.contains(id);
    }

    private void init() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.lines().map(UUID::fromString).forEach(deadPlayers::add);
        reader.close();
    }

    private void write() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (UUID id : deadPlayers) {
            String s = id.toString() + System.lineSeparator();
            writer.write(s);
        }
        writer.close();
    }

    private void writeLine(BufferedWriter writer, UUID id) throws IOException {
        writer.write(id.toString());
        writer.newLine();
    }
}
