package aftergames.demo;

import aftergames.engine.Controllable;

/**
 *
 * @author KiQDominaN
 */
public class DemoControls {

    //Game actions
    public static int USE = Controllable.getKeyCode("E");
    //Movement
    public static int MOVE_FORWARD = Controllable.getKeyCode("W");
    public static int MOVE_BACKWARD = Controllable.getKeyCode("S");
    public static int SPRINT = Controllable.SHIFT;
    //Combat
    public static int SHOOT = Controllable.getKeyCode("MOUSE_1");
    public static int RELOAD = Controllable.getKeyCode("R");
    //Inventory
    public static int OPEN_INVENTORY = Controllable.getKeyCode("I");
    public static int FLASHLIGHT = Controllable.getKeyCode("F");
}