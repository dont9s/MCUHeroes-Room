package com.mcu.nikhil.mcuheroes.util;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mcu.nikhil.mcuheroes.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url){
        if (url == null) {
            imageView.setImageResource(R.drawable.default_image);
        }else {
            Picasso.get()
                    .load(url)
                    .error(R.drawable.default_image)
                    .into(imageView , new PaletteCallback(imageView){
                        @Override
                        public void onPalette(Palette palette) {
                            if (palette != null) {
                                ViewGroup parent = ((ViewGroup) imageView.getParent().getParent());
                                parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
                            }
                        }
                    });
        }
    }

    private static abstract class PaletteCallback implements Callback {

        private  final ImageView target;

        public PaletteCallback(final ImageView imageView) {target  = imageView;}


        @Override
        public void onSuccess() {
            onPalette(Palette.from(((BitmapDrawable) target.getDrawable()).getBitmap()).generate());
        }

        @Override
        public void onError(Exception e) {onPalette(null);}

        public abstract void onPalette(final Palette palette);
    }
}
