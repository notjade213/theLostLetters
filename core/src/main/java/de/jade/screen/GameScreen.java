package de.jade.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.jade.Assets;
import de.jade.Main;
import de.jade.helper.Constans;
import de.jade.player.Obanana;
import de.jade.player.ObananaInput;
import de.jade.player.abilities.Attack;

public class GameScreen extends InputAdapter implements Screen {

    private final Main main;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    public final Obanana player;

    private final Viewport gamePort;
    private ObananaInput inputManager;
    private Music tutorialTheme;

    // Map
    private final TmxMapLoader maploader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(Main main) {
        inputManager = new ObananaInput();
        Gdx.input.setInputProcessor(inputManager);

        tutorialTheme = main.assetManager.get(Assets.TUTORIAL_THEME);
        tutorialTheme.setLooping(true);

        this.camera = new OrthographicCamera();
        gamePort = new FitViewport(main.camera.viewportWidth, Main.camera.viewportHeight, camera);

        camera.position.set((gamePort.getScreenWidth() / 2f) / Constans.PPM, (gamePort.getScreenHeight() / 2f) / Constans.PPM, 0f);
        camera.zoom = 0.2f / Constans.PPM;
        this.batch = new SpriteBatch();

        maploader = new TmxMapLoader();
        map = maploader.load("maps/Tutorial.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / Constans.PPM);

        // Box2D variables
        world = new World(new Vector2(0, -10f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground, copy this for other types of bodies/fixtures
        for (MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            float x = (rectangle.getX() + rectangle.getWidth() / 2f) / Constans.PPM;
            float y = (rectangle.getY() + rectangle.getHeight() / 2f) / Constans.PPM;
            float halfWidth = rectangle.getWidth() / 2f / Constans.PPM;
            float halfHeight = rectangle.getHeight() / 2f / Constans.PPM;

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(x, y);

            body = world.createBody(bdef);

            shape.setAsBox(halfWidth, halfHeight);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        this.main = main;
        this.player = new Obanana(world, main);
    }

    private void update() {
        player.getAttack().updatePunch(Gdx.graphics.getDeltaTime());

        // handle user input
        ObananaInput.inputHandler(player);

//        int speed = 10;
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += speed * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= speed * Gdx.graphics.getDeltaTime();

        camera.position.x = player.b2body.getPosition().x;
        camera.position.y = player.b2body.getPosition().y; // 550f

        world.step(1 / 60f, 6, 2);

        if (camera.position.x >= 90.37492 ) {
            camera.position.set(90.37492f, camera.position.y, 0f);
        }

        cameraUpdate();

        renderer.setView(camera);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            main.setScreen(new TitleScreen(main));
        }
    }

    private void cameraUpdate() {
        camera.update();
    }

    @Override
    public void show() {
        tutorialTheme.play();
        camera.setToOrtho(false);
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        renderer.render();

        batch.begin();
        player.update(delta);
        player.draw(batch);
        batch.end();

        cameraUpdate();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (tutorialTheme.isPlaying()) {
            tutorialTheme.pause();
        }
    }

    @Override
    public void resume() {
        if (!tutorialTheme.isPlaying()) {
            tutorialTheme.play();
        }
    }

    @Override
    public void hide() {
        tutorialTheme.stop();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        map.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        batch.dispose();
        tutorialTheme.dispose();
    }

    public Obanana getPlayer() {
        return this.player;
    }
}
