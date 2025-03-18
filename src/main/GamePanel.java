package main;

import Entities.Player;
import HUD.UI;
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

    private final double NANO_SECOND = 1_000_000_000;
    private double targetFPS = 90;
    private double OPTIMAL_TIME = NANO_SECOND / targetFPS;

    int fps = 0;
    int frameCount = 0;
    long lastTime = System.nanoTime();
    long lastFPSCheck = System.currentTimeMillis();


    //Initialize inputs, entities and objects.
    TileBoard tb = new TileBoard(this);
    KeyHandler keyInput = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyInput);
    public CollisionValidation cv = new CollisionValidation(this);
    public SuperObject[] obj = new SuperObject[10];
    public AssetSetter as = new AssetSetter(this);
    public UI ui = new UI(this);


    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
    }

    public void setupGame() {
        as.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The method "run" is responsible for running the new thread and updating and drawing everything on Game panel.
     * It is also responsible on making the game run with predefined max value of FPS (int fps).
     */
    @Override
    public void run() {


        while (gameThread != null) {
            long now = System.nanoTime();
            long updateLength = now - lastTime;
            lastTime = now;

            frameCount++;
            if (System.currentTimeMillis() - lastFPSCheck >= 1000) {
                fps = frameCount;
                frameCount = 0;
                lastFPSCheck = System.currentTimeMillis();
                //System.out.println("FPS: " + fps);

            }

            update(updateLength / NANO_SECOND);
            repaint();

            long sleepTime = (long) ((OPTIMAL_TIME - (System.nanoTime() - now)) / 1_000_000);
            if(sleepTime > 0){
                try{
                    Thread.sleep(sleepTime);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(double deltaTime) {
        player.update(deltaTime);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tb.draw(g2);
        drawObj(g2);
        player.draw(g2);
        ui.draw(g2);

        g2.dispose();
    }


    private void drawObj(Graphics2D g2) {
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(this, g2);
            }
        }
    }


    public void zoomInOut(int code) {

    }
}