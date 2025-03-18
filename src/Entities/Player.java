package Entities;

import main.GamePanel;
import main.KeyboardInput;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    public GamePanel gp;
    public KeyboardInput keyboardInput;

    //Player Coordinates
    public final int player_x_coordinate;
    public final int player_y_coordinate;





    public Player(GamePanel gp, KeyboardInput keyboardInput){

        this.gp = gp;
        this.keyboardInput = keyboardInput;

        player_x_coordinate = (gp.SCREEN_WIDTH / 2) - (gp.tileSize / 2);
        player_y_coordinate = (gp.SCREEN_HEIGHT / 2) - (gp.tileSize / 2);

        this.hitBox = new Rectangle(gp.tileSize / 6, gp.tileSize / 3, gp.tileSize / 3 * 2, gp.tileSize / 3 * 2);

        setDefaultValues();
        getPlayerImage();
    }

    //Set starting point, player movement speed and which direction faces
    public void setDefaultValues() {
        this.mapX = gp.MAX_MAP_COLUMNS / 2 * gp.tileSize;
        this.mapY = gp.MAX_MAP_ROWS / 2 * gp.tileSize;
        this.speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
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

    public BufferedImage setup (String imagePath){
        Utility utility = new Utility();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imagePath + ".png"));
            image = utility.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        boolean hasMovement = false;

        if (keyboardInput.pressedKeys.contains("up"))
            direction = "up";


        if (keyboardInput.pressedKeys.contains("left"))
            direction = "left";

        if (keyboardInput.pressedKeys.contains("right"))
            direction = "right";

        if (keyboardInput.pressedKeys.contains("down"))
            direction = "down";

        if (keyboardInput.pressedKeys.size() != 4) {
            collision = false;
            gp.cv.checkCollision(this);

            if (!collision) {
                int y_add = 0;
                int x_add = 0;
                if (keyboardInput.pressedKeys.contains("up"))
                    y_add -= this.speed;
                if (keyboardInput.pressedKeys.contains("down"))
                    y_add += this.speed;

                if (keyboardInput.pressedKeys.contains("left"))
                    x_add -= this.speed;

                if (keyboardInput.pressedKeys.contains("right"))
                    x_add += this.speed;

                this.mapY += y_add;
                this.mapX += x_add;
            }

            spriteCounter++;
            if (spriteCounter > 15) {
                spriteNum = (spriteNum + 1) % 3;
                spriteCounter = 0;
            }
            if(keyboardInput.pressedKeys.isEmpty()) {
                spriteNum = 0;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        //chooses the player image to draw according to the current direction
        switch(direction){
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
        g2.drawImage(image, this.player_x_coordinate, this.player_y_coordinate, null);
    }
}