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

package com.google.engedu.touringmusician;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class TourMap extends View {

    private Bitmap mapImage;
    private CircularLinkedList list = new CircularLinkedList();
    private String insertMode = "Add";
    private int colour = 1;

    public TourMap(Context context) {
        super(context);
        mapImage = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.map);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mapImage, 0, 0, null);
        Paint pointPaint = new Paint();
        Paint linePaint = new Paint();
        pointPaint.setColor(Color.RED);
        if(colour == 1) {
            linePaint.setColor(Color.BLACK);
        }
        else if(colour == 2) {
            linePaint.setColor(Color.BLUE);
        }
        else if (colour == 3) {
            linePaint.setColor(Color.MAGENTA);
        }
        int i = 0;
        Point h = null;
        Point temp = null;
        boolean flag = false;
        for (Point p : list) {
            if(flag) {
                canvas.drawLine(p.x, p.y, temp.x, temp.y, linePaint);
            }
            else {
                h = p;
            }
            temp = p;
            canvas.drawCircle(p.x, p.y, 15, pointPaint);
            i++;
            flag = true;
        }
        if(i > 1) {
            canvas.drawLine(h.x, h.y, temp.x, temp.y, linePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point p = new Point((int) event.getX(), (int)event.getY());
                if (insertMode.equals("Closest")) {
                    colour = 1;
                    list.insertNearest(p);
                } else if (insertMode.equals("Smallest")) {
                    colour = 2;
                    list.insertSmallest(p);
                } else {
                    colour = 3;
                    list.insertBeginning(p);
                }
                /*else {
                    colour = 1;
                    list.insertNearest(p);
                    colour = 2;
                    list.insertSmallest(p);
                    colour = 3;
                    list.insertBeginning(p);
                }*/
                TextView message = (TextView) ((Activity) getContext()).findViewById(R.id.game_status);
                if (message != null) {
                    message.setText(String.format("Tour length is now %.2f", list.totalDistance()));
                }
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void reset() {
        list.reset();
        invalidate();
    }

    public void setInsertMode(String mode) {
        insertMode = mode;
    }
}
