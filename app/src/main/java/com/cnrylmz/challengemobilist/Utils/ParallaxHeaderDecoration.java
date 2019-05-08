package com.cnrylmz.challengemobilist.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Caner on 08.05.2019.
 */

public class ParallaxHeaderDecoration extends RecyclerView.ItemDecoration {

    private Bitmap mImage;

    public ParallaxHeaderDecoration(final Context context, @DrawableRes int resId) {
        mImage = BitmapFactory.decodeResource(context.getResources(), resId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(view) == 22) {
                int offset = view.getTop() / 3;
                c.drawBitmap(mImage, new Rect(0, offset, mImage.getWidth(), view.getHeight() + offset),
                        new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()), null);
            }
        }

    }
}
