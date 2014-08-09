/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.womp.simpletictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 *
 * @author Atog
 */
public class InputHandler implements InputProcessor {

    int clickedPosX = 0;
    int clickedPosY = 0;

    public InputHandler() {
        clickedPosX = 0;
        clickedPosY = 0;

    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int i2, int i3) {
        clickedPosX = x;
        clickedPosY = Gdx.graphics.getHeight() - y;

        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;

    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;

    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

    public void update() {
        clickedPosX = 0;
        clickedPosY = 0;
    }

    public int getClickedPosX() {
        return clickedPosX;
    }

    public int getClickedPosY() {
        return clickedPosY;
    }

    public void setClickedPosX(int n) {
        clickedPosX = n;
    }

    public void setClickedPosY(int n) {
        clickedPosY = n;
    }
}
