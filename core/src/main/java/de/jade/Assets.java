package de.jade;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.io.File;

public class Assets {

    private Assets() {
    };

    public static final AssetDescriptor<BitmapFont> HUD_FONT = new AssetDescriptor<BitmapFont>("fonts" + File.separator + "yoster.fnt",
        BitmapFont.class);

    public static final AssetDescriptor<Texture> TITLE_BACKGROUND = new AssetDescriptor<Texture>("sprites/ui" + File.separator + "TheLostLettersTitleScreen.png",
        Texture.class);

    public static final AssetDescriptor<Texture> LOGO = new AssetDescriptor<Texture>("sprites/ui" + File.separator + "Logo.png",
        Texture.class);

    public static final AssetDescriptor<Texture> START_BUTTON = new AssetDescriptor<Texture>("sprites/ui" + File.separator + "Playbutton.png",
        Texture.class);

    public static final AssetDescriptor<Texture> EXIT_BUTTON = new AssetDescriptor<Texture>("sprites/ui" + File.separator + "Exitbutton.png",
        Texture.class);

    public static final AssetDescriptor<Texture> OBANANA = new AssetDescriptor<Texture>("sprites/characters" + File.separator + "ObamaSpriteSheet.png",
        Texture.class);

    //public static final AssetDescriptor<TextureAtlas> = new AssetDescriptor<TextureAtlas>("ObamaSpriteSheet.png")
}
