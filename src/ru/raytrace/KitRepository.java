package ru.raytrace;

import java.io.*;
import java.util.*;

public class KitRepository {
    private Map<String, Integer> kitsMap;
    private final File dataFile;

    public KitRepository(String PATH_TO_FILE) {
        dataFile = new File(PATH_TO_FILE);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (Exception ignored) {}
        }
        kitsMap = deserialize();
    }

    public Optional<Integer> getPriceByName(String kitName) {
        return Optional.ofNullable(kitsMap.get(kitName));
    }

    public void addNewEntry(String kitName, int price) {
        kitsMap.put(kitName, price);
    }

    public String getAvailableKits() {
        List<String> kitList = new ArrayList<>();
        kitsMap.forEach((k, v) -> kitList.add(k + ":" + v));

        return kitList.
                toString().
                replace("[", "").
                replace("]", "");
    }

    public boolean removeByName(String name) {
        try {
            kitsMap.remove(name);

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void serialize() {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(dataFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(kitsMap);
        } catch (IOException ignored) {}
    }

    private HashMap<String, Integer> deserialize() {
        try (
                FileInputStream fileInputStream = new FileInputStream(dataFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (HashMap<String, Integer>) objectInputStream.readObject();
        } catch (Exception ignored) {}

        return new HashMap<>();
    }

}
