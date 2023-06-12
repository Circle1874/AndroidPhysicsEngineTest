package com.stav.physicsengine;

/**
 * A material <a href="#{@link}">{@link PhysicsObject}</a>s may be made of.
 */
public class Material {

    /**
     * The total Materials created. Used to generate names for nameless materials.
     */
    public static int count = 0;

    /**
     * idk why i added this field. might get removed later.
     */
    protected int id;

    /**
     * The density of the material. I'll see if I use it.
     */
    protected double density;

    /**
     * The name of the material. Used for materials that multiple PhysicsObjects ore made of.
     */
    protected String name;


    /**
     * Constructor with everything known about the material.
     *
     * @param name The name of the material.
     * @param density The density of the material.
     */
    public Material(String name, double density) {
        this.id = count;
        count++;
        this.name = name;
        this.density = density;
    }


    /**
     * Constructor of a nameless material, given the density.
     * Generates a name with the id of the material.
     *
     * @param density the density of the material.
     */
    public Material(double density) {
        this.id = count;
        count++;
        this.density = density;
        this.name = "Material_" + id;
    }

    /**
     * Constructor used in the PhysicsObject constructor to generate a material with no given density.
     *
     * @param mass the mass of the about-to-be PhysicsObject.
     * @param area the height of the about-to-be PhysicsObject.
     * @see PhysicsObject
     */
    public Material(double mass, int area) {
        this.id = count;
        count++;
        this.density = mass /area;
        this.name = "Material_" + id;
    }


    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Material.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
