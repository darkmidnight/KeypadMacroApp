package com.darkmidnight.keypadserialmodule;

import com.fazecast.jSerialComm.SerialPort;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerialControl extends Thread {

    private final SerialPort sp;
    private DataInputStream dis;
    private DataOutputStream dos;

    public SerialControl(String portDesc) {
        this.sp = SerialPort.getCommPort(portDesc);
        sp.openPort();
        sp.setBaudRate(9600);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(sp.getInputStream());
            int aByte = 0;

            int prevByte = -1;
            while ((aByte = dis.read()) != -1) {
                aByte = Byte.toUnsignedInt((byte) aByte);
                if (aByte != prevByte) {
//                    send((byte) aByte);
//                    System.out.println("Send"+(byte) aByte);
                    for (int i = 0; i < 8; i++) {
                        if (prevByte != -1) {
                            if (((aByte & (1 << (i))) == (1 << (i)))
                                    && // aByte is set, wasn't before (keypress)
                                    ((prevByte & (1 << (i))) != (1 << (i)))) {
                                KeyProfileSingleton.handleKeyPress(i);
                            } else if (((aByte & (1 << (i))) != (1 << (i)))
                                    && // aByte is NOT set, WAS before (keyrelease)
                                    ((prevByte & (1 << (i))) == (1 << (i)))) {
                                KeyProfileSingleton.handleKeyRelease(i);
                            }
                        }
                    }
                }
                prevByte = aByte;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void send(byte aByte) throws IOException {
        dos = new DataOutputStream(sp.getOutputStream());
        dos.write(aByte);
        dos.flush();
    }

    /**
     * Static convenience method for seeing what ports are available.
     *
     * @return
     */
    public static List<String> getPortList() {
        List<String> aList = new ArrayList<>();
        for (SerialPort sp : SerialPort.getCommPorts()) {
            aList.add(sp.getSystemPortName());
        }
        return aList;
    }

}
