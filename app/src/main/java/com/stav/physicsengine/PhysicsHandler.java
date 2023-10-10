package com.stav.physicsengine;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

/**
 * The class that's responsible for all physics calculations and notifies the related {@link Activity} when to update the views.
 */
public class PhysicsHandler {


    //constants:


    /**
     * The gravitational constant.
     */
    public final double g = 5;


    //non final fields:


    /**
     * The list of all {@link PhysicsObject}s in the relevant {@link Activity}.
     */
    public ArrayList<PhysicsObject> physicsObjects;


    //constructors:

    /**
     * Creates a {@link PhysicsHandler} with no attached {@link PhysicsObject}s.
     */
    public PhysicsHandler(){
        physicsObjects = new ArrayList<>();
    }

    /**
     * Creates a {@link PhysicsHandler} and attaches the specified {@link PhysicsObject}s.
     */
    public PhysicsHandler(ArrayList<PhysicsObject> physicsObjects){
        this.physicsObjects = physicsObjects;
    }


    //public methods:


//    public boolean advance() {
//
//        for (PhysicsObject pObject : physicsObjects) {
//            try{
//                calculate(pObject);
//            }
//            catch (Exception e){
//                Log.e("PHYSICS_HANDLER", "tf just happened.\nPhysicsObject " + pObject.name + " ran into an unexpected error.\nit is skipped for now.");
//            }
//        }
//
//
//
//        return true;
//    }


    //private methods:


/*    private void calculate(PhysicsObject pObject) throws Exception{

        //current frame adjustments:
        try {

        }
        catch (ObjectOutOfBoundsException e) {
            pObject.setBottomY(PhysicsObject.MAX_Y);
        }

    }*/




}
