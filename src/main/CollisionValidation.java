package main;

import Entities.*;

public class CollisionValidation {
    GamePanel gp;

    public CollisionValidation(GamePanel gp) {
        this.gp = gp;
    }


    public void checkCollision(Entity entity, double deltaTime) {
        double deltaSpeed = deltaTime * entity.speed;
        //Calculates the boundaries of the entity hit box.
        double rightX = entity.entity_X + entity.hitBox.x + entity.hitBox.width;
        double leftX = entity.entity_X + entity.hitBox.x;
        double bottomY = entity.entity_Y + entity.hitBox.y + entity.hitBox.height;
        double topY = entity.entity_Y + entity.hitBox.y;

        //Calculates the row and col of each boundary.
        int rightCol = (int) (rightX / gp.tileSize);
        int leftCol = (int) (leftX / gp.tileSize);
        int bottomRow = (int) (bottomY / gp.tileSize);
        int topRow = (int) (topY / gp.tileSize);

        //for each direction the entity moves to, 2 tiles needs to be checked.
        int tile1, tile2;

        //for each direction, we predict the next location and check if the collision on the predicted tile is true.

        switch (entity.direction) {
            case "up":
                topRow = (int) ((topY - deltaSpeed) / gp.tileSize);
                tile1 = gp.tb.map[leftCol][topRow];
                tile2 = gp.tb.map[rightCol][topRow];
                if ((gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision))
                    entity.collision = true;
                break;
            case "down":
                bottomRow = (int) ((bottomY + deltaSpeed) / gp.tileSize);
                tile1 = gp.tb.map[leftCol][bottomRow];
                tile2 = gp.tb.map[rightCol][bottomRow];
                if (gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision)
                    entity.collision = true;
                break;
            case "left":
                leftCol = (int) ((leftX - deltaSpeed) / gp.tileSize);
                tile1 = gp.tb.map[leftCol][topRow];
                tile2 = gp.tb.map[leftCol][bottomRow];
                if (gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision)
                    entity.collision = true;
                break;
            case "right":
                rightCol = (int) ((rightX + deltaSpeed) / gp.tileSize);
                tile1 = gp.tb.map[rightCol][topRow];
                tile2 = gp.tb.map[rightCol][bottomRow];
                if (gp.tb.resourceTiles[tile1].collision || gp.tb.resourceTiles[tile2].collision)
                    entity.collision = true;
                break;
        }
    }


    public int checkObject(Entity entity, boolean player, double deltaTime){
        int index = 999;

        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] != null) {
                entity.hitBox.x = entity.entity_X + entity.hitBox.x;
                entity.hitBox.y = entity.entity_Y + entity.hitBox.y;
                gp.obj[i].hitBox.x = gp.obj[i].objectX + gp.obj[i].hitBox.y;
                gp.obj[i].hitBox.y = gp.obj[i].objectY + gp.obj[i].hitBox.y;

                switch(entity.direction){
                    case "up":
                        entity.hitBox.y -= (entity.speed * deltaTime);
                        if(entity.hitBox.intersects(gp.obj[i].hitBox)) {
                            if (gp.obj[i].collision)
                                entity.collision = true;

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.hitBox.y -= (entity.speed * deltaTime);
                        if(entity.hitBox.intersects(gp.obj[i].hitBox)){
                            if (gp.obj[i].collision)
                                entity.collision = true;

                            if (player)
                                index = i;
                        }
                        break;
                    case "right":
                        entity.hitBox.y -= (entity.speed * deltaTime);
                        if(entity.hitBox.intersects(gp.obj[i].hitBox)){
                            if (gp.obj[i].collision)
                                entity.collision = true;

                            if (player)
                                index = i;
                        }
                        break;
                    case "down":
                        entity.hitBox.y -= (entity.speed * deltaTime);
                        if(entity.hitBox.intersects(gp.obj[i].hitBox)){
                            if (gp.obj[i].collision)
                                entity.collision = true;

                            if (player)
                                index = i;
                        }
                        break;
                }
                entity.hitBox.x = 0;
                entity.hitBox.y = 0;
                gp.obj[i].hitBox.x = 0;
                gp.obj[i].hitBox.y = 0;

            }
        }



        return index;
    }


}
