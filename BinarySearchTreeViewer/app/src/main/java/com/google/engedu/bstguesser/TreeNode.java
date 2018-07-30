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

package com.google.engedu.bstguesser;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class TreeNode {
    private static final int SIZE = 60;
    private static final int MARGIN = 20;
    private int value, height;
    protected TreeNode left, right;
    private boolean showValue;
    private int x, y;
    private int color = Color.rgb(150, 150, 250);
    //private TreeNode c = null; //child
    //private TreeNode d = null;//grandchild
    private static TreeNode temp = null;//it has its own use
    private static int i, j; //to denote left or right
    //right -> 0 and left -> 1

    public TreeNode(int value) {
        this.value = value;
        this.height = 0;
        showValue = false;
        left = null;
        right = null;
    }

    public TreeNode insert(int valueToInsert) {
        if (valueToInsert < getValue()) {
            if (left == null) {
                TreeNode newNode = new TreeNode(valueToInsert);
                left = newNode;
                temp = this;
                i = 0;
                updateHeight();
            } else {
                left = left.insert(valueToInsert);
                j = i;
                i = 0;
                updateHeight();
                if (bf() < -1 || bf() > 1) {
                    //temp = rotation(c, d, i, j);
                    TreeNode b = this, c = null, d = null;
                    if (i == 0) {
                        c = left;
                    } else if (i == 1) {
                        c = right;
                    }
                    if (j == 0) {
                        d = c.left;
                    } else if (j == 1) {
                        d = c.right;
                    }
                    if (i == 0 && j == 0) {
                        b.left = c.right;
                        c.right = b;
                        temp = c;
                        b.updateHeight();
                        c.updateHeight();
                    } else if (i == 1 && j == 1) {
                        b.right = c.left;
                        c.left = b;
                        temp = c;
                        b.updateHeight();
                        c.updateHeight();
                    } else if (i == 0 && j == 1) {
                        c.right = d.left;
                        b.left = d;
                        d.left = c;
                        b.left = d.right;
                        d.right = b;
                        temp = d;
                        c.updateHeight();
                        b.updateHeight();
                        d.updateHeight();
                    } else if (i == 1 && j == 0) {
                        c.left = d.right;
                        b.right = d;
                        d.right = c;
                        b.right = d.left;
                        d.left = b;
                        temp = d;
                        c.updateHeight();
                        b.updateHeight();
                        d.updateHeight();
                    }
                } else {
                    temp = this;
                }
            }
            /*if(height <= left.height + 1) {
                height = left.height + 1;
            }*/
            //updateHeight();
        } else if (valueToInsert > getValue()) {
            if (right == null) {
                TreeNode newNode = new TreeNode(valueToInsert);
                right = newNode;
                temp = this;
                i = 1;
                updateHeight();
            } else {
                right = right.insert(valueToInsert);
                j = i;
                i = 1;
                updateHeight();
                if (bf() < -1 || bf() > 1) {
                    //temp = rotation(c, d, i, j);
                    TreeNode b = this, c = null, d = null;
                    if (i == 0) {
                        c = left;
                    } else if (i == 1) {
                        c = right;
                    }
                    if (j == 0) {
                        d = c.left;
                    } else if (j == 1) {
                        d = c.right;
                    }
                    if (i == 0 && j == 0) {
                        b.left = c.right;
                        c.right = b;
                        temp = c;
                        b.updateHeight();
                        c.updateHeight();
                    } else if (i == 1 && j == 1) {
                        b.right = c.left;
                        c.left = b;
                        temp = c;
                        b.updateHeight();
                        c.updateHeight();
                    } else if (i == 0 && j == 1) {
                        c.right = d.left;
                        b.left = d;
                        d.left = c;
                        b.left = d.right;
                        d.right = b;
                        temp = d;
                        c.updateHeight();
                        b.updateHeight();
                        d.updateHeight();
                    } else if (i == 1 && j == 0) {
                        c.left = d.right;
                        b.right = d;
                        d.right = c;
                        b.right = d.left;
                        d.left = b;
                        temp = d;
                        c.updateHeight();
                        b.updateHeight();
                        d.updateHeight();
                    }
                } else {
                    temp = this;
                }
                }
            }
            /*if(height <= right.height + 1) {
                height = right.height + 1;
            }*/
            //updateHeight();
            return temp;
        }

    public int getValue() {
        return value;
    }

    private void updateHeight() {
        int a = -1, b = -1;
        if(right != null) {
            a = right.height;
        }
        if(left != null) {
            b = left.height;
        }
        if(a>b) {
            height = a+1;
        }
        else {
            height = b + 1;
        }
    }

    private int bf() {
        int a = -1, b = -1;
        if(right != null) {
            a = right.height;
        }
        if(left != null) {
            b = left.height;
        }
        return a - b;
    }

    private TreeNode rotation(TreeNode c, TreeNode d, int i, int j) {
        TreeNode b = this;
        if(i == 0 && j == 0) {
            b.left = null;
            c.right = b;
            return c;
        }
        else if(i == 1 && j == 1) {
            b.right = null;;
            c.left = b;
            return c;
        }
        else if(i == 0 && j == 1) {
            b.left = d;
            c.right = null;
            d.left = c;
            b.left = null;
            d.right = b;
            return d;
        }
        else {
            b.right = d;
            c.left = null;
            d.right = c;
            b.right = null;
            d.left = b;
            return d;
        }
    }

    public void positionSelf(int x0, int x1, int y) {
        this.y = y;
        x = (x0 + x1) / 2;

        if(left != null) {
            left.positionSelf(x0, right == null ? x1 - 2 * MARGIN : x, y + SIZE + MARGIN);
        }
        if (right != null) {
            right.positionSelf(left == null ? x0 + 2 * MARGIN : x, x1, y + SIZE + MARGIN);
        }
    }

    public void draw(Canvas c) {
        Paint linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);
        linePaint.setColor(Color.GRAY);
        if (left != null)
            c.drawLine(x, y + SIZE/2, left.x, left.y + SIZE/2, linePaint);
        if (right != null)
            c.drawLine(x, y + SIZE/2, right.x, right.y + SIZE/2, linePaint);

        Paint fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(color);
        c.drawRect(x-SIZE/2, y, x+SIZE/2, y+SIZE, fillPaint);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(SIZE * 2/3);
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawText(showValue ? String.valueOf(value) : "?", x, y + SIZE * 3/4, paint);

        if (height > 0) {
            Paint heightPaint = new Paint();
            heightPaint.setColor(Color.MAGENTA);
            heightPaint.setTextSize(SIZE * 2 / 3);
            heightPaint.setTextAlign(Paint.Align.LEFT);
            c.drawText(String.valueOf(height), x + SIZE / 2 + 10, y + SIZE * 3 / 4, heightPaint);
        }

        if (left != null)
            left.draw(c);
        if (right != null)
            right.draw(c);
    }

    public int click(float clickX, float clickY, int target) {
        int hit = -1;
        if (Math.abs(x - clickX) <= (SIZE / 2) && y <= clickY && clickY <= y + SIZE) {
            if (!showValue) {
                if (target != value) {
                    color = Color.RED;
                } else {
                    color = Color.GREEN;
                }
            }
            showValue = true;
            hit = value;
        }
        if (left != null && hit == -1)
            hit = left.click(clickX, clickY, target);
        if (right != null && hit == -1)
            hit = right.click(clickX, clickY, target);
        return hit;
    }

    public void invalidate() {
        color = Color.CYAN;
        showValue = true;
    }
}
