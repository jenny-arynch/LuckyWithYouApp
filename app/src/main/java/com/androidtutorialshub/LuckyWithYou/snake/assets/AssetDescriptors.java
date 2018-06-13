package com.androidtutorialshub.LuckyWithYou.snake.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {


    public static final AssetDescriptor<BitmapFont> UI_FONT =
            new AssetDescriptor<>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<Sound> COIN_SOUND =
            new AssetDescriptor<>(AssetPaths.COIN_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> LOSE_SOUND =
            new AssetDescriptor<>(AssetPaths.LOSE_SOUND, Sound.class);

    private AssetDescriptors() {
    }

}
