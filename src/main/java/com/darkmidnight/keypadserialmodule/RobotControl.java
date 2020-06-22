package com.darkmidnight.keypadserialmodule;

import java.awt.event.MouseEvent;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class RobotControl {

    private static RobotControl theControl;
    private Robot theRobot;
    private HashMap<Integer, HoldDownThread> keyDownThreads;

    private RobotControl() throws AWTException {
        theRobot = new Robot();
        keyDownThreads = new HashMap<Integer, HoldDownThread>();
    }

    public void leftMouseDown() {
        theRobot.mousePress(MouseEvent.BUTTON1_MASK);
    }

    public void leftMouseUp() {
        theRobot.mouseRelease(MouseEvent.BUTTON1_MASK);
    }

    public void middleMouseDown() {
        theRobot.mousePress(MouseEvent.BUTTON2_MASK);
    }

    public void middleMouseUp() {
        theRobot.mouseRelease(MouseEvent.BUTTON2_MASK);
    }

    public void rightMouseDown() {
        theRobot.mousePress(MouseEvent.BUTTON3_MASK);
    }

    public void rightMouseUp() {
        theRobot.mouseRelease(MouseEvent.BUTTON3_MASK);
    }

    public void keyDown(String keyStr) {
        Integer k = keyTargetLookup(keyStr);
        keyUp(k);
        HoldDownThread hdt = new HoldDownThread(k);
        hdt.start();
        keyDownThreads.put(k, hdt);
    }

    public void keyUp(String keyStr) {
        Integer keyId = keyTargetLookup(keyStr);
        keyUp(keyId);
    }

    public void keyUp(int keyId) {
        if (keyDownThreads.get(keyId) != null) {
            keyDownThreads.get(keyId).keyUp();
            keyDownThreads.remove(keyId);
        }
    }

    class HoldDownThread extends Thread {

        private final int keyId;
        boolean isRunning = false;

        public HoldDownThread(int keyId) {
            this.keyId = keyId;
        }

        @Override
        public void run() {
            isRunning = true;
            while (isRunning) {
                theRobot.keyPress(keyId);
                theRobot.delay(1);
                theRobot.keyRelease(keyId);
                theRobot.delay(1);
            }
        }

        public synchronized void keyUp() {
            isRunning = false;
            theRobot.keyRelease(keyId);
        }

    }

//    /**
//     * Returns the Robot class
//     *
//     * @return the Robot class
//     */
//    public synchronized Robot getRobot() {
//        return theRobot;
//    }
    /**
     * Get the robot control.
     *
     * @return
     */
    public static synchronized RobotControl getControl() {
        if (theControl == null) {
            try {
                theControl = new RobotControl();
            } catch (AWTException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return theControl;
    }

    /**
     * Stops the class being cloned
     *
     * @return Nothing - Throws Exception
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private int keyTargetLookup(String tgt) {
        switch (tgt) {
            case "VK_0":
                return KeyEvent.VK_0;
            case "VK_1":
                return KeyEvent.VK_1;
            case "VK_2":
                return KeyEvent.VK_2;
            case "VK_3":
                return KeyEvent.VK_3;
            case "VK_4":
                return KeyEvent.VK_4;
            case "VK_5":
                return KeyEvent.VK_5;
            case "VK_6":
                return KeyEvent.VK_6;
            case "VK_7":
                return KeyEvent.VK_7;
            case "VK_8":
                return KeyEvent.VK_8;
            case "VK_9":
                return KeyEvent.VK_9;
            case "VK_A":
                return KeyEvent.VK_A;
            case "VK_ACCEPT":
                return KeyEvent.VK_ACCEPT;
            case "VK_ADD":
                return KeyEvent.VK_ADD;
            case "VK_AGAIN":
                return KeyEvent.VK_AGAIN;
            case "VK_ALL_CANDIDATES":
                return KeyEvent.VK_ALL_CANDIDATES;
            case "VK_ALPHANUMERIC":
                return KeyEvent.VK_ALPHANUMERIC;
            case "VK_ALT":
                return KeyEvent.VK_ALT;
            case "VK_ALT_GRAPH":
                return KeyEvent.VK_ALT_GRAPH;
            case "VK_AMPERSAND":
                return KeyEvent.VK_AMPERSAND;
            case "VK_ASTERISK":
                return KeyEvent.VK_ASTERISK;
            case "VK_AT":
                return KeyEvent.VK_AT;
            case "VK_B":
                return KeyEvent.VK_B;
            case "VK_BACK_QUOTE":
                return KeyEvent.VK_BACK_QUOTE;
            case "VK_BACK_SLASH":
                return KeyEvent.VK_BACK_SLASH;
            case "VK_BACK_SPACE":
                return KeyEvent.VK_BACK_SPACE;
            case "VK_BRACELEFT":
                return KeyEvent.VK_BRACELEFT;
            case "VK_BRACERIGHT":
                return KeyEvent.VK_BRACERIGHT;
            case "VK_C":
                return KeyEvent.VK_C;
            case "VK_CANCEL":
                return KeyEvent.VK_CANCEL;
            case "VK_CAPS_LOCK":
                return KeyEvent.VK_CAPS_LOCK;
            case "VK_CIRCUMFLEX":
                return KeyEvent.VK_CIRCUMFLEX;
            case "VK_CLEAR":
                return KeyEvent.VK_CLEAR;
            case "VK_CLOSE_BRACKET":
                return KeyEvent.VK_CLOSE_BRACKET;
            case "VK_CODE_INPUT":
                return KeyEvent.VK_CODE_INPUT;
            case "VK_COLON":
                return KeyEvent.VK_COLON;
            case "VK_COMMA":
                return KeyEvent.VK_COMMA;
            case "VK_COMPOSE":
                return KeyEvent.VK_COMPOSE;
            case "VK_CONTROL":
                return KeyEvent.VK_CONTROL;
            case "VK_CONVERT":
                return KeyEvent.VK_CONVERT;
            case "VK_COPY":
                return KeyEvent.VK_COPY;
            case "VK_CUT":
                return KeyEvent.VK_CUT;
            case "VK_D":
                return KeyEvent.VK_D;
            case "VK_DEAD_ABOVEDOT":
                return KeyEvent.VK_DEAD_ABOVEDOT;
            case "VK_DEAD_ABOVERING":
                return KeyEvent.VK_DEAD_ABOVERING;
            case "VK_DEAD_ACUTE":
                return KeyEvent.VK_DEAD_ACUTE;
            case "VK_DEAD_BREVE":
                return KeyEvent.VK_DEAD_BREVE;
            case "VK_DEAD_CARON":
                return KeyEvent.VK_DEAD_CARON;
            case "VK_DEAD_CEDILLA":
                return KeyEvent.VK_DEAD_CEDILLA;
            case "VK_DEAD_CIRCUMFLEX":
                return KeyEvent.VK_DEAD_CIRCUMFLEX;
            case "VK_DEAD_DIAERESIS":
                return KeyEvent.VK_DEAD_DIAERESIS;
            case "VK_DEAD_DOUBLEACUTE":
                return KeyEvent.VK_DEAD_DOUBLEACUTE;
            case "VK_DEAD_GRAVE":
                return KeyEvent.VK_DEAD_GRAVE;
            case "VK_DEAD_IOTA":
                return KeyEvent.VK_DEAD_IOTA;
            case "VK_DEAD_MACRON":
                return KeyEvent.VK_DEAD_MACRON;
            case "VK_DEAD_OGONEK":
                return KeyEvent.VK_DEAD_OGONEK;
            case "VK_DEAD_SEMIVOICED_SOUND":
                return KeyEvent.VK_DEAD_SEMIVOICED_SOUND;
            case "VK_DEAD_TILDE":
                return KeyEvent.VK_DEAD_TILDE;
            case "VK_DEAD_VOICED_SOUND":
                return KeyEvent.VK_DEAD_VOICED_SOUND;
            case "VK_DECIMAL":
                return KeyEvent.VK_DECIMAL;
            case "VK_DELETE":
                return KeyEvent.VK_DELETE;
            case "VK_DIVIDE":
                return KeyEvent.VK_DIVIDE;
            case "VK_DOLLAR":
                return KeyEvent.VK_DOLLAR;
            case "VK_DOWN":
                return KeyEvent.VK_DOWN;
            case "VK_E":
                return KeyEvent.VK_E;
            case "VK_END":
                return KeyEvent.VK_END;
            case "VK_ENTER":
                return KeyEvent.VK_ENTER;
            case "VK_EQUALS":
                return KeyEvent.VK_EQUALS;
            case "VK_ESCAPE":
                return KeyEvent.VK_ESCAPE;
            case "VK_EURO_SIGN":
                return KeyEvent.VK_EURO_SIGN;
            case "VK_EXCLAMATION_MARK":
                return KeyEvent.VK_EXCLAMATION_MARK;
            case "VK_F":
                return KeyEvent.VK_F;
            case "VK_F1":
                return KeyEvent.VK_F1;
            case "VK_F10":
                return KeyEvent.VK_F10;
            case "VK_F11":
                return KeyEvent.VK_F11;
            case "VK_F12":
                return KeyEvent.VK_F12;
            case "VK_F13":
                return KeyEvent.VK_F13;
            case "VK_F14":
                return KeyEvent.VK_F14;
            case "VK_F15":
                return KeyEvent.VK_F15;
            case "VK_F16":
                return KeyEvent.VK_F16;
            case "VK_F17":
                return KeyEvent.VK_F17;
            case "VK_F18":
                return KeyEvent.VK_F18;
            case "VK_F19":
                return KeyEvent.VK_F19;
            case "VK_F2":
                return KeyEvent.VK_F2;
            case "VK_F20":
                return KeyEvent.VK_F20;
            case "VK_F21":
                return KeyEvent.VK_F21;
            case "VK_F22":
                return KeyEvent.VK_F22;
            case "VK_F23":
                return KeyEvent.VK_F23;
            case "VK_F24":
                return KeyEvent.VK_F24;
            case "VK_F3":
                return KeyEvent.VK_F3;
            case "VK_F4":
                return KeyEvent.VK_F4;
            case "VK_F5":
                return KeyEvent.VK_F5;
            case "VK_F6":
                return KeyEvent.VK_F6;
            case "VK_F7":
                return KeyEvent.VK_F7;
            case "VK_F8":
                return KeyEvent.VK_F8;
            case "VK_F9":
                return KeyEvent.VK_F9;
            case "VK_FINAL":
                return KeyEvent.VK_FINAL;
            case "VK_FIND":
                return KeyEvent.VK_FIND;
            case "VK_FULL_WIDTH":
                return KeyEvent.VK_FULL_WIDTH;
            case "VK_G":
                return KeyEvent.VK_G;
            case "VK_GREATER":
                return KeyEvent.VK_GREATER;
            case "VK_H":
                return KeyEvent.VK_H;
            case "VK_HALF_WIDTH":
                return KeyEvent.VK_HALF_WIDTH;
            case "VK_HELP":
                return KeyEvent.VK_HELP;
            case "VK_HIRAGANA":
                return KeyEvent.VK_HIRAGANA;
            case "VK_HOME":
                return KeyEvent.VK_HOME;
            case "VK_I":
                return KeyEvent.VK_I;
            case "VK_INPUT_METHOD_ON_OFF":
                return KeyEvent.VK_INPUT_METHOD_ON_OFF;
            case "VK_INSERT":
                return KeyEvent.VK_INSERT;
            case "VK_INVERTED_EXCLAMATION_MARK":
                return KeyEvent.VK_INVERTED_EXCLAMATION_MARK;
            case "VK_J":
                return KeyEvent.VK_J;
            case "VK_JAPANESE_HIRAGANA":
                return KeyEvent.VK_JAPANESE_HIRAGANA;
            case "VK_JAPANESE_KATAKANA":
                return KeyEvent.VK_JAPANESE_KATAKANA;
            case "VK_JAPANESE_ROMAN":
                return KeyEvent.VK_JAPANESE_ROMAN;
            case "VK_K":
                return KeyEvent.VK_K;
            case "VK_KANA":
                return KeyEvent.VK_KANA;
            case "VK_KANA_LOCK":
                return KeyEvent.VK_KANA_LOCK;
            case "VK_KANJI":
                return KeyEvent.VK_KANJI;
            case "VK_KATAKANA":
                return KeyEvent.VK_KATAKANA;
            case "VK_KP_DOWN":
                return KeyEvent.VK_KP_DOWN;
            case "VK_KP_LEFT":
                return KeyEvent.VK_KP_LEFT;
            case "VK_KP_RIGHT":
                return KeyEvent.VK_KP_RIGHT;
            case "VK_KP_UP":
                return KeyEvent.VK_KP_UP;
            case "VK_L":
                return KeyEvent.VK_L;
            case "VK_LEFT":
                return KeyEvent.VK_LEFT;
            case "VK_LEFT_PARENTHESIS":
                return KeyEvent.VK_LEFT_PARENTHESIS;
            case "VK_LESS":
                return KeyEvent.VK_LESS;
            case "VK_M":
                return KeyEvent.VK_M;
            case "VK_META":
                return KeyEvent.VK_META;
            case "VK_MINUS":
                return KeyEvent.VK_MINUS;
            case "VK_MODECHANGE":
                return KeyEvent.VK_MODECHANGE;
            case "VK_MULTIPLY":
                return KeyEvent.VK_MULTIPLY;
            case "VK_N":
                return KeyEvent.VK_N;
            case "VK_NONCONVERT":
                return KeyEvent.VK_NONCONVERT;
            case "VK_NUM_LOCK":
                return KeyEvent.VK_NUM_LOCK;
            case "VK_NUMBER_SIGN":
                return KeyEvent.VK_NUMBER_SIGN;
            case "VK_NUMPAD0":
                return KeyEvent.VK_NUMPAD0;
            case "VK_NUMPAD1":
                return KeyEvent.VK_NUMPAD1;
            case "VK_NUMPAD2":
                return KeyEvent.VK_NUMPAD2;
            case "VK_NUMPAD3":
                return KeyEvent.VK_NUMPAD3;
            case "VK_NUMPAD4":
                return KeyEvent.VK_NUMPAD4;
            case "VK_NUMPAD5":
                return KeyEvent.VK_NUMPAD5;
            case "VK_NUMPAD6":
                return KeyEvent.VK_NUMPAD6;
            case "VK_NUMPAD7":
                return KeyEvent.VK_NUMPAD7;
            case "VK_NUMPAD8":
                return KeyEvent.VK_NUMPAD8;
            case "VK_NUMPAD9":
                return KeyEvent.VK_NUMPAD9;
            case "VK_O":
                return KeyEvent.VK_O;
            case "VK_OPEN_BRACKET":
                return KeyEvent.VK_OPEN_BRACKET;
            case "VK_P":
                return KeyEvent.VK_P;
            case "VK_PAGE_DOWN":
                return KeyEvent.VK_PAGE_DOWN;
            case "VK_PAGE_UP":
                return KeyEvent.VK_PAGE_UP;
            case "VK_PASTE":
                return KeyEvent.VK_PASTE;
            case "VK_PAUSE":
                return KeyEvent.VK_PAUSE;
            case "VK_PERIOD":
                return KeyEvent.VK_PERIOD;
            case "VK_PLUS":
                return KeyEvent.VK_PLUS;
            case "VK_PREVIOUS_CANDIDATE":
                return KeyEvent.VK_PREVIOUS_CANDIDATE;
            case "VK_PRINTSCREEN":
                return KeyEvent.VK_PRINTSCREEN;
            case "VK_PROPS":
                return KeyEvent.VK_PROPS;
            case "VK_Q":
                return KeyEvent.VK_Q;
            case "VK_QUOTE":
                return KeyEvent.VK_QUOTE;
            case "VK_QUOTEDBL":
                return KeyEvent.VK_QUOTEDBL;
            case "VK_R":
                return KeyEvent.VK_R;
            case "VK_RIGHT":
                return KeyEvent.VK_RIGHT;
            case "VK_RIGHT_PARENTHESIS":
                return KeyEvent.VK_RIGHT_PARENTHESIS;
            case "VK_ROMAN_CHARACTERS":
                return KeyEvent.VK_ROMAN_CHARACTERS;
            case "VK_S":
                return KeyEvent.VK_S;
            case "VK_SCROLL_LOCK":
                return KeyEvent.VK_SCROLL_LOCK;
            case "VK_SEMICOLON":
                return KeyEvent.VK_SEMICOLON;
            case "VK_SEPARATOR":
                return KeyEvent.VK_SEPARATOR;
            case "VK_SHIFT":
                return KeyEvent.VK_SHIFT;
            case "VK_SLASH":
                return KeyEvent.VK_SLASH;
            case "VK_SPACE":
                return KeyEvent.VK_SPACE;
            case "VK_STOP":
                return KeyEvent.VK_STOP;
            case "VK_SUBTRACT":
                return KeyEvent.VK_SUBTRACT;
            case "VK_T":
                return KeyEvent.VK_T;
            case "VK_TAB":
                return KeyEvent.VK_TAB;
            case "VK_U":
                return KeyEvent.VK_U;
            case "VK_UNDEFINED":
                return KeyEvent.VK_UNDEFINED;
            case "VK_UNDERSCORE":
                return KeyEvent.VK_UNDERSCORE;
            case "VK_UNDO":
                return KeyEvent.VK_UNDO;
            case "VK_UP":
                return KeyEvent.VK_UP;
            case "VK_V":
                return KeyEvent.VK_V;
            case "VK_W":
                return KeyEvent.VK_W;
            case "VK_X":
                return KeyEvent.VK_X;
            case "VK_Y":
                return KeyEvent.VK_Y;
            case "VK_Z":
                return KeyEvent.VK_Z;
            default:
                return Integer.MIN_VALUE;
        }
    }
}
