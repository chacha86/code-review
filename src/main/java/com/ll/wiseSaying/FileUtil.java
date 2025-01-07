package com.ll.wiseSaying;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> loadJSON(final String path) {
        List<String> outputs = new ArrayList<>();
        File directory = new File(path);

        if (directory.isDirectory()) {
            File[] jsonFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
            if (jsonFiles != null && jsonFiles.length > 0) {
                for (File jsonFile : jsonFiles) {
                    outputs.add(readFileContent(jsonFile));
                }
            }
        }
        return outputs;
    }

    private static String readFileContent(final File file) {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString().trim();
    }

    public static void saveFile(final String path, final String content) {
        try {
            File file = new File(path);

            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readTextFile(final String filePath) {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            // invalid file path
            return null;
        }

        return content.toString();
    }

    public static boolean delete(final String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }
}
