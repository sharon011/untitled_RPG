package Entities;

import main.GamePanel;
import main.KeyHandler;
import main.Utility;
import objects.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {

    public GamePanel gp;
    public KeyHandler keyHandler;

    public int cameraX;
    public int cameraY;
    public ArrayList<SuperObject> inventory = new ArrayList<>();


    public Player(GamePanel gp, KeyHandler keyHandler) {

        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    //Set starting point, player movement speed and which direction faces
    public void setDefaultValues() {
        this.entity_X = gp.MAX_MAP_COLUMNS / 2 * gp.tileSize;
        this.entity_Y = gp.MAX_MAP_ROWS / 2 * gp.tileSize;
        this.cameraX = (gp.SCREEN_WIDTH / 2) - (gp.tileSize / 2);
        this.cameraY = (gp.SCREEN_HEIGHT / 2) - (gp.tileSize / 2);
        this.speed = 200;
        this.direction = "down";
        this.hitBox = new Rectangle(gp.tileSize / 6, gp.tileSize / 3, gp.tileSize / 3 * 2, gp.tileSize / 3 * 2);
    }

    public void getPlayerImage() {
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        down3 = setup("boy_down_3");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
        right3 = setup("boy_right_3");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        left3 = setup("boy_left_3");
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        up3 = setup("boy_up_3");

    }

    public BufferedImage setup(String imagePath) {
        Utility utility = new Utility();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imagePath + ".png"));
            image = utility.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update(double deltaTime) {

        if (keyHandler.pressedKeys.contains("up"))
            direction = "up";

        if (keyHandler.pressedKeys.contains("left"))
            direction = "left";

        if (keyHandler.pressedKeys.contains("right"))
            direction = "right";

        if (keyHandler.pressedKeys.contains("down"))
            direction = "down";


        collision = false;
        gp.cv.checkCollision(this, deltaTime);
        int objIndex = gp.cv.checkObject(this, true, deltaTime);
        pickUpObject(objIndex);

        if (!collision) {
            int y_add = 0;
            int x_add = 0;

            if (keyHandler.pressedKeys.contains("up"))
                y_add -= this.speed;

            if (keyHandler.pressedKeys.contains("down"))
                y_add += this.speed;

            if (keyHandler.pressedKeys.contains("left")) {
                x_add -= this.speed;
                System.out.println(("x_add left: " + x_add));
                System.out.println("add to X: " + (int)(x_add * Math.abs(deltaTime)));

            }

            if (keyHandler.pressedKeys.contains("right")) {
                x_add += this.speed;
                System.out.println("x_add right: " + x_add);
                System.out.println("add to X: " + (int)(x_add * Math.abs(deltaTime)));

            }
            this.entity_Y += (int)(y_add * Math.abs(deltaTime));
            this.entity_X += (int)(x_add * Math.abs(deltaTime));
        }

        spriteCounter++;
        if (spriteCounter > 15) {
            spriteNum = (spriteNum + 1) % 3;
            spriteCounter = 0;
        }
        if (keyHandler.pressedKeys.isEmpty()) {
            spriteNum = 0;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        //chooses the player image to draw according to the current direction
        switch (direction) {
            case "down":
                if (spriteNum == 0)
                    image = down1;
                if (spriteNum == 1)
                    image = down2;
                if (spriteNum == 2)
                    image = down3;
                break;
            case "right":
                if (spriteNum == 0)
                    image = right1;
                if (spriteNum == 1)
                    image = right2;
                if (spriteNum == 2)
                    image = right3;
                break;
            case "left":
                if (spriteNum == 0)
                    image = left1;
                if (spriteNum == 1)
                    image = left2;
                if (spriteNum == 2)
                    image = left3;
                break;
            case "up":
                if (spriteNum == 0)
                    image = up1;
                if (spriteNum == 1)
                    image = up2;
                if (spriteNum == 2)
                    image = up3;
                break;
        }
        g2.drawImage(image, this.cameraX, this.cameraY, null);
    }


    public void pickUpObject(int i) {
        if (i != 999) {
            if (inventory.size() < 12){
                inventory.add(gp.obj[i]);
                gp.obj[i] = null;
        }

        }
    }
}