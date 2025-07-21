package de.jade.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.jade.Assets;
import de.jade.Main;
import de.jade.helper.TileMapHelper;
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

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    public GameScreen(Main main, OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.main = main;
        this.player = new Obanana();

        this.tileMapHelper = new TileMapHelper();
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
    }

    private void update() {
        world.step(1 / 60f, 6,2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            main.setScreen(new TitleScreen(main));
        }
    }

    private void cameraUpdate() {
        camera.position.set(280f + player.getX(),170f + player.getY(),0f);
        camera.zoom = 0.18f; // Default = 0.18f
        camera.update();
    }

    @Override
    public void show() {
        camera.setToOrtho(false);
        stage = new Stage();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        batch.begin();
        stage.draw();
        batch.draw(main.getAssetManager().get(Assets.OBANANA), player.getX(), player.getY());

        player.update(); // handle input
        player.render(camera);

        batch.end();
        box2DDebugRenderer.render(world, new Matrix4(camera.combined).scl(PPM));
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

    }

}
