package Tiles;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileBoard {

    GamePanel gp;
    public Tile[] resourceTiles;
    public int[][] map;


    public TileBoard(GamePanel gp){
        this.gp = gp;
        resourceTiles = new Tile[30];
        map = new int[gp.MAX_MAP_COLUMNS][gp.MAX_MAP_ROWS];

        getTileImage();
        loadMap();
    }

    public void getTileImage(){
            setup(0,"grass", false);
            setup(1,"stone", true);
            setup(2, "treeGrass", true);
            setup(3, "singlePath", false);
            setup(4,"doublePath1", false);
            setup(5,"doublePath2", false);
            setup(6,"fullPath", false);
            setup(7,"pathTopLeft", false);
            setup(8,"pathBottomRight",false);
            setup(9,"pathTopRight", false);
            setup(10,"pathBottomLeft", false);
            setup(11,"doublePathBottom", false);
            setup(12, "lakeBottomLeft", true);
            setup(13,"lakeBottomMid", true);
            setup(14,"lakeBottomRight", true);
            setup(15, "lakeMidLeft",true);
            setup(16,"lakeMidMid", true);
            setup(17, "lakeMidRight", true);
            setup(18, "lakeTopLeft",true);
            setup(19,"lakeTopMid", true);
            setup(20,"lakeTopRight", true);
            setup(21,"singlePathHorizontal", false);

    }

    public void setup(int index, String imagePath, boolean collision){
        Utility utility = new Utility();

        try{
            resourceTiles[index] = new Tile();
            resourceTiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            resourceTiles[index].image = utility.scaleImage(resourceTiles[index].image, gp.tileSize, gp.tileSize);
            resourceTiles[index].collision = collision;

        }catch (IOException e){
            e.printStackTrace();
        }
    }




    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/maps/test1.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.MAX_MAP_COLUMNS && row < gp.MAX_MAP_ROWS){

                String line = br.readLine();

                //Using while loop for the ability to declare a variable inside the loop.
                while(col < gp.MAX_MAP_COLUMNS && line != null){
                    String[] lineAsChar = line.split(" ");

                    int num = Integer.parseInt(lineAsChar[col]);
                    map[col][row] = num;
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.MAX_MAP_COLUMNS && worldRow < gp.MAX_MAP_ROWS){

            int tile = map[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int playerX = (int) (worldX - gp.player.entity_X + gp.player.cameraX);
            int playerY = (int) (worldY - gp.player.entity_Y + gp.player.cameraY);

            if(worldX + gp.tileSize > gp.player.entity_X - gp.player.cameraX &&
                    worldX - gp.tileSize < gp.player.entity_X + gp.player.cameraX &&
                    worldY + gp.tileSize > gp.player.entity_Y - gp.player.cameraY &&
                    worldY - gp.tileSize < gp.player.entity_Y + gp.player.cameraY){

                g.drawImage(resourceTiles[tile].image, playerX, playerY,null);

            }

            worldCol++;

            if(worldCol == gp.MAX_MAP_COLUMNS) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}