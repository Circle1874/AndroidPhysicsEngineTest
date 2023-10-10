package com.stav.physicsengine;


import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TestActivity extends AppCompatActivity {

    private ImageView im1, im2;
    private boolean viewsInitialized = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        im1 = findViewById(R.id.imageView1);
        im2 = findViewById(R.id.imageView2);
        PhysicsHandler physicsHandler = new PhysicsHandler();
    }

    @Override
    protected void onStart(){
        super.onStart();
        ViewTreeObserver vto = im1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!viewsInitialized) {
                    double im1CenterX = getCenterX(im1);
                    double im1CenterY = getCenterY(im1);
                    double im2CenterX = getCenterX(im2);
                    double im2CenterY = getCenterY(im2);

                    PhysicsObject po1 = new PhysicsObject("Image 1", im1CenterX, im1CenterY, im1.getWidth(), im1.getHeight(), 5);
                    PhysicsObject po2 = new PhysicsObject("Image 2", im2CenterX, im2CenterY, im2.getWidth(), im2.getHeight(), 5);
                    Log.v("SCREEN_INFO", String.format(Locale.getDefault(), "Screen Dimensions: {\n\tscreen width: %d\n\tscreen height: %d\n}", PhysicsObject.MAX_X, PhysicsObject.MAX_Y));
                    Log.v("PHYSICS_OBJECT", po1.toString());
                    Log.v("IMAGE_VIEW", imageViewMsg(im1));
                    Log.v("PHYSICS_OBJECT", po2.toString());
                    Log.v("IMAGE_VIEW", imageViewMsg(im2));
                }
            }
        });
    }

    private double getCenterY(ImageView im) {
        return im.getY() + (double)im.getHeight()/2;
    }

    private double getCenterX(ImageView im) {
        return im.getX() + (double)im.getWidth()/2;
    }

    private String imageViewMsg(ImageView im){
        return String.format(Locale.getDefault(), "x: %f\n" +
                "y: %f\n" +
                "width: %d\n" +
                "height: %d",
                im.getX(), im.getY(), im.getWidth(), im.getHeight());
    }
}