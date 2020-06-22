package com.darkmidnight.keypadserialmodule;


public class Main {

    public static void main(String[] args) {
        SerialControl sc = new SerialControl("/dev/ttyACM0");
        sc.start();
        KeyProfileSingleton.setSerialControl(sc);
        Syscalls.xdoThread sysThread = new Syscalls.xdoThread();
        sysThread.start();
    }

}
