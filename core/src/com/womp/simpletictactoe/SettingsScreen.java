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

/**
 *
 * @author Atog
 */
public class SettingsScreen implements Screen {

    Color backColor = Color.BLACK;
    Color XColor = Color.WHITE;
    Color OColor = Color.WHITE;
    Color gridColor = Color.WHITE;
    Color fontColor = Color.WHITE;
    Rectangle XBounds, OBounds, gridBounds, fontBounds, backBounds, backButtonBounds;
    int backCount = 0, XCount = 17, OCount = 17, gridCount = 17, fontCount = 17;
    SpriteBatch batcher;
    OrthographicCamera cam;
    TicTacToe game;
    InputHandler inputHandler;
    ShapeRenderer rend;

//    black = 0
//    blue = 1
//    clear = 2
//    cyan = 3
//    dark gray = 4
//    gray = 5
//    green = 6
//    light gray = 7
//    magenta = 8
//    maroon = 9
//    navy = 10
//    olive = 11
//    orange = 12
//    pink = 13
//    purple = 14
//    red = 15
//    teal = 16
//    white = 17
//    yellow = 18
    public SettingsScreen(TicTacToe game) {
        cam = new OrthographicCamera(400, 450);
        cam.position.set(game.DEVICE_WIDTH/ 2, game.DEVICE_HEIGHT / 2, 0);
        this.game = game;
        batcher = new SpriteBatch();
        inputHandler = new InputHandler();
        rend = new ShapeRenderer();
        backBounds = new Rectangle(250, 335, 25, 25);
        XBounds = new Rectangle(155, 300, 25, 25);
        OBounds = new Rectangle(155, 265, 25, 25);
        gridBounds = new Rectangle(181, 230, 25, 25);
        fontBounds = new Rectangle(181, 195, 25, 25);
        backButtonBounds = new Rectangle(45, 34, 50, 20);

        Gdx.input.setInputProcessor(inputHandler);

    }

    @Override
    public void render(float f) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batcher.setProjectionMatrix(cam.combined);

        batcher.begin();

        game.optionFont.draw(batcher, "Background Color:", 80, 355);
        game.optionFont.draw(batcher, "X Color:", 80, 320);
        game.optionFont.draw(batcher, "O Color:", 80, 285);
        game.optionFont.draw(batcher, "Grid Color:", 80, 250);
        game.optionFont.draw(batcher, "Font Color:", 80, 215);
        game.optionFont.draw(batcher, "Back", 50, 50);
        batcher.end();

        rend.begin(ShapeRenderer.ShapeType.Filled);

        rend.setColor(backColor);
        rend.rect(backBounds.x, backBounds.y, backBounds.width, backBounds.height);

        rend.setColor(XColor);
        rend.rect(XBounds.x, XBounds.y, XBounds.width, XBounds.height);

        rend.setColor(OColor);
        rend.rect(OBounds.x, OBounds.y, OBounds.width, OBounds.height);

        rend.setColor(gridColor);
        rend.rect(gridBounds.x, gridBounds.y, gridBounds.width, gridBounds.height);

        rend.setColor(fontColor);
        rend.rect(fontBounds.x, fontBounds.y, fontBounds.width, fontBounds.height);

        rend.end();

        //  System.out.println(inputHandler.getClickedPosX());
        update();
        inputHandler.update();

    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void show() {
        cam.position.set(game.DEVICE_WIDTH / 2, game.DEVICE_HEIGHT/ 2, 0);
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
    }

