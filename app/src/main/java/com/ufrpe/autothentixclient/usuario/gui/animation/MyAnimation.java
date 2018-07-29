package com.ufrpe.autothentixclient.usuario.gui.animation;

import android.content.Context;
import android.view.animation.AnimationUtils;

public class MyAnimation {
    public static android.view.animation.Animation getAnimationFadeIn(Context context) {
        return AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
    }

    public static android.view.animation.Animation getAnimationFadeOut(Context context) {
        return AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
    }

    public static android.view.animation.Animation getAnimationSlideIn(Context context) {
        return AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
    }

    public static android.view.animation.Animation getAnimationSlideOut(Context context) {
        return AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
    }
}
