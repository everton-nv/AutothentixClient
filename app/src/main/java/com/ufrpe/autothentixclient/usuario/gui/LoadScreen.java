package com.ufrpe.autothentixclient.usuario.gui;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import static com.ufrpe.autothentixclient.usuario.gui.animation.MyAnimation.getAnimationFadeIn;
import static com.ufrpe.autothentixclient.usuario.gui.animation.MyAnimation.getAnimationFadeOut;

public final class LoadScreen {
    private static LinearLayout progressBarLayout;

    public static void loadOut(Context context, LinearLayout linearLayout){
        progressBarLayout = linearLayout;
        Animation animationFadeOut = getAnimationFadeOut(context);
        animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBarLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        progressBarLayout.startAnimation(animationFadeOut);
    }

    public static void loadOn(Context context, LinearLayout linearLayout){
        progressBarLayout = linearLayout;
        progressBarLayout.setVisibility(View.VISIBLE);
        progressBarLayout.startAnimation(getAnimationFadeIn(context));
    }
}
