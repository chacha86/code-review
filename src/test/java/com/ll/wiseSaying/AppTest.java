package com.ll.wiseSaying;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class AppTest {

    private static final String DBPATH = "db/wiseSaying";

    public static void clear() {
        File folder = new File(DBPATH);

        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        try {
            Files.write(new File(DBPATH+"/lastId.txt").toPath(), String.valueOf(0).getBytes());
        } catch (IOException ignored) {
        }
    }
}