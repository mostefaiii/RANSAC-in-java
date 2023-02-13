/*
Name: Meriem Mostefai
Student number: 300255443
*/

import java.lang.Math.*;

public class Plane3D {

	/**
	 * helper vector class 
	 */
	private static class Vector {

		public double i, j, k;
		
		public Vector(double i, double j, double k){
			this.i = i;
			this.j = j;
			this.k = k;
		}
	}

	private double a, b, c, d;

	/**
	 * constructor that sets the parameters 
	 * from three points on the plane
	 */
	public Plane3D(Point3D p1, Point3D p2, Point3D p3){
		//construct two vectors from the 3 points
		Vector ab = new Vector((p2.getX() - p1.getX()),
				(p2.getY() - p1.getY()), (p2.getZ() - p1.getZ()));
		Vector bc = new Vector((p3.getX() - p2.getX()),
				(p3.getY() - p2.getY()), (p3.getZ() - p2.getZ()));

		//cross product of the two vectors from our points
		Vector normal = getCrossed(ab, bc);

		//plane equation: Ni(x-a1) + Nj(y-a2) + Nk(z - a3) = 0
		//so Nix + Njy + Nkz = (Nia1 + Nja2 + Nka3)

		this.a = normal.i;
		this.b = normal.j;
		this.c = normal.k;
		this.d = (normal.i*p1.getX() + normal.j*p1.getY() + normal.k*p1.getZ());
	}

	/**
	 * constructor that takes variables for plane equation
	 */
	public Plane3D(double a, double b, double c, double d){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

	}

	/**
	 * returns the distance from the plane
	 * to a point
	 */
	public double getDistance(Point3D pt){
		/*the formula is the following: a*x + b*y + c*z + d
			/ sqrt(a^2 + b^2 + c^2) 
		*/

		return Math.abs((this.a * pt.getX() + this.b * pt.getY() + this.c * pt.getZ() + this.d))
		/(Math.sqrt((this.a*this.a)+(this.b*this.b)+(this.c*this.c)));
	}

	/**
	 * private method for convenience 
	 * calculates and returns the cross product of two vectors
	 */
	private Vector getCrossed(Vector a, Vector b){
		return new Vector(((a.j * b.k) - (a.k * b.j)),
				((a.k * b.i) - (a.i * b.k)),
				((a.i * b.j) - (a.j * b.i)));
		}

	/**
	 * returns a string representation of the plane
	 * in the form of the plane formula
	 */
	public String toString(){
		return this.a + "x + " + this.b + "y + " + this.c + "z = " + this.d;
	}

}