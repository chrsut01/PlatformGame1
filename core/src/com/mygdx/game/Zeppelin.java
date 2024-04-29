package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Zeppelin extends Rectangle {
        private static final float width = 783/2;
        private static final float height = 109/2;
        private static final float MAX_SPEED = 80; // Maximum speed of the zeppelin
        private static final float ACCELERATION = 55f; // Acceleration factor
        private static final float DECELERATION = 20f; // Deceleration factor
        private static final float MIN_SPEED = 1; // Minimum speed before stopping


        private float ySpeed = 0;
        private float xSpeed = 80;


        private Sprite zeppelinSprite;
        private Sound engineSound;

        public Zeppelin() {
            init();
        }

        private void init() {
            // Load textures and sounds
            zeppelinSprite = new Sprite(new Texture(Gdx.files.internal("zeppelin-image.png")));
            engineSound = Gdx.audio.newSound(Gdx.files.internal("ZeppelinEngine.mp3"));

            // Resize the sprite to match the zeppelin's dimensions
            zeppelinSprite.setSize(width, height);

            // Set default dimensions and position zeppelin in center of screen
            zeppelinSprite.setOrigin(width / 2, height / 2);
            zeppelinSprite.setPosition(GameConfig.SCREEN_WIDTH / 2f - width / 2,
                    GameConfig.SCREEN_HEIGHT / 2f - height / 2 + 500);

            playEngineSound(2.2f); // Set the initial volume (you can change this value)
        }

        public void update() {
            handleInput();
            zeppelinSprite.translateX(xSpeed * Gdx.graphics.getDeltaTime());
        }

        public void render(SpriteBatch batch) {
            zeppelinSprite.draw(batch);
        }

        public void playEngineSound(float volume) {
            engineSound.loop(volume);
        }

        private void handleInput() {
            // Handle user input for zeppelin movement
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                ySpeed += ACCELERATION * Gdx.graphics.getDeltaTime();
               // ySpeed += 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                ySpeed -= ACCELERATION * Gdx.graphics.getDeltaTime();
               // ySpeed -= 100 * Gdx.graphics.getDeltaTime();
            if (!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (ySpeed > 0)
                    ySpeed -= DECELERATION * Gdx.graphics.getDeltaTime();
                else if (ySpeed < 0)
                    ySpeed += DECELERATION * Gdx.graphics.getDeltaTime();
            }

            // Limit the speed
            ySpeed = MathUtils.clamp(ySpeed, -MAX_SPEED, MAX_SPEED);

            // Apply position update based on speed
            zeppelinSprite.translateY(ySpeed * Gdx.graphics.getDeltaTime());

            // Ensure speed doesn't drop below a certain threshold to avoid jittering
            if (Math.abs(ySpeed) < MIN_SPEED) ySpeed = 0;

            // Ensure the zeppelin stays within the screen bounds vertically
            float minY = 0;
            float maxY = GameConfig.TILEMAP_HEIGHT - (zeppelinSprite.getHeight() +7);

            // Check if the zeppelin is at the top of the screen and reduce ySpeed to change direction more naturally
            if (zeppelinSprite.getY() == maxY && ySpeed > 0) {
                ySpeed -= ACCELERATION * Gdx.graphics.getDeltaTime();
            }
            // Keep the zeppelin within the screen bounds
            zeppelinSprite.setY(MathUtils.clamp(zeppelinSprite.getY(), minY, maxY));
        }

        public void dispose() {
            zeppelinSprite.getTexture().dispose();
            engineSound.dispose();
        }

        public float getWidth() {
            return zeppelinSprite.getWidth();
        }
        public float getHeight() {
            return  zeppelinSprite.getHeight();
        }
        public float getX() {
            return zeppelinSprite.getX();
        }

        public float getY() {
            return zeppelinSprite.getY();
        }

    public Rectangle getBoundingRectangle() {
            return new Rectangle(zeppelinSprite.getX(), zeppelinSprite.getY(), zeppelinSprite.getWidth(), zeppelinSprite.getHeight());

    }

      /*  public getPosition() {
            return zeppelinSprite.getX(), zeppelinSprite.getY();
        }*/

    }

