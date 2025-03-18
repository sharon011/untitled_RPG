package main;

import Entities.Player;
import Tiles.TileBoard;
import objects.SuperObject;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16; //The size of each tile in the game is 16x16 pixels.
    final int scale = 4; //By how much to scale each time (16x16 * 3 = 48x48 pixels)
    public final int tileSize = originalTileSize * scale;
    public final int MAX_SCREEN_COLUMNS = 24;
    public final int MAX_SCREEN_ROWS = 18;
    public final int SCREEN_WIDTH = tileSize * MAX_SCREEN_COLUMNS;
    public final int SCREEN_HEIGHT = tileSize * MAX_SCREEN_ROWS;

    public final int MAX_MAP_COLUMNS = 50;
    public final int MAX_MAP_ROWS = 50;


    final int nanoSecond = 1000000000;

    int fps = 60;

    //Initiate inputs and entities.
    TileBoard tb = new TileBoard(this);
    KeyboardInput keyInput = new KeyboardInput();
    Thread gameThread;
    public Player player = new Player(this, keyInput);
    public CollisionValidation cv = new CollisionValidation(this);
    public SuperObject[] obj = new SuperObject[10];
    public AssetSetter as = new AssetSetter(this);


    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
    }

    public void setupGame(){
        as.setObject();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The method "run" is responsible for running the new thread and updating and drawing everything on Game panel.
     * It is also responsible on making the game run with predefined max value of FPS (int fps).
     */
    @Override
    public void run() {
        double frameInterval = (double)nanoSecond / fps;
        double nextFrameDraw = System.nanoTime() + frameInterval;
        double lastTime = System.nanoTime();
        double newTime;
        double timer = 0;
        int countFPS = 0;

        while (gameThread != null) {

            newTime = System.nanoTime();

            update();

            repaint();

            countFPS++;

            timer += newTime - lastTime; //Start of FPS counter.
            if(timer >= nanoSecond) {
                System.out.println("FPS counted: " + countFPS);
                countFPS = 0;
                timer = 0;
            }

            lastTime = newTime;

            try {
                double remainingTime = nextFrameDraw - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);
                nextFrameDraw += frameInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tb.draw(g2);
        drawObj(g2);
        player.draw(g2);

        g2.dispose();
    }


    private void drawObj(Graphics2D g2){
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(this, g2);
            }
        }
    }



    public void zoomInOut(int code){

    }
}