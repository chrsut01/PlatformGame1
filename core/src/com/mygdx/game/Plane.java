package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.Constants.PPM;

public class Plane extends GameEntity {
    private Texture planeImage;
    private static final float width = 94;
    private static final float height = 72;
    private Sprite planeSprite;

    public Plane(Body body) {
        super(width, height, body);
        this.speed = 10f;

        planeImage = new Texture("sopwith_camel_small.png");
        planeSprite = new Sprite(planeImage);
        planeSprite.setSize(width, height);
    }

    @Override
    public void update() {

        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        // e5
        //checkUserInput();
    }


    @Override
    public void render(SpriteBatch batch) {
        planeSprite.setPosition(x, y);
        planeSprite.draw(batch);
    }


    public void dispose() {
        planeImage.dispose();
    }
}