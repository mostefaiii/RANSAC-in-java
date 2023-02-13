/*
Name: Meriem Mostefai
Student number: 300255443
*/


public class Point3D {
    private double x;
    private double y;
    private double z;

    /**
    * constructor for ease of use
    */
    public Point3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
    * getters
    */
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }

    /**
    * tostring method for testing convenience
    */
    public String toString(){ //TEST
        return "(" +  x + ", " + y + ", " + z + ")";
    }
}
