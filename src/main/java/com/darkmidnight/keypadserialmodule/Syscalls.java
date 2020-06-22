package com.darkmidnight.keypadserialmodule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Syscalls {

    public static String findWindow() throws IOException {
        String line = "";
        Process p = Runtime.getRuntime().exec(new String[]{"xdotool", "getwindowfocus", "getwindowname"});

        try {
            p.waitFor();
        } catch (InterruptedException e) {
        }
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String theID = "";
        while ((line = input.readLine()) != null) {
            return line;

        }
        input.close();
        if (p.exitValue() == 0) {
            return theID;
        }
        return "";
    }

    static class xdoThread extends Thread {

        public xdoThread() {
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Looking for window");
                    String winName = findWindow();
                    KeyProfileSingleton.setProfile(winName);
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(Syscalls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
