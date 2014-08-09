/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.womp.simpletictactoe;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Atog
 */
public class Grid {
//X is equal to 1 O is equal to 2

    Rectangle bounds;
    boolean marked = false;
    int type;

    public Grid(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }

    public void mark(int xORo) {
        marked = true;
        type = xORo;
    }

    public void render(ShapeRenderer rend) {
        
    }
}
