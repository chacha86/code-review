import java.io.*;

public class TestUtil {

    public static ByteArrayInputStream getInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    public static ByteArrayOutputStream setOutToByteArray() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        return output;
    }

    public static void clearSetOutToByteArray(final ByteArrayOutputStream output) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}