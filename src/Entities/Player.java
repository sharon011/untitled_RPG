package Entities;

import main.GamePanel;
import main.KeyboardInput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gamePanel;
    KeyboardInput keyboardInput;

    //Coordinates of the player
    public final int playerX;
    public final int playerY;



    public Player(GamePanel gamePanel, KeyboardInput keyboardInput){

        this.gamePanel = gamePanel;
        this.keyboardInput = keyboardInput;

        playerX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        playerY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

        this.hitBox = new Rectangle(gamePanel.tileSize / 6, gamePanel.tileSize / 3, gamePanel.tileSize / 3 * 2, gamePanel.tileSize / 3 * 2);

        setDefaultValues();
        getPlayerImage();
    }

    //Set starting point, player movement speed and which direction faces
    public void setDefaultValues() {
        this.mapX = gamePanel.maxWorldCol / 2 * gamePanel.tileSize;
        this.mapY = gamePanel.maxWorldRow / 2 * gamePanel.tileSize;
        this.speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_3.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_3.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_3.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_3.png")));

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        if ((keyboardInput.downPressed) || keyboardInput.leftPressed || keyboardInput.rightPressed
                || keyboardInput.upPressed) {

            if (keyboardInput.downPressed) {
                direction = "down";
            }
            if (keyboardInput.rightPressed) {
                direction = "right";
            }
            if (keyboardInput.leftPressed) {
                direction = "left";
            }
            if (keyboardInput.upPressed) {
                direction = "up";
            }

            collision = false;
            gamePanel.cv.checkCollision(this);

            if(!collision)
                switch (direction) {
                    case "up":
                        this.mapY -= this.speed;
                        break;
                    case "down":
                        this.mapY += this.speed;
                        break;
                    case "left":
                        this.mapX -= this.speed;
                        break;
                    case "right":
                        this.mapX += this.speed;
                        break;
                }

            spriteCounter++;
            if(spriteCounter > 15) {
                if (spriteNum == 2) {
                    spriteNum = 3;
                }else if(spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }else if(spriteCounter > 7.5){
                if(spriteNum == 1)
                    spriteNum = 2;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        //chooses the player image to draw according to the current direction
        switch(direction){
            case "down":
                if (spriteNum == 1)
                    image = down1;
                if (spriteNum == 2)
                    image = down2;
                if (spriteNum == 3)
                    image = down3;
                break;
            case "right":
                if (spriteNum == 1)
                    image = right1;
                if (spriteNum == 2)
                    image = right2;
                if (spriteNum == 3)
                    image = right3;
                break;
            case "left":
                if (spriteNum == 1)
                    image = left1;
                if (spriteNum == 2)
                    image = left2;
                if (spriteNum == 3)
                    image = left3;
                break;
            case "up":
                if (spriteNum == 1)
                    image = up1;
                if (spriteNum == 2)
                    image = up2;
                if (spriteNum == 3)
                    image = up3;
                break;
        }
        g2.drawImage(image, this.playerX, this.playerY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}