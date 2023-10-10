package com.stav.physicsengine;


import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * The fundamental class of basic objects. Includes location on-screen (x and y), material, and size (height and width).
 */
public class PhysicsObject {

    //constants:


    /**
     * The minimum value of x. In other words, the left side of the screen.
     * (is equal to 0).
     */
    public static final int MIN_X = 0;

    /**
     * The maximum value of x. In other words, the right side of the screen.
     * (Resources.getSystem.getDisplayMetrics().widthPixels).
     */
    public static final int MAX_X = Resources.getSystem().getDisplayMetrics().widthPixels;

    /**
     * The minimum value of x. In other words, the bottom of the screen.
     * (is equal to 0).
     */
    public static final int MIN_Y = 0;

    /**
     * The maximum value of y. In other words, the top of the screen.
     * (Resources.getSystem.getDisplayMetrics().heightPixels).
     */
    public static final int MAX_Y = Resources.getSystem().getDisplayMetrics().heightPixels;


    //non-final fields:


    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s center point's x position, measured in dp (0 - left of the screen, MAX - right of the screen).
     */
    protected double centerX;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s center point's y position, measured in dp (0 - top of the screen, MAX - bottom of the screen).
     */
    protected double centerY;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s left-most side's x position, measured in dp (0 - left of the screen, MAX - right of the screen).
     */
    protected double startX;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s right-most side's x position, measured in dp (0 - left of the screen, MAX - right of the screen).
     */
    protected double endX;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s top side's y position, measured in dp (0 - top of the screen, MAX - bottom of the screen).
     */
    protected double topY;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s bottom side's y position, measured in dp (0 - top of the screen, MAX - bottom of the screen).
     */
    protected double bottomY;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>s width, measured in pixels.
     */
    protected int width;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s height, measured in pixels.
     */
    protected int height;

    /**
     * The material of the <a href="#{@link}">{@link PhysicsObject}</a>. Used in calculations of forces and acceleration.
     *
     * @see Material
     */
    protected Material material;

    /**
     * The <a href="#{@link}">{@link PhysicsObject}</a>'s name. Used to clarify which PhysicsObject is which.
     */
    protected String name;

    /**
     * The {@link PhysicsObject}'s current x-axis velocity (positive - right, negative - left).
     */
    protected double xVelocity;

    /**
     * The {@link PhysicsObject}'s current y-axis velocity (positive - down, negative - up).
     */
    protected double yVelocity;

    /**
     * The {@link PhysicsObject}'s current x-axis acceleration (positive - right, negative - left).
     */
    protected double xAcceleration;

    /**
     * The {@link PhysicsObject}'s current y-axis acceleration (positive - down, negative - up).
     */
    protected double yAcceleration;


    //constructors:

    /**
     * Constructs a <a href="#{@link}">{@link PhysicsObject}</a> with a specified {@link Material}.
     * Velocity and acceleration are automatically set to 0.
     *
     * @param name The name of the object.
     * @param centerX the center x coordinate of the object.
     * @param centerY the center y coordinate of the object.
     * @param width the width of the object.
     * @param height the height of the object.
     * @param material the Material the object is made of.
     */
    public PhysicsObject(String name, int centerX, int centerY, int width, int height, Material material) {
        try{
            this.name = name;

            this.centerX = centerX;
            this.centerY = centerY;
            this.startX = centerX - (double)width/2;
            this.endX = centerX + (double)width/2;
            this.topY = centerY - (double)height/2;
            this.bottomY = centerY + (double)height/2;
            xVelocity = 0;  yVelocity = 0; xAcceleration = 0; yAcceleration = 0;

            this.width = width;
            this.height = height;
            this.material = material;

            if((startX < MIN_X) || (endX > MAX_X) || (topY < MIN_Y) || (bottomY > MAX_Y))
                throw new ObjectOutOfBoundsException(makeOutOfBoundsMessage(name, centerX, centerY, width, height));
            Log.v("PHYSICS_OBJECT", String.format(Locale.getDefault(), "Created PhysicsObject named %s.", name));
        }
        catch (ObjectOutOfBoundsException e) {
            Log.e("PHYSICS_ERROR", e.getMessage(), e);
        }
    }

