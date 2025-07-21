package de.jade.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Obanana {
    public float x,y;
    public float dx, dy;

    public Obanana(){
        x = Gdx.graphics.getWidth()/2;
        y = Gdx.graphics.getHeight()/2;
        dx = 5.5f;
        dy = 5.5f;

    }
    public void render(OrthographicCamera cam){ // Changed to Texture Atlas
        cam.position.x = this.getX();
        cam.position.y = this.getY();
        cam.update();
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= dx;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x += dx;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= dy;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            y += dy;
        }
    }
    public void setPos(float x, float y){
        x = this.x;
        y = this.y;
    }


    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
}
