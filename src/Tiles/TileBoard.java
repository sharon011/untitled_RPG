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
        resourceTiles = new Tile[3];
        map = new int[gp.MAX_MAP_COLUMNS][gp.MAX_MAP_ROWS];

        getTileImage();
        loadMap();
    }

    public void getTileImage(){
            setup(0,"grass", false);
            setup(1,"stone", true);

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
            int playerX = worldX - gp.player.mapX + gp.player.player_x_coordinate;
            int playerY = worldY - gp.player.mapY + gp.player.player_y_coordinate;

            if(worldX + gp.tileSize > gp.player.mapX - gp.player.player_x_coordinate &&
                    worldX - gp.tileSize < gp.player.mapX + gp.player.player_x_coordinate &&
                    worldY + gp.tileSize > gp.player.mapY - gp.player.player_y_coordinate &&
                    worldY - gp.tileSize < gp.player.mapY + gp.player.player_y_coordinate){

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