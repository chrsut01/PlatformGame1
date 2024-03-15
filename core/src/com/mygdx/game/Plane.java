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
    private final Sprite planeSprite;

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
        checkUserInput();
    }


    @Override
    public void render(SpriteBatch batch) {
        planeSprite.setPosition(x, y);
        planeSprite.draw(batch);
    }

    // e5
    private void checkUserInput() {
        velY = 0;
        velX = 0.2f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            velY = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velY = -1;

        //body.setLinearVelocity(body.getLinearVelocity().x, velY * speed);
        body.setLinearVelocity(velX * speed, velY * speed);
    }

    public void dispose() {
        planeImage.dispose();
    }
}