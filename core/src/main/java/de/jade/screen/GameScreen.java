package de.jade.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.jade.Assets;
import de.jade.Main;
import de.jade.helper.Constans;
import de.jade.player.Obanana;

import static de.jade.helper.Constans.PPM;

public class GameScreen extends InputAdapter implements Screen {

    private Main main;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Obanana player;
    private Viewport gamePort;

    // Map
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public GameScreen(Main main) {
        this.camera = new OrthographicCamera();
        gamePort = new FitViewport(Main.camera.viewportWidth, Main.camera.viewportHeight, camera);

        camera.position.set(gamePort.getScreenWidth() / 2f,gamePort.getScreenHeight() / 2f, 0f); // 280f + 170f
        camera.zoom = 1f; // 0.008f
        this.batch = new SpriteBatch();

        maploader = new TmxMapLoader();
        map = maploader.load("maps/Tutorial.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 140 / PPM); // 140 / PPM

        // Box2D variables
        this.world = new World(new Vector2(0,0), true);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground, copy this for other types of bodies/fixtures
       for(MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
           Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

           bdef.type = BodyDef.BodyType.StaticBody;
           bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2f) / PPM, (rectangle.getY() + rectangle.getHeight() / 2) / PPM);

           body = world.createBody(bdef);

           shape.setAsBox((rectangle.getWidth() / 2f) / PPM, (rectangle.getHeight() / 2f) / PPM);
           fdef.shape = shape;
           body.createFixture(fdef);
       }

        this.main = main;
        this.player = new Obanana();

    }

    private void update() {
        world.step(1 / 60f, 6,2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            main.setScreen(new TitleScreen(main));
        }
    }

    private void cameraUpdate() {
        // Default = 0.18f
        camera.update();
    }

    @Override
    public void show() {
        camera.setToOrtho(false);
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        box2DDebugRenderer.render(world, camera.combined);

        batch.begin();
        Texture Obanana = main.getAssetManager().get(Assets.OBANANA);
        batch.draw(Obanana, 0, 0);
        batch.end();
        cameraUpdate();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        map.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        batch.dispose();
    }

}
