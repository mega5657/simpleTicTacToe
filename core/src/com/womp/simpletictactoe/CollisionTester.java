/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.womp.simpletictactoe;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Atog
 */
//credits to superjumper! This code taken from there! thank you very much!
public class CollisionTester {

    public static boolean pointInRectangle(Rectangle r, Vector3 v) {
       
        if (v.x >= r.x && v.x <= r.x + r.width && v.y >= r.y && v.y <= r.y + r.height  ) {
            return true;
        } else {
            return false;
        }
    }
    
     public static boolean pointInRectangle(Rectangle r, int x, int y) {
       
        if (x >= r.x && x <= r.x + r.width && y >= r.y && y <= r.y + r.height  ) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
        if (r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y) {
            return true;
        } else {
            return false;
        }
    }
}