package common.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FileUtil {

        public static void createDir(String dirPath) {
            try {
                Files.createDirectories(Paths.get(dirPath));
            } catch (IOException e) {
                System.out.println("Failed to create directory: " + e.getMessage());
            }
        }

        public static void createFile(String file) {
            write(file, "");
        }

        public static void write(String file, int content) {
            write(file, String.valueOf(content));
        }

        public static void write(String file, String content) {
            Path filePath = Paths.get(file);

            if (filePath.getParent() != null) {
                createDir(filePath.getParent().toString());
            }

            try {
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                System.out.println("Failed to write file: " + e.getMessage());
            }
        }

        public static Path getPath(String file) {
            return Paths.get(file);
        }

        public static String readAsString(String file) {

            Path filePath = Paths.get(file);

            try {
                return Files.readString(filePath);
            } catch (IOException e) {
                System.out.println("Failed to read file: " + e.getMessage());
            }

            return "";
        }

        public static boolean delete(String file) {
            Path filePath = Paths.get(file);

            if (!Files.exists(filePath)) return false;

            try {
                Files.delete(filePath);
                return true;
            } catch (IOException e) {
                System.out.println("Failed to delete file: " + e.getMessage());
                return false;
            }
        }

        public static void deleteForce(String path) {

            Path folderPath = Paths.get(path);

            if (!Files.exists(folderPath)) return;

            try {
                Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        System.out.println("Deleted file: " + file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        System.out.println("Deleted directory: " + dir);
                        return FileVisitResult.CONTINUE;
                    }
                });

                System.out.println("Success deleted files: " + folderPath);
            } catch (IOException e) {
                System.err.println("Failed to delete files: " + e.getMessage());
            }
        }

        public static List<Path> getPaths(String dirPath) {
            Path filePath = Paths.get(dirPath);
            try {
                return Files.walk(filePath).filter(Files::isRegularFile).toList();
            } catch (IOException e) {
                System.out.println("Failed to read files: " + e.getMessage());
            }
            return List.of();
        }

        public static boolean exists(String file) {
            Path filePath = Paths.get(file);
            return Files.exists(filePath);
        }
    }