/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.method.NumberKeyListener;

import java.util.ArrayList;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private int steps = 0;
    private PuzzleBoard previousBoard = null;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },//left
            { 1, 0 },//right
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        tiles = new ArrayList< >();
        bitmap = Bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth, true);
        for(int y = 0; y != NUM_TILES; y++) {
            for(int x = 0; x != NUM_TILES; x++) {
                int index = y * NUM_TILES + x;
                if(index == NUM_TILES * NUM_TILES - 1) {
                    tiles.add(null);
                }
                else {
                    Bitmap tile = Bitmap.createBitmap(bitmap, x * parentWidth / NUM_TILES, y * parentWidth / NUM_TILES, parentWidth/NUM_TILES, parentWidth/NUM_TILES);
                    tiles.add(new PuzzleTile(tile, index));
                }
            }
        }
    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        steps = otherBoard.steps + 1;
        previousBoard = otherBoard;
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {

        ArrayList<PuzzleBoard> fullTiles = new ArrayList<>();
        int xcord = 0, ycord = 0;
        for(int i = 0; i < NUM_TILES*NUM_TILES; i++) {
            if(tiles.get(i) == null) {
                xcord = i % NUM_TILES;
                ycord = i / NUM_TILES;
            }
        }

        for(int[] delta: NEIGHBOUR_COORDS) {
            int tilex = xcord + delta[0];
            int tiley = ycord + delta[1];
            if(tilex >= 0 && tilex < NUM_TILES && tiley >= 0 && tiley < NUM_TILES) {
                PuzzleBoard newBoard = new PuzzleBoard(this);
                newBoard.swapTiles(XYtoIndex(tilex, tiley), XYtoIndex(xcord, ycord));
                fullTiles.add(newBoard);
            }
        }
        return fullTiles;
    }

    public int priority() {

        return 0;
    }

}
