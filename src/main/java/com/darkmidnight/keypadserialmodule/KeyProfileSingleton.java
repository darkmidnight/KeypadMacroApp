package com.darkmidnight.keypadserialmodule;

import java.io.IOException;

public class KeyProfileSingleton {

    private static SerialControl getSerialControl() {
        return getSingleton().sc;
    }

    private SerialControl sc;

    public KeyProfileSingleton() {
        this.profile = Profile.DEMO;

    }

    public static void setSerialControl(SerialControl sc) {
        getSingleton().sc = sc;
    }

    public static synchronized KeyProfileSingleton getSingleton() {
        if (singleton == null) {
            singleton = new KeyProfileSingleton();
        }
        return singleton;
    }
    private static KeyProfileSingleton singleton;

    /**
     *
     * @param i An int between 0-7 to indicate which button was pressed.
     */
    static void handleKeyPress(int i) {
        getSingleton().profile.handlePress(i);
    }

    /**
     *
     * @param i An int between 0-7 to indicate which button was pressed.
     */
    static void handleKeyRelease(int i) {
        getSingleton().profile.handleRelease(i);
    }
    private Profile profile;

    private void setActive(Profile p) {
        System.out.println("Profile "+p.name());
        for (int i = 0; i < 8; i++) {
            p.handleRelease(i);
        }
        this.profile = p;
        profile.setup();
    }

    private Profile getActive() {
        return profile;
    }

    enum Profile implements ProfileInterface {
        DEMO,
        OPENSCAD {
            @Override
            public void setup() {
          
                lightButton((byte)42);
            }

            @Override
            public void handlePress(int i) {
                switch (i) {
                    case 1:
                        // Rotate
                        RobotControl.getControl().leftMouseDown();
                        break;
                    case 3:
                        // Pan
                        RobotControl.getControl().rightMouseDown();
                        break;
//                    case 5:
//                        // Zoom
//                        RobotControl.getControl().middleMouseDown();
//                        break;
                }
            }

            @Override
            public void handleRelease(int i) {
                switch (i) {
                    case 1:
                        // Rotate
                        RobotControl.getControl().leftMouseUp();  
                        break;
                    case 3:
                        // Pan
                        RobotControl.getControl().rightMouseUp();
                        break;
//                    case 5:
//                        // Zoom
//                        RobotControl.getControl().middleMouseUp();
//                        break;
                } 
            }  

            @Override
            public void lightButton(byte b) {
                try {
                    KeyProfileSingleton.getSerialControl().send(b);
                } catch (IOException ex) {
                    System.err.println("Unable to send byte " + b);
                }
            }
        },
        BLENDER {
             @Override
            public void handlePress(int i) {
                switch (i) {
                    case 1:
                        // Rotate
                        RobotControl.getControl().middleMouseDown();
                        break;
                    case 3:
                        // Pan
                        RobotControl.getControl().keyDown("VK_SHIFT");
                        RobotControl.getControl().middleMouseDown();
                        break;
//                    case 5:
//                        // Zoom
//                        
//                        break;
                }
            }

            @Override
            public void handleRelease(int i) {
                switch (i) {
                    case 1:
                        // Rotate
                        RobotControl.getControl().middleMouseUp();
                        break;
                    case 3:
                        // Pan
                        RobotControl.getControl().keyUp("VK_SHIFT");
                        RobotControl.getControl().middleMouseUp();
                        break;
//                    case 5:
//                        // Zoom
//                        
//                        break;
                } 
            }  
        },
        UNREAL;

        @Override
        public void setup() {
            System.out.println("Setup complete");
        }

        @Override
        public void handlePress(int i) {
            System.out.println("Button " + i + " Pressed");
        }

        @Override
        public void handleRelease(int i) {
            System.out.println("Button " + i + " Released");
        }

        @Override
        public void lightButton(byte b) {
            try {
                KeyProfileSingleton.getSerialControl().send(b);
            } catch (IOException ex) {
                System.err.println("Unable to send byte " + b);
            }
        }
    }

    interface ProfileInterface {

        void setup();

        void lightButton(byte b);

        void handlePress(int i);

        void handleRelease(int i);
    }

    static void setProfile(String winName) {
                for (Profile p : Profile.values()) {
            if (winName.toUpperCase().contains(p.name())) {
                if (!getSingleton().getActive().name().equals(p.name())) {
                    getSingleton().setActive(p);

                }
            }
        }
        
    }

}
