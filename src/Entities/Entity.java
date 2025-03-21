package Entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int entity_X;
    public int entity_Y;
    public int speed;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1,right2, right3;

    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle hitBox;
    public boolean collision = false;
}