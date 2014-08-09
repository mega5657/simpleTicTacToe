/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.womp.simpletictactoe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 *
 * @author Atog
 */
public class TicTacToe extends Game {

    boolean firstTimeCreating = true;
    public static boolean debug = false;
    MainMenuScreen menuScreen;
    GameScreen gameScreen;
    SettingsScreen settingsScreen;
    FPSLogger fps;
    BitmapFont markingFont, optionFont, titleFont, symbolsFont, tictactoeFont;

    public static float DEVICE_WIDTH = 0;
    public static float DEVICE_HEIGHT = 0;

    @Override
    public void create() {
        fps = new FPSLogger();

        DEVICE_WIDTH = Gdx.graphics.getWidth();
        DEVICE_HEIGHT = Gdx.graphics.getHeight();

        System.out.println(Gdx.graphics.getWidth());
        System.out.println(Gdx.graphics.getHeight());
        // markingFont

        markingFont = new BitmapFont(Gdx.files.internal("data/markingFont.fnt"), false);
        markingFont.setScale((DEVICE_WIDTH * DEVICE_HEIGHT) / (1280 * 720));

        optionFont = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
        symbolsFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
        tictactoeFont = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);

        settingsScreen = new SettingsScreen(this);
        menuScreen = new MainMenuScreen(this);
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);

    }

    @Override
    public void render() {
        super.render();
        //fps.log();

    }

}
