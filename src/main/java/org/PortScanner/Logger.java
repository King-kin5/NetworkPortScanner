package org.PortScanner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    /**
     * @param message The message to print into the log file
     * @param color   The color of the message that is printed in the console
     */
    public static void log(final String message, final Color color) {
        System.out.println( color.getColor() + message + Color.RESET.getColor());
    }

    /**
     * @param message The message to print into the log file.
     */
    public static void log(final String message) {
        log(message, Color.WHITE);
    }


}