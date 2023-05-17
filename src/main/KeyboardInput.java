package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {

    }
    public boolean upPressed, leftPressed, downPressed, rightPressed;
    @Override
    public void keyPressed(KeyEvent e) {
        //TODO figure how to add diagonal walk with collision detection.
        int code = e.getKeyCode();

        switch(code){
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;

        }

    }
}