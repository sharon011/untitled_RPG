package Tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileBoard {

    GamePanel gp;
    public Tile[] resourceTiles;
    public int[][] map;


    public TileBoard(GamePanel gp){
        this.gp = gp;
        resourceTiles = new Tile[3];
        map = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage(){
        try{
            resourceTiles[0] = new Tile();
            resourceTiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            resourceTiles[1] = new Tile();
            resourceTiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stone.png")));
            resourceTiles[1].collision = true;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/maps/test1.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                //Using while loop for the ability to declare a variable inside the loop.
                while(col < gp.maxWorldCol && line != null){
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

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tile = map[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int playerX = worldX - gp.player.mapX + gp.player.playerX;
            int playerY = worldY - gp.player.mapY + gp.player.playerY;

            if(worldX + gp.tileSize > gp.player.mapX - gp.player.playerX &&
                    worldX - gp.tileSize < gp.player.mapX + gp.player.playerX &&
                    worldY + gp.tileSize > gp.player.mapY - gp.player.playerY &&
                    worldY - gp.tileSize < gp.player.mapY + gp.player.playerY){

                g.drawImage(resourceTiles[tile].image, playerX, playerY, gp.tileSize, gp.tileSize, null);

            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}