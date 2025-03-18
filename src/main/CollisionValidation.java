package main;

import Entities.*;

public class CollisionValidation {
    GamePanel gp;

    public CollisionValidation(GamePanel gp) {
        this.gp = gp;
    }


    public void checkCollision(Entity entity) {
        //Calculates the boundaries of the entity hit box.
        int rightX = entity.mapX + entity.hitBox.x + entity.hitBox.width;
        int leftX = entity.mapX + entity.hitBox.x;
        int bottomY = entity.mapY + entity.hitBox.y + entity.hitBox.height;
        int topY = entity.mapY + entity.hitBox.y;

        //Calculates the row and col of each boundary.
        int rightCol = rightX / gp.tileSize;
        int leftCol = leftX / gp.tileSize;
        int bottomRow = bottomY / gp.tileSize;
        int topRow = topY / gp.tileSize;

        //for each direction the entity moves to, 2 tiles needs to be checked.
        int tile1, tile2;

        //for each direction, we predict the next location and check if the collision on the predicted tile is true.

        switch (entity.direction) {
            case "up":
                topRow = (topY - entity.speed) / gp.tileSize;
                tile1 = gp.tb.map[leftCol][topRow];
                tile2 = gp.tb.map[rightCol][topRow];
                if ((gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision))
                    entity.collision = true;
                break;
            case "down":
                bottomRow = (bottomY + entity.speed) / gp.tileSize;
                tile1 = gp.tb.map[leftCol][bottomRow];
                tile2 = gp.tb.map[rightCol][bottomRow];
                if (gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision)
                    entity.collision = true;
                break;
            case "left":
                leftCol = (leftX - entity.speed) / gp.tileSize;
                tile1 = gp.tb.map[leftCol][topRow];
                tile2 = gp.tb.map[leftCol][bottomRow];
                if (gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision)
                    entity.collision = true;
                break;
            case "right":
                rightCol = (rightX + entity.speed) / gp.tileSize;
                tile1 = gp.tb.map[rightCol][topRow];
                tile2 = gp.tb.map[rightCol][bottomRow];
                if (gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision)
                    entity.collision = true;
                break;
        }
    }


}
