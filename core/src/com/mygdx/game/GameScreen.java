package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    //  private TriggerObject triggerObject;
    private DilemmaScreen dilemma;
    private boolean dilemmaTriggered;
    private boolean gamePaused;
    private static final int MIN_Y_ANGLE = 0;
    private static final int MAX_Y_ANGLE = 60;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Stage stage;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    long lastPlaneTime;
    float planeSpawnTimer;

    public static final float MIN_PLANE_SPAWN_TIME = 0.2f;
    public static final float MAX_PLANE_SPAWN_TIME = 10f;

    long lastStormCloudTime = TimeUtils.millis() + (long) (MIN_StormCloud_SPAWN_TIME * 1000);
    float stormCloudSpawnTimer;
    public static final float MIN_StormCloud_SPAWN_TIME = 5f;
    public static final float MAX_StormCloud_SPAWN_TIME = 15f;

    private Texture mapImage;

    private float mapWidth;
    private float mapHeight;

    private Zeppelin zeppelin;
    private Plane plane;
    //private StormCloud stormCloud;

    private ArrayList<Plane> planes;

    private ArrayList<StormCloud> stormClouds;


    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        zeppelin = new Zeppelin();
        planes = new ArrayList<>();
        stormClouds = new ArrayList<>();
        random = new Random();

        // triggerObject = new TriggerObject();
        dilemma = new DilemmaScreen();
        dilemmaTriggered = false;

        this.world = new World(new Vector2(0,0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        this.mapImage = new Texture("map-to-afrika.png");

        // Calculate the maximum size based on the desired maximum width or height
        mapHeight = Gdx.graphics.getHeight() * 0.3f;

        float aspectRatio = (float) mapImage.getHeight() / (float) mapImage.getWidth();

        // Calculate the scaling factor based on the maximum width or height
        mapWidth = mapHeight / aspectRatio;
    }

    private void update(float delta) {
        if(!gamePaused) {
            // Update the world (physics simulation
        world.step(1/60f,6,2);
        zeppelin.update();
        dilemma.update();
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Check if it's time to spawn a plane
        if (TimeUtils.timeSinceMillis(lastPlaneTime) > planeSpawnTimer) {
            spawnPlane();
            // Generate a new random spawn delay
            planeSpawnTimer = MathUtils.random(MIN_PLANE_SPAWN_TIME * 1000, MAX_PLANE_SPAWN_TIME * 1000);
            // Update the last plane spawn time
            lastPlaneTime = TimeUtils.millis();
        }

        for (Plane plane : planes) {
            plane.updatePosition(delta);
        }

        // Check if it's time to spawn a cloud
        if (TimeUtils.timeSinceMillis(lastStormCloudTime) > stormCloudSpawnTimer) {
            spawnStormCloud();
            // Generate a new random spawn delay
            stormCloudSpawnTimer = MathUtils.random(MIN_StormCloud_SPAWN_TIME * 1000, MAX_StormCloud_SPAWN_TIME * 1000);
            // Update the last storm cloud spawn time
            lastStormCloudTime = TimeUtils.millis();
        }

        for (StormCloud stormCloud : stormClouds) {
            stormCloud.updatePosition(delta);
        }

        // Check if zeppelin reaches x value 900
        if (zeppelin.getX() >= 1750) {
            if (!dilemmaTriggered) {

                // Initialize the dilemma object
                dilemma = new DilemmaScreen();

                // Add the dilemma object to the stage for rendering
                dilemma.addToStage(new Actor());

                ((Game) Gdx.app.getApplicationListener()).setScreen(dilemma);

                // Show the dilemma screen
                dilemma.showDilemmaScreen();

                dilemmaTriggered = true; // Mark dilemma as triggered to prevent repeated triggering
            }
        }
        }
    }
    @Override
    public void render(float delta) {
        this.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);  // makes screen transparent
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.setView(camera);

        orthogonalTiledMapRenderer.render();

        batch.begin();

        for (Plane plane : planes) {
            plane.render(batch);
        }

        zeppelin.render(batch);

        for (StormCloud stormCloud : stormClouds) {
            stormCloud.render(batch);
        }

        // Draw the map at the bottom left corner of the screen
        float mapX = camera.position.x - camera.viewportWidth / 2 + 20;
        float mapY = camera.position.y - camera.viewportHeight / 2 + 20;
        batch.draw(mapImage, mapX, mapY, mapWidth, mapHeight);

        batch.end();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));


        // Check if the dilemma screen is triggered and an option is clicked
        if (dilemmaTriggered && dilemma.isButtonClicked()) {

            if (dilemma.getSelectedOption() == 1) {
                // Option 1 chosen
            } else if (dilemma.getSelectedOption() == 2) {
                // Option 2 chosen
            }

            // Reset the dilemma trigger and button clicked status
            dilemmaTriggered = false;
            dilemma.setButtonClicked(false);

         /*   if(!gamePaused){
            ((Game) Gdx.app.getApplicationListener()).setScreen(this);
            }
            dilemma.update();*/

        }

        // Check for collision between player and polygon objects
      /*  for (RectangleMapObject mapObject : tileMapHelper.getDilemmaObjects()) {
            Rectangle rectangle = mapObject.getRectangle();
            String polygonName = mapObject.getName();

            if (zeppelin.getBoundingRectangle().overlaps(tileMapHelper.getDilemmaRectangle())) {
                if (!dilemmaTriggered) {
                    // Initialize the dilemma object
                    dilemma = new DilemmaScreen();

                    // Add the dilemma object to the stage for rendering
                    dilemma.addToStage(new Actor());

                    // Show the dilemma screen
                    dilemma.showDilemmaScreen();

                    dilemmaTriggered = true; // Mark dilemma as triggered to prevent repeated triggering
                }
            }
        }*/
    }
    private void cameraUpdate() {
        int mapWidth = GameConfig.TILEMAP_WIDTH;
        int mapHeight = GameConfig.TILEMAP_HEIGHT;

        // Calculate the maximum camera position based on the tilemap dimensions
        //  float maxCameraX = mapWidth - camera.viewportWidth / 2;
        //  float maxCameraY = mapHeight - camera.viewportHeight / 2;

        float zeppelinX = zeppelin.getX();
        float zeppelinY = zeppelin.getY();

        // Calculate the target camera position to keep the Zeppelin centered
        float targetCameraX = MathUtils.clamp(zeppelinX + zeppelin.getWidth() / 2, camera.viewportWidth / 2, mapWidth - camera.viewportWidth / 2);
        float targetCameraY = MathUtils.clamp(zeppelinY + zeppelin.getHeight() / 2, camera.viewportHeight / 2, mapHeight - camera.viewportHeight / 2);

        camera.position.set(targetCameraX, targetCameraY, 0);

        // Ensure the camera stays within the tilemap boundaries
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, mapWidth - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, mapHeight - camera.viewportHeight / 2);

        camera.update();
    }

    private void spawnPlane () {
        float x = camera.position.x + camera.viewportWidth / 2;
        float minY = camera.position.y - camera.viewportHeight / 2;
        float maxY = camera.position.y + camera.viewportHeight / 2;
        float y = MathUtils.random(minY + (camera.viewportHeight / 4), maxY - (camera.viewportHeight / 4)); // Adjusted y-coordinate range
        float middleY = camera.position.y; // Calculate the middle of the screen

        // Determine the yAngle based on the relative position of the plane to the middle of the screen
        int yAngle;
        if (y < middleY) {
            yAngle = random(MIN_Y_ANGLE, MAX_Y_ANGLE);  // Plane starts above the middle of the screen
        } else {
            yAngle = -random(MIN_Y_ANGLE, MAX_Y_ANGLE); // Plane starts below or at the middle of the screen
        }
        plane = new Plane(x, y, yAngle);
        plane.planeFlyingSound.play();
        planes.add(plane);
    }

    private void spawnStormCloud () {
        float x = camera.position.x + camera.viewportWidth / 2;
        float minY = 400;
        float maxY = GameConfig.TILEMAP_HEIGHT - 800;
        float y = MathUtils.random(minY, maxY);

        StormCloud stormCloud = new StormCloud(x, y);
        stormClouds.add(stormCloud);
    }




    private void showDilemmaScreen () {
        // Switch to dilemma screen or show the popup
        // Example:
        // ((Game) Gdx.app.getApplicationListener()).setScreen(dilemmaScreen);
        // Or handle dilemma within the game screen
        dilemma.showDilemmaScreen();
    }
    private void pauseGame() {
        gamePaused = true;
    }

    private void resumeGame() {
        gamePaused = false;
    }

    public World getWorld () {
        return world;
    }

}