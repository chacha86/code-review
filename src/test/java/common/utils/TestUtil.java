package common.utils;

import java.io.*;
import java.util.Scanner;

public class TestUtil {
    public static Scanner genScanner(String input) {
        return new Scanner(input);
    }

    public static ByteArrayOutputStream setOutToByteArray() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        return output;
    }

    public static void clearSetOutToByteArray(ByteArrayOutputStream output) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}