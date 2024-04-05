package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.Constants.PPM;

public class Plane extends GameEntity {

    private Texture planeImage;
    Sound planeFlyingSound;
    private static final float width = 44;
    private static final float height = 44;
    private static float x = GameConfig.SCREEN_WIDTH;
    private int yAngle;
    private final Sprite planeSprite;
    private World world;

    public Plane(float y, Body body, int yAngle) {
        super(width, height, body);
        this.y = y;
        this.velX = 200f;
        this.yAngle = yAngle;

        planeImage = new Texture("sopwith_camel_small.png");
        planeFlyingSound = Gdx.audio.newSound(Gdx.files.internal("plane_flying.mp3"));
        planeSprite = new Sprite(planeImage);
        planeSprite.setSize(width, height);
    }

    @Override
    public void update() {

        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
    }

    public int getyAngle() {

        return yAngle;
    }
    public void updatePosition(float deltaTime) {
        y -= yAngle * deltaTime;
        x -= 200 * deltaTime;
    }

    @Override
    public void render(SpriteBatch batch) {
        planeSprite.setPosition(x, y);
        planeSprite.draw(batch);
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
    public void playPlaneFlyingSound(float volume) {
        planeFlyingSound.loop(volume);
    }
    public void dispose() {
        planeImage.dispose();
        planeFlyingSound.dispose();
        planeSprite.getTexture().dispose();
    }
}