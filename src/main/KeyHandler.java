package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyHandler implements KeyListener {

    public final Set<String> pressedKeys = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    GamePanel gp;

    public void KeyPressed(GamePanel gp) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO figure how to add diagonal walk with collision detection.
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            pressedKeys.add("up");
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            pressedKeys.add("left");
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            pressedKeys.add("right");
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            pressedKeys.add("down");

    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            pressedKeys.remove("up");
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            pressedKeys.remove("left");
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            pressedKeys.remove("right");
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            pressedKeys.remove("down");

    }

}