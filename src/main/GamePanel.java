package main;

import Entities.Player;
import Tiles.TileBoard;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16; //The size of each tile in the game is 16x16 pixels.
    final int scale = 3; //The amount in which we will increase each tile (16x16 * 3 = 48x48 pixels)
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 24;
    public final int maxScreenRow = 18;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    final int nanoSecond = 1000000000;

    int fps = 60;

    //Initiate inputs and entities.
    TileBoard tb = new TileBoard(this);
    KeyboardInput keyI = new KeyboardInput();
    Thread gameThread;
    public Player player = new Player(this, keyI);
    public CollisionValidation cv = new CollisionValidation(this);

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyI);
        this.setFocusable(true);
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
        double frameInterval = nanoSecond / fps;
        double nextFrameDraw = System.nanoTime() + frameInterval;
        double lastTime = System.nanoTime();
        double newTime = System.nanoTime();
        double timer = 0;
        int fpsCount = 0;

        while (gameThread != null) {

            newTime = System.nanoTime();

            update();

            repaint();

            fpsCount++;

            timer += newTime - lastTime; //Start of FPS counter.
            if(timer >= 1000000000) {
                System.out.println("FPS counted: " + fpsCount);
                fpsCount = 0;
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
        player.draw(g2);
        g2.dispose();
    }
    /**
     * NEEDS REFINING, returns 60/61 fps instead of 60.
     *
     private static int getFps(){
     double lastTime = System.nanoTime();
     double timer = 0;
     int fpsCount = 0;
     timer += System.nanoTime() - lastTime; //Start of FPS counter.
     if(timer >= 1000000000){
     System.out.println("FPS counted: " + fpsCount);
     fpsCount = 0;
     timer = 0;
     }
     fpsCount++;
     lastTime = System.nanoTime(); //end of FPS counter;

     }
     **/
}