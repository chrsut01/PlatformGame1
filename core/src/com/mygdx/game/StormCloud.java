package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StormCloud extends Rectangle {
    private Vector2 position;
    private Texture stormCloudImage;
    private float radius;
    private float width = 3364 * 0.3f; // 3364 (orig pixels)
    private float height = 1564 * 0.3f; //1564 (orig pixels)

    private final Sprite stormCloudSprite;

    public StormCloud(float x, float y) {
       // this.position = new Vector2(x, y);
       // this.radius = radius;
        this.x = x;
        this.y = y;
        stormCloudImage = new Texture("storm-cloud.png");
        stormCloudSprite = new Sprite(stormCloudImage);
        stormCloudSprite.setSize(width, height);

    }

    public void render(SpriteBatch batch) {
        stormCloudSprite.setPosition(x, y);
        stormCloudSprite.draw(batch);
    }

    public void updatePosition(float deltaTime){
        x -= 40 * deltaTime;
    }

  /*  public boolean isZeppelinNear(Zeppelin zeppelin) {
        // Check if the player is within the radius of the thundercloud
        return position.dst(zeppelin.getPosition()) < radius;
    }*/

  /*  public void interactWithZeppelin(Zeppelin zeppelin) {
        // Apply thundercloud effects on the player
        zeppelin.reduceVision();
        zeppelin.triggerLightningStrike();
    }*/

}