    /**
     * Constructs a <a href="#{@link}">{@link PhysicsObject}</a> with no material given, but the mass must be known.
     * Creates the material on its own and gives it an auto-generated name.
     *
     * @param name The name of the object.
     * @param centerX the center x coordinate of the object.
     * @param centerY the center y coordinate of the object.
     * @param width the width of the object.
     * @param height the height of the object.
     * @param mass the mass of the object.
     */
    public PhysicsObject(String name, double centerX, double centerY, int width, int height, double mass) {
        try{
            this.name = name;

            this.centerX = centerX;
            this.centerY = centerY;
            this.startX = centerX - (double)width/2;
            this.endX = centerX + (double)width/2;
            this.topY = centerY - (double)height/2;
            this.bottomY = centerY + (double)height/2;

            this.width = width;
            this.height = height;
            this.material = new Material(mass, area(height, width));

            if((startX < MIN_X) || (endX > MAX_X) || (topY < MIN_Y) || (bottomY > MAX_Y))
                throw new ObjectOutOfBoundsException(makeOutOfBoundsMessage(name, centerX, centerY, width, height));
            Log.v("PHYSICS_OBJECT", String.format(Locale.getDefault(), "Created PhysicsObject named %s.", name));
        }
        catch (ObjectOutOfBoundsException e) {
            Log.e("PHYSICS_ERROR", e.getMessage(), e);
        }
    }


    //other methods:


    /**
     * Makes and returns the error message of the ObjectOutOfBoundsException.
     *
     * @param name The name of the object.
     * @param x the center x coordinate of the object.
     * @param y the center y coordinate of the object.
     * @param width the width of the object.
     * @param height the height of the object.
     * @return The error message.
     * @see ObjectOutOfBoundsException
     * @see PhysicsObject
     */
    private String makeOutOfBoundsMessage(String name, double x, double y, int width, int height) {

        return String.format(Locale.getDefault(), "Object was created out of bounds. Object %s:\n" +
                        "Top left: (%f, %f)\n" +
                        "Top right: (%f, %f)\n" +
                        "Bottom left: (%f, %f)\n" +
                        "Bottom right: (%f, %f) \n" +
                        "Screen height: %d\n" +
                        "Screen width: %d",
               name, x-width/2, y-height/2, x+width/2, y-height/2, x-width/2, y+height/2, x+width/2, y+height/2 , MAX_X, MAX_Y);
    }

    /**
     * Makes and returns the error message of the ObjectOutOfBoundsException.
     * Specifically made for any displacements.
     *
     * @param name The name of the object.
     * @param x the center x coordinate of the object.
     * @param y the center y coordinate of the object.
     * @param width the width of the object.
     * @param height the height of the object.
     * @return The error message.
     * @see ObjectOutOfBoundsException
     * @see PhysicsObject
     */
    private String makePotentialOutOfBoundsMessage(String name, double x, double y, int width, int height) {

        return String.format(Locale.getDefault(), "Attempted to create Object out of bounds. Object %s:\n" +
                        "Top left: (%f, %f)\n" +
                        "Top right: (%f, %f)\n" +
                        "Bottom left: (%f, %f)\n" +
                        "Bottom right: (%f, %f) \n" +
                        "Screen height: %d\n" +
                        "Screen width: %d",
                name, x-width/2, y-height/2, x+width/2, y-height/2, x-width/2, y+height/2, x+width/2, y+height/2 , MAX_X, MAX_Y);
    }

    private String makePotentialOutOfBoundsMessage(PhysicsObject physicsObject) {



        return String.format(Locale.getDefault(), "Attempted to create Object out of bounds. Object %s:\n" +
                        "Top left: (%f, %f)\n" +
                        "Top right: (%f, %f)\n" +
                        "Bottom left: (%f, %f)\n" +
                        "Bottom right: (%f, %f) \n" +
                        "Screen height: %d\n" +
                        "Screen width: %d",
                name, physicsObject.getStartX(), physicsObject.getTopY(), physicsObject.getEndX(), physicsObject.getTopY(),
                physicsObject.getStartX(), physicsObject.getBottomY(), physicsObject.getEndX(), physicsObject.getBottomY() , MAX_X, MAX_Y);
    }

    @NonNull
    @Override
    public String toString(){
        return String.format(Locale.getDefault(), "PhysicsObject %s:{\n" +
                "   x: %f\n" +
                "   y: %f\n" +
                "   width: %d\n" +
                "   height: %d\n" +
                "   material: %s\n" +
                "}",
                this.name, this.centerX, this.centerY, this.width, this.height, this.material.getName()
               );
    }


    /**
     * Derives the area of a PhysicsObject.
     *
     * @param height the height of the PhysicsObject
     * @param width the width of the PhysicsObject.
     * @return The area of the PhysicsObject.
     */
    private int area(int height, int width) {
        return height*width;
    }


    //getters and setters:


    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX){
        this.startX = startX;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getTopY() {
        return topY;
    }

    public void setTopY(double topY) {
        this.topY = topY;
    }

    public double getBottomY() {
        return bottomY;
    }

    public void setBottomY(double bottomY) {
        this.bottomY = bottomY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getxAcceleration() {
        return xAcceleration;
    }

    public void setxAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public double getyAcceleration() {
        return yAcceleration;
    }

    public void setyAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }
}
