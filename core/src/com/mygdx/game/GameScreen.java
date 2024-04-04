package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import static com.mygdx.game.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;  // e2
    private TileMapHelper tileMapHelper; // e2

    // e4
    // game objects
    private Player player;
    private Zeppelin zeppelin;
    private Plane plane;
    private ArrayList<Plane> planes = new ArrayList<Plane>();

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        zeppelin = new Zeppelin();

        this.world = new World(new Vector2(0,0), false);         // e5 så hoppes der bedre
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);  // e2, e3: parameter
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(); // e2
        //setPlayer(player); // e4
    }

    private void update() {
        world.step(1/60f,6,2);
        zeppelin.update();
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera); // e2
        //player.update(); // e5


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


    @Override
    public void render(float delta) {
        this.update();
        //Gdx.gl.glClearColor(0.5f, 0.7f, 0.9f, 0.5f);  // makes screen light blue
        Gdx.gl.glClearColor(0, 0, 0, 0);  // makes screen transparent

        // Gdx.gl.glClearColor(0,0,0.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the view for the TiledMapRenderer
        orthogonalTiledMapRenderer.setView(camera);

        orthogonalTiledMapRenderer.render(); // e2

        batch.begin();

       // plane.render(batch);
        zeppelin.render(batch);

        batch.end();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));

    }

    // e3
    public World getWorld() {
        return world;
    }

    // e4
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setPlane(Plane plane) {
        this.plane = plane;
    }
    public void drawTexture(TextureRegion textureRegion, float x, float y) {
        batch.draw(textureRegion, x, y);
    }
}