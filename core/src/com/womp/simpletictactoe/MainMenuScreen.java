/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.womp.simpletictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Atog
 */
public class MainMenuScreen implements Screen {

    TicTacToe game;

    ShapeRenderer rend;
    SpriteBatch batcher;
    OrthographicCamera cam;
    
    Vector3 touchPos;
    int adjustment = 30;
    int adjustment2 = 40;
    int SCREEN_WIDTH = 0;
    int SCREEN_HEIGH = 0;

    Rectangle ngBounds, pHolderBounds, optionBounds;
    InputHandler inputHandler;

    public MainMenuScreen(TicTacToe game) {
        this.game = game;
        
        inputHandler = new InputHandler();
        batcher = new SpriteBatch();
        cam = new OrthographicCamera(400, 450);
        touchPos = new Vector3(0, 0, 0);
        rend = new ShapeRenderer();

        ngBounds = new Rectangle(138, 160, 120, 25);
        optionBounds = new Rectangle(138, 130, 120, 25);
        pHolderBounds = new Rectangle(128, 120, 300, 25);

        rend = new ShapeRenderer();

        cam.position.set(400 / 2, 450 / 2, 0);
        game.titleFont.setColor(Color.WHITE);
        game.tictactoeFont.setColor(Color.WHITE);

        Gdx.input.setInputProcessor(inputHandler);
        this.game = game;

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();

        batcher.setProjectionMatrix(cam.combined);

        batcher.begin();

        game.tictactoeFont.draw(batcher, "New Game", 100 + adjustment2, 180);
        game.tictactoeFont.draw(batcher, "Options", 100 + adjustment2, 150);
        game.tictactoeFont.draw(batcher, "PlaceHolder", 100 + adjustment2, 120);
        game.titleFont.drawWrapped(batcher, "XTICXTACXTOE", 135, 350 + adjustment, 130);

//         game.titleFont.drawWrapped(batcher, "XOXOHUGS", 135, 350 + adjustment, 150);
//        game.titleFont.drawWrapped(batcher, "KISSES", 135, 250 + adjustment, 170);
        batcher.end();
        if (TicTacToe.debug) {
            rend.begin(ShapeRenderer.ShapeType.Line);
            rend.rect(ngBounds.x, ngBounds.y, ngBounds.width, ngBounds.height);
            rend.rect(optionBounds.x, optionBounds.y, optionBounds.width, optionBounds.height);
            rend.end();
        }
        update();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void show() {
        cam.position.set(400 / 2, 450 / 2, 0);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        game.dispose();
        batcher.dispose();
        rend.dispose();
        game.tictactoeFont.dispose();
        game.titleFont.dispose();
    }

    public void update() {
        touchPos.x = 0;
        touchPos.y = 0;

        if (Gdx.input.justTouched()) {
            cam.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            System.out.println(touchPos.x);
            System.out.println(touchPos.y);
        }

        if (CollisionTester.pointInRectangle(ngBounds, touchPos)) {
            System.out.println("Womp Womp");
            game.setScreen(game.gameScreen);

        } // end of new game bounds checker

        if (CollisionTester.pointInRectangle(optionBounds, touchPos)) {
            game.setScreen(game.settingsScreen);
        }

    }
}
