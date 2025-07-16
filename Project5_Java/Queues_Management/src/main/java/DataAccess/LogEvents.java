package DataAccess;

import java.io.*;

public class LogEvents {
    private static PrintStream originalOut = System.out;
    private static PrintStream logStream;

    public static void startLogging(String fileName) {
        try {
            logStream = new PrintStream(new FileOutputStream(fileName, false));
            PrintStream customStream = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    originalOut.write(b);
                    logStream.write(b);
                }

                @Override
                public void write(byte[] b, int off, int len) throws IOException {
                    originalOut.write(b, off, len);
                    logStream.write(b, off, len);
                }

                @Override
                public void flush() throws IOException {
                    originalOut.flush();
                    logStream.flush();
                }

                @Override
                public void close() throws IOException {
                    originalOut.close();
                    logStream.close();
                }
            }, true);

            System.setOut(customStream);
        }
        catch (FileNotFoundException e) {
            System.err.println("Could not create log file: " + fileName);
        }
    }

    public static void stopLogging() {
        if (logStream != null) {
            logStream.close();
        }
        System.setOut(originalOut);
    }
}
