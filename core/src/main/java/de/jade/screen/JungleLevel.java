package de.jade.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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
import de.jade.helper.ContactListener.MasterContactListener;
import de.jade.helper.ContactListener.ObananaContactListener;
import de.jade.helper.ContactListener.PitContactListener;
import de.jade.helper.Door;
import de.jade.helper.Trigger;
import de.jade.player.Obanana;
import de.jade.player.ObananaInput;

import java.util.ArrayList;
import java.util.List;

public class JungleLevel extends InputAdapter implements Screen {

    private final Main main;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    private Door door;
    private boolean doorRange = false;

    public final Obanana player;
    private List<Trigger> triggers = new ArrayList<>();

    private final List<Body> movingPlatforms = new ArrayList<>();

    private final Viewport gamePort;
    private ObananaInput inputManager;
    private Music jungleTheme;

    // Map
    private final TmxMapLoader maploader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    // Moving platform variables
    private boolean goingUp = true;
    private boolean goingSideWays = true;
    private float speedY = 1f;
    private float speedX = 1f;

    private float minY1;
    private float maxY1;


    public JungleLevel(Main main) {
        inputManager = new ObananaInput();
        Gdx.input.setInputProcessor(inputManager);

        jungleTheme = main.assetManager.get(Assets.JUNGLE_THEME);
        jungleTheme.setLooping(true);

        triggers.add(new Trigger(new Vector2(92.7f,14.5f), 3f, () -> {
            doorRange = true;
        }));

        door = new Door(main);

        this.camera = new OrthographicCamera();
        camera.position.set(0, 500f, 0);
        gamePort = new FitViewport(main.camera.viewportWidth, Main.camera.viewportHeight, camera);

        camera.position.set((gamePort.getScreenWidth() / 2f) / Constans.PPM, (gamePort.getScreenHeight() / 2f) / Constans.PPM, 0f);
        camera.zoom = 0.2f / Constans.PPM;
        this.batch = new SpriteBatch();

        maploader = new TmxMapLoader();
        map = maploader.load("maps/JungleLevel/Jungle..tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / Constans.PPM);

        // Box2D variables
        world = new World(new Vector2(0, -10f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        BodyDef bdef2 = new BodyDef();
        PolygonShape shape2 = new PolygonShape();
        FixtureDef fdef2 = new FixtureDef();
        Body body2;

        BodyDef bdefPlatform = new BodyDef();
        PolygonShape shapePlatform= new PolygonShape();
        FixtureDef fdefPlatform = new FixtureDef();
        Body platform;

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
            body.createFixture(fdef).setUserData("ground");
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle2 = ((RectangleMapObject) object).getRectangle();


            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;

            bodyDef.position.set((rectangle2.x + rectangle2.width / 2) / Constans.PPM, (rectangle2.y + rectangle2.height / 2) / Constans.PPM
            );

            body2 = world.createBody(bodyDef);
            shape2 = new PolygonShape();
            shape2.setAsBox((rectangle2.width / 2) / Constans.PPM, (rectangle2.height / 2) / Constans.PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape2;
            fixtureDef.isSensor = true;

            body2.createFixture(fixtureDef).setUserData("pit");
        }

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            float x = (rectangle.getX() + rectangle.getWidth() / 2f) / Constans.PPM;
            float y = (rectangle.getY() + rectangle.getHeight() / 2f) / Constans.PPM;
            float halfWidth = rectangle.getWidth() / 2f / Constans.PPM;
            float halfHeight = rectangle.getHeight() / 2f / Constans.PPM;

            bdef.type = BodyDef.BodyType.KinematicBody;
            bdef.position.set(x, y);

            platform = world.createBody(bdef);

            shape.setAsBox(halfWidth, halfHeight);
            fdef.shape = shape;
            platform.createFixture(fdef).setUserData("platform");

            movingPlatforms.add(platform);
        }

        // Moving Platform variables
        minY1 = movingPlatforms.get(0).getPosition().y;
        maxY1 = movingPlatforms.get(0).getPosition().y + 4f;
        movingPlatforms.get(0).setLinearVelocity(0, speedY);



        this.main = main;
        this.player = new Obanana(world, main, 107f, 3.5f);



        shape.dispose();
        shape2.dispose();
    }

    private void update() {
        player.getAttack().updatePunch(Gdx.graphics.getDeltaTime());

        // handle user input
        ObananaInput.inputHandler(player);

        camera.position.x = player.b2body.getPosition().x;
        camera.position.y = player.b2body.getPosition().y;
//        int speed = 10;
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += speed * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= speed * Gdx.graphics.getDeltaTime();

        world.step(1 / 60f, 6, 2);


        // Locks the camera when the player reaches the end
//        if (camera.position.x >= 90.37492 ) {
//            camera.position.set(90.37492f, camera.position.y, 0f);
//        }

        camera.update();

        renderer.setView(camera);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            main.setScreen(new TitleScreen(main));
        }

        for (Trigger t : triggers) {
            t.update(player.b2body.getPosition());
        }

        // Moving Platforms
        if(goingUp && movingPlatforms.get(0).getPosition().y >= maxY1) {
            goingUp = false;
            movingPlatforms.get(0).setLinearVelocity(0, -speedY);
        } else if(!goingUp && movingPlatforms.get(0).getPosition().y <= minY1) {
            goingUp = true;
            movingPlatforms.get(0).setLinearVelocity(0, speedY);
        }

    }

    @Override
    public void show() {
        jungleTheme.play();
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
        if(doorRange) {
            door.update(batch, new TitleScreen(main), player.b2body);
        }
        player.update(delta);
        player.draw(batch);
        batch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (jungleTheme.isPlaying()) {
            jungleTheme.pause();
        }
    }

    @Override
    public void resume() {
        if (!jungleTheme.isPlaying()) {
            jungleTheme.play();
        }
    }

    @Override
    public void hide() {
        jungleTheme.stop();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        map.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        batch.dispose();
        jungleTheme.dispose();
    }
}
