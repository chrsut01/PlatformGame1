package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.TextureRegion;
import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;  // e2
    private TileMapHelper tileMapHelper; // e2

    long lastPlaneTime;
    float planeSpawnTimer;

    public static final float MIN_PLANE_SPAWN_TIME = 0.05f;
    public static final float MAX_PLANE_SPAWN_TIME = 0.1f;

    private Player player;
    private Zeppelin zeppelin;
    private Plane plane;
    private Plane1 plane1;
    private ArrayList<Plane1> planes1;
    Body planeBody;





    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        zeppelin = new Zeppelin();
        planes1 = new ArrayList<>();
        //spawnPlane1();


        this.world = new World(new Vector2(0,0), false);         // e5 så hoppes der bedre
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);  // e2, e3: parameter
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(); // e2

    }

    private void update() {
        world.step(1/60f,6,2);
        zeppelin.update();
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera); // e2

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    // update camera position for zeppelin
    private void cameraUpdate(){
        // Get the tilemap dimensions from the GameConfiguration class
        int mapWidth = GameConfig.TILEMAP_WIDTH;
        int mapHeight = GameConfig.TILEMAP_HEIGHT;

        // Calculate the maximum camera position based on the tilemap dimensions
        float maxCameraX = mapWidth - camera.viewportWidth / 2;
        float maxCameraY = mapHeight - camera.viewportHeight / 2;

        // Get the Zeppelin's position
        float zeppelinX = zeppelin.getX();
        float zeppelinY = zeppelin.getY();

        // Calculate the target camera position to keep the Zeppelin centered
        float targetCameraX = MathUtils.clamp(zeppelinX + zeppelin.getWidth() / 2, camera.viewportWidth / 2, mapWidth - camera.viewportWidth / 2);
        float targetCameraY = MathUtils.clamp(zeppelinY + zeppelin.getHeight() / 2, camera.viewportHeight / 2,  mapHeight - camera.viewportHeight / 2);

        // Set the camera at the target position
        camera.position.set(targetCameraX, targetCameraY, 0);

        // Ensure the camera stays within the tilemap boundaries
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, mapWidth - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, mapHeight - camera.viewportHeight / 2);

        // Update the camera position
        camera.update();
    }

    private void spawnPlane1() {
        float x = camera.viewportWidth;
        float y = 300;//random((camera.viewportHeight/4), camera.viewportHeight - (camera.viewportHeight/4)); // ensures planes enter screen at least 1/4 screen-height from top and bottom
        float middleY = camera.viewportHeight / 2f; // Calculate the middle of the screen

        // Determine the yAngle based on the relative position of the plane to the middle of the screen
      /*  int yAngle;
        if (y < middleY) {
            // Plane starts above the middle of the screen
            yAngle = random.nextInt(60); // Generate a positive yAngle
        } else {
            // Plane starts below or at the middle of the screen
            yAngle = -random.nextInt(60); // Generate a negative yAngle
        }*/
        int yAngle = 60;
        plane1 = new Plane1(y, yAngle);
        plane1.planeFlyingSound.play();
        planes1.add(plane1);
        // planeSpawnTimer = random.nextFloat() * (MAX_PLANE_SPAWN_TIME - MIN_PLANE_SPAWN_TIME) + MIN_PLANE_SPAWN_TIME;
    }

    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(0, 0, 0, 0);  // makes screen transparent
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the view for the TiledMapRenderer
        orthogonalTiledMapRenderer.setView(camera);

        orthogonalTiledMapRenderer.render(); // e2

        batch.begin();

        // Plane1 spawn code
        planeSpawnTimer -= delta;
        if (planeSpawnTimer <= 0) {
            planeSpawnTimer = random.nextFloat() * (MAX_PLANE_SPAWN_TIME - MIN_PLANE_SPAWN_TIME) + MIN_PLANE_SPAWN_TIME;
        spawnPlane1();
        }
        for (Plane1 plane1 : planes1) {
            plane1.updatePosition(delta);
        }

        plane1.render(batch);
        zeppelin.render(batch);

        batch.end();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));

    }


    // e3
    public World getWorld() {
        return world;
    }



   /* public void drawTexture(TextureRegion textureRegion, float x, float y) {
        batch.draw(textureRegion, x, y);
    }*/
}