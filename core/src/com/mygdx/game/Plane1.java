package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Plane1 {

    private Texture planeImage;
    Sound planeFlyingSound;
    public static final int width = 44;
    public static final int height = 44;
    private static float x = GameConfig.SCREEN_WIDTH;
    private float y;
    private int yAngle;
    private final Sprite planeSprite;


    public Plane1(float y, int yAngle) {
        this.x = x;
        this.y = y;
        this.yAngle = yAngle;
        planeImage = new Texture("sopwith_camel_small.png");
        planeFlyingSound = Gdx.audio.newSound(Gdx.files.internal("plane_flying.mp3"));
        planeSprite = new Sprite(planeImage);
        planeSprite.setSize(width, height);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public int getyAngle() {
        return yAngle;
    }

    public void updatePosition(float deltaTime) {
        y += yAngle * deltaTime;
        x -= 20 * deltaTime;
    }

    public void render (SpriteBatch batch) {
        planeSprite.setPosition(x, y);
        planeSprite.draw(batch);
    }
}
