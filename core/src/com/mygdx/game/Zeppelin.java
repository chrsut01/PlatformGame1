package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


    public class Zeppelin {
        private static final float width = 783/2;
        private static final float height = 109/2;
        private static final float MAX_SPEED = 200; // Maximum speed of the zeppelin
        private static final float ACCELERATION = 5; // Acceleration factor
        private static final float DECELERATION = 5; // Deceleration factor
        private static final float MIN_SPEED = 1; // Minimum speed before stopping


        private float ySpeed = 0;
        private float xSpeed = 30;


        private Sprite zeppelinSprite;
        private Sound engineSound;

        public Zeppelin() {
            init();
        }

        private void init() {
            // Load textures and sounds
            zeppelinSprite = new Sprite(new Texture(Gdx.files.internal("zeppelin-image.png")));
            engineSound = Gdx.audio.newSound(Gdx.files.internal("ZeppelinEngine.mp3"));

            // Resize the sprite to match the player's dimensions
            zeppelinSprite.setSize(width, height);

            // Set default dimensions and position
            zeppelinSprite.setPosition(GameConfig.SCREEN_WIDTH / 2f - width / 2,
                    GameConfig.SCREEN_HEIGHT / 2f - height / 2);

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
            // You can add more parameters for pitch, pan, etc., based on your requirements.
        }

        private void handleInput() {
            // Handle user input for zeppelin movement
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
              //  ySpeed += ACCELERATION * Gdx.graphics.getDeltaTime();
                ySpeed += 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
               // ySpeed -= ACCELERATION * Gdx.graphics.getDeltaTime();
                ySpeed -= 100 * Gdx.graphics.getDeltaTime();
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
          //  y += ySpeed * Gdx.graphics.getDeltaTime();

            // Ensure speed doesn't drop below a certain threshold to avoid jittering
            if (Math.abs(ySpeed) < MIN_SPEED) ySpeed = 0;

            // Ensure the zeppelin stays within the screen bounds
            zeppelinSprite.setY(MathUtils.clamp(zeppelinSprite.getY(), 0, GameConfig.SCREEN_HEIGHT - zeppelinSprite.getHeight()));

          /*  // Ensure the zeppelin stays within the screen bounds vertically
            float minY = 0;
            float maxY = GameConfig.SCREEN_HEIGHT - zeppelinSprite.getHeight();
            zeppelinSprite.setY(MathUtils.clamp(zeppelinSprite.getY(), minY, maxY));*/



        }

        public void dispose() {
            zeppelinSprite.getTexture().dispose();
            engineSound.dispose();
        }

        public float getX() {
            return zeppelinSprite.getX();
        }

        public float getY() {
            return zeppelinSprite.getY();
        }
    }