    private void update() {

        if (CollisionTester.pointInRectangle(backBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            backCount++;
        }

        if (CollisionTester.pointInRectangle(XBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            XCount++;
        }

        //System.out.println(inputHandler.clickedPosX);
        if (CollisionTester.pointInRectangle(OBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            OCount++;
        }

        if (CollisionTester.pointInRectangle(gridBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            gridCount++;
        }

        if (CollisionTester.pointInRectangle(fontBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            fontCount++;
        }
        calculateColor();
        if (CollisionTester.pointInRectangle(backButtonBounds, inputHandler.getClickedPosX(), inputHandler.getClickedPosY())) {
            game.setScreen(game.menuScreen);
        }
    }

    private void calculateColor() {
//    black = 0
//    blue = 1
//    clear = 2
//    cyan = 3
//    dark gray = 4
//    gray = 5
//    green = 6
//    light gray = 7
//    magenta = 8
//    maroon = 9
//    navy = 10
//    olive = 11
//    orange = 12
//    pink = 13
//    purple = 14
//    red = 15
//    teal = 16
//    white = 17
//    yellow = 18

        if (backCount == 0) {
            backColor = Color.BLACK;
        }

        if (backCount == 1) {
            backColor = Color.BLUE;
        }
        if (backCount == 2) {
            backColor = Color.CLEAR;
        }
        if (backCount == 3) {
            backColor = Color.CYAN;
        }
        if (backCount == 4) {
            backColor = Color.DARK_GRAY;
        }
        if (backCount == 5) {
            backColor = Color.GRAY;
        }
        if (backCount == 6) {
            backColor = Color.GREEN;
        }
        if (backCount == 7) {
            backColor = Color.LIGHT_GRAY;
        }
        if (backCount == 8) {
            backColor = Color.MAGENTA;
        }
        if (backCount == 9) {
            backColor = Color.MAROON;
        }
        if (backCount == 10) {
            backColor = Color.NAVY;
        }
        if (backCount == 11) {
            backColor = Color.OLIVE;
        }
        if (backCount == 12) {
            backColor = Color.ORANGE;
        }
        if (backCount == 13) {
            backColor = Color.PINK;
        }

        if (backCount == 14) {
            backColor = Color.PURPLE;
        }
        if (backCount == 15) {
            backColor = Color.RED;
        }
        if (backCount == 16) {
            backColor = Color.TEAL;
        }
        if (backCount == 17) {
            backColor = Color.WHITE;
        }
        if (backCount == 18) {
            backColor = Color.YELLOW;
        }

        if (XCount == 0) {
            XColor = Color.BLACK;
        }

        if (XCount == 1) {
            XColor = Color.BLUE;
        }
        if (XCount == 2) {
            XColor = Color.CLEAR;
        }
        if (XCount == 3) {
            XColor = Color.CYAN;
        }
        if (XCount == 4) {
            XColor = Color.DARK_GRAY;
        }
        if (XCount == 5) {
            XColor = Color.GRAY;
        }
        if (XCount == 6) {
            XColor = Color.GREEN;
        }
        if (XCount == 7) {
            XColor = Color.LIGHT_GRAY;
        }
        if (XCount == 8) {
            XColor = Color.MAGENTA;
        }
        if (XCount == 9) {
            XColor = Color.MAROON;
        }
        if (XCount == 10) {
            XColor = Color.NAVY;
        }
        if (XCount == 11) {
            XColor = Color.OLIVE;
        }
        if (XCount == 12) {
            XColor = Color.ORANGE;
        }
        if (XCount == 13) {
            XColor = Color.PINK;
        }

        if (XCount == 14) {
            XColor = Color.PURPLE;
        }
        if (XCount == 15) {
            XColor = Color.RED;
        }
        if (XCount == 16) {
            XColor = Color.TEAL;
        }
        if (XCount == 17) {
            XColor = Color.WHITE;
        }
        if (XCount == 18) {
            XColor = Color.YELLOW;
        }

        if (OCount == 0) {
            OColor = Color.BLACK;
        }

        if (OCount == 1) {
            OColor = Color.BLUE;
        }
        if (OCount == 2) {
            OColor = Color.CLEAR;
        }
        if (OCount == 3) {
            OColor = Color.CYAN;
        }
        if (OCount == 4) {
            OColor = Color.DARK_GRAY;
        }
        if (OCount == 5) {
            OColor = Color.GRAY;
        }
        if (OCount == 6) {
            OColor = Color.GREEN;
        }
        if (OCount == 7) {
            OColor = Color.LIGHT_GRAY;
        }
        if (OCount == 8) {
            OColor = Color.MAGENTA;
        }
        if (OCount == 9) {
            OColor = Color.MAROON;
        }
        if (OCount == 10) {
            OColor = Color.NAVY;
        }
        if (OCount == 11) {
            OColor = Color.OLIVE;
        }
        if (OCount == 12) {
            OColor = Color.ORANGE;
        }
        if (OCount == 13) {
            OColor = Color.PINK;
        }
        if (OCount == 14) {
            OColor = Color.PURPLE;
        }
        if (OCount == 15) {
            OColor = Color.RED;
        }
        if (OCount == 16) {
            OColor = Color.TEAL;
        }
        if (OCount == 17) {
            OColor = Color.WHITE;
        }
        if (OCount == 18) {
            OColor = Color.YELLOW;
        }

        if (gridCount == 0) {
            gridColor = Color.BLACK;
        }

        if (gridCount == 1) {
            gridColor = Color.BLUE;
        }
        if (gridCount == 2) {
            gridColor = Color.CLEAR;
        }
        if (gridCount == 3) {
            gridColor = Color.CYAN;
        }
        if (gridCount == 4) {
            gridColor = Color.DARK_GRAY;
        }
        if (gridCount == 5) {
            gridColor = Color.GRAY;
        }
        if (gridCount == 6) {
            gridColor = Color.GREEN;
        }
        if (gridCount == 7) {
            gridColor = Color.LIGHT_GRAY;
        }
        if (gridCount == 8) {
            gridColor = Color.MAGENTA;
        }
        if (gridCount == 9) {
            gridColor = Color.MAROON;
        }
        if (gridCount == 10) {
            gridColor = Color.NAVY;
        }
        if (gridCount == 11) {
            gridColor = Color.OLIVE;
        }
        if (gridCount == 12) {
            gridColor = Color.ORANGE;
        }
        if (gridCount == 13) {
            gridColor = Color.PINK;
        }

        if (gridCount == 14) {
            gridColor = Color.PURPLE;
        }
        if (gridCount == 15) {
            gridColor = Color.RED;
        }
        if (gridCount == 16) {
            gridColor = Color.TEAL;
        }
        if (gridCount == 17) {
            gridColor = Color.WHITE;
        }
        if (gridCount == 18) {
            gridColor = Color.YELLOW;
        }

        if (fontCount == 0) {
            fontColor = Color.BLACK;
        }
        if (fontCount == 1) {
            fontColor = Color.BLUE;
        }
        if (fontCount == 2) {
            fontColor = Color.CLEAR;
        }
        if (fontCount == 3) {
            fontColor = Color.CYAN;
        }
        if (fontCount == 4) {
            fontColor = Color.DARK_GRAY;
        }
        if (fontCount == 5) {
            fontColor = Color.GRAY;
        }
        if (fontCount == 6) {
            fontColor = Color.GREEN;
        }
        if (fontCount == 7) {
            fontColor = Color.LIGHT_GRAY;
        }
        if (fontCount == 8) {
            fontColor = Color.MAGENTA;
        }
        if (fontCount == 9) {
            fontColor = Color.MAROON;
        }
        if (fontCount == 10) {
            fontColor = Color.NAVY;
        }
        if (fontCount == 11) {
            fontColor = Color.OLIVE;
        }
        if (fontCount == 12) {
            fontColor = Color.ORANGE;
        }
        if (fontCount == 13) {
            fontColor = Color.PINK;
        }

        if (fontCount == 14) {
            fontColor = Color.PURPLE;
        }
        if (fontCount == 15) {
            fontColor = Color.RED;
        }
        if (fontCount == 16) {
            fontColor = Color.TEAL;
        }
        if (fontCount == 17) {
            fontColor = Color.WHITE;
        }
        if (fontCount == 18) {
            fontColor = Color.YELLOW;
        }

        if (backCount == 19) {
            backCount = 0;
        }

        if (XCount == 19) {
            XCount = 0;
        }

        if (OCount == 19) {
            OCount = 0;
        }

        if (gridCount == 19) {
            gridCount = 0;
        }

        if (fontCount == 19) {
            fontCount = 0;
        }

    }

    public Color getXColor() {
        return XColor;
    }

    public void setCurrentXColor(Color XColor) {
        this.XColor = XColor;
    }

    public Color getOColor() {
        return OColor;
    }

    public void setCurrentOColor(Color OColor) {
        this.OColor = OColor;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setCurrentGrid(Color currentTicTacGrid) {
        this.gridColor = currentTicTacGrid;
    }

    public void setCurrentFont(Color fontColor) {
        this.fontColor = fontColor;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
    
}
