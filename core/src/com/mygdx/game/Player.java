package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.Constants.PPM;

public class Player extends GameEntity {
    private Texture zeppelinImage;
    private static final float width = 783/2;
    private static final float height = 109/2;
    private final Sprite zeppelinSprite;

    private World world;
    public Player(Body body) {
        super(width, height, body);
        this.speed = 10f;

        //zeppelinImage = new Texture(Gdx.files.internal("Dirigibile-Zeppelin-L59.png"));
        zeppelinImage = new Texture("zeppelin-image.png");
        zeppelinSprite = new Sprite(zeppelinImage);
        zeppelinSprite.setSize(width, height);
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
        // Convert world coordinates to screen coordinates
      //  float screenX = x * PPM;
      //  float screenY = y * PPM;

       // zeppelinSprite.setPosition(screenX, screenY);
        zeppelinSprite.setPosition(x, y);
        zeppelinSprite.draw(batch);
    }

    @Override
    public Body createBody() {
        // Implement body creation specific to Plane
        // For example:
        BodyDef bodyDef = new BodyDef();
        // Set properties of the body definition
        // Create and return the body
        return world.createBody(bodyDef);
    }

    // e5
    private void checkUserInput() {
        velY = 0;
        velX = 0.2f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            velY = 0.2f;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velY = -0.2f;

        //body.setLinearVelocity(body.getLinearVelocity().x, velY * speed);
        body.setLinearVelocity(velX * speed, velY * speed);
    }

    // Method to set the position of the player
    public void setPosition(float x, float y) {
        // Assuming you're using Box2D, you'll set the position of the body
        if (body != null) {
            body.setTransform(x / Constants.PPM, y / Constants.PPM, body.getAngle());
        }
    }

    public float getWidth() {
        return zeppelinImage.getWidth();
    }

    public float getHeight() {
        return zeppelinImage.getHeight();
    }
    public void dispose() {
        zeppelinImage.dispose();
    }
}