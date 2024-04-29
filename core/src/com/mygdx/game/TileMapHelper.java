package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.game.Constants.PPM;

public class TileMapHelper {

    private DilemmaScreen dilemma;
    private Stage stage;
    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("maps3/ZepMap3.tmx");

        parseMapObjects(tiledMap.getLayers().get("Foreground").getObjects());
        parseMapObjects(tiledMap.getLayers().get("Midground").getObjects());
        parseMapObjects(tiledMap.getLayers().get("Background").getObjects());
        //parseMapObjects(tiledMap.getLayers().get("Tile Layer 1").getObjects());

        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {

               if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);

                   Polygon polygon = ((PolygonMapObject) mapObject).getPolygon();
                   String polygonName = mapObject.getName();

                   if (polygonName != null && polygonName.equals("dilemma1")) {
                       // method for initializing the dilemma class and adding it to the game screen

                        // Initialize the dilemma object
                       dilemma = new DilemmaScreen();

                       // Add the dilemma object to the stage for rendering
                       dilemma.addToStage(new Actor());

                   }
            }
            if (mapObject instanceof RectangleMapObject) {
                createStaticRectangleBody((RectangleMapObject) mapObject);

                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName != null && rectangleName.equals("dilemma1")) {
                    // method for initializing the dilemma class and adding it to the game screen

                    // Initialize the dilemma object
                    dilemma = new DilemmaScreen();

                    // Add the dilemma object to the stage for rendering
                    dilemma.addToStage(new Actor());

                }
            }


         /*   if (mapObject instanceof RectangleMapObject){
                createStaticBody((RectangleMapObject) mapObject);
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName != null && rectangleName.equals("dilemma1")) {

            }*/

          /*  if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName != null && rectangleName.equals("zeppelin")) {
                    float ellipseX = rectangle.x + rectangle.width / 2;
                    float ellipseY = rectangle.y + rectangle.height / 2;

                    Body body = BodyHelperService.createBody(
                            ellipseX,
                            ellipseY,
                            ellipse.width,
                            ellipse.height,
                            false,
                            gameScreen.getWorld()
                    );
                    gameScreen.setPlayer(new Player(body));
                }*/
            }
        }

    private void createStaticRectangleBody (RectangleMapObject rectangleMapObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createRectangleShape(rectangleMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }
    private Shape createRectangleShape(RectangleMapObject rectangleMapObject) {
        Rectangle rectangle = rectangleMapObject.getRectangle();
        float x = rectangle.x / PPM;
        float y = rectangle.y / PPM;
        float width = rectangle.width / PPM;
        float height = rectangle.height / PPM;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2, new Vector2(x + width / 2, y + height / 2), 0);
        return shape;
    }





        private void createStaticBody (PolygonMapObject polygonMapObject){
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = gameScreen.getWorld().createBody(bodyDef);
            Shape shape = createPolygonShape(polygonMapObject);
            body.createFixture(shape, 1000);
            shape.dispose();
        }
        private Shape createPolygonShape (PolygonMapObject polygonMapObject){
            float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
            Vector2[] worldVertices = new Vector2[vertices.length / 2];

            for (int i = 0; i < vertices.length / 2; i++) {
                Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
                worldVertices[i] = current;
            }
            PolygonShape shape = new PolygonShape();
            shape.set(worldVertices);
            return shape;
        }


    public Array<RectangleMapObject> getDilemmaObjects() {
        Array<RectangleMapObject> dilemmaObjects = new Array<>();

        MapObjects mapObjects = tiledMap.getLayers().get("Foreground").getObjects();
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                dilemmaObjects.add((RectangleMapObject) mapObject);
            }
        }

        return dilemmaObjects;
    }


    public Object getMap() {
        return tiledMap;
    }

    public Rectangle getDilemmaRectangle() {
        Rectangle dilemmaRectangle = new Rectangle();
        MapObjects mapObjects = tiledMap.getLayers().get("Foreground").getObjects();
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                if (rectangleMapObject.getName().equals("dilemma1")) {
                    dilemmaRectangle = rectangleMapObject.getRectangle();
                }
            }
        }
        return dilemmaRectangle;
    }
}