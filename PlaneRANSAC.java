/*
Name: Meriem Mostefai
Student number: 300255443
*/

import java.lang.Math.*;
import java.util.*;

public class PlaneRANSAC {

	private double eps;
	private PointCloud pc;
	private PointCloud empty;

	private static int iteration; //used for naming the output files 
	//basically keeps track of how many times run() has been called 

	public PlaneRANSAC(PointCloud pc){
		this.pc = pc;
		this.empty = new PointCloud();
		this.iteration = 0;
	}

	public void setEps(double eps){
		this.eps = eps;
	}

	public double getEps(){
		return eps;
	}


	/**
	 * returns estimated number of iterations
	 */
	public int getNumberOfIterations(double confidence, 
		double percentageOfPointsOnPlane){
		//formula where k is numb of iterations
		// k = log(1 - confidence) / log(1 - percentage^3)

		double percentagecubed = Math.pow(percentageOfPointsOnPlane, 3);
		int numb = (int)( (Math.log(1-confidence)) / (Math.log(1 - percentagecubed)) );
		return numb;
	}


	/**
	 * executes the ransac algorithm and saves two files:
	 * one containing the original point cloud excluding the points from the dominant plane
	 * and the other containing the points on the plane itself
	 */
	public void run(int numberOfIterations, String filename){
		/*
		1. Initially, no dominant plane has been found, and the best support is set to 0 (see Step 5.)
		2. Randomly draw 3 points from the point cloud.
		3. Compute the plane equation from these 3 points.
		4. Count the number of points that are at a distance less than eps (ε) from that plane. This number
		is the support for the current plane.
		5. If the current support is higher than the best support value, then the current plane becomes the
		current dominant plane and its support is the new best support.
		6. Repeat 2 to 5 until we are confident to have found the dominant plane.
		7. Remove the points that belong to the dominant plane from the point cloud. Save these points in a
		new file. */

		iteration++;

		//step 1
		int bestsup = 0;
		Plane3D domplane = new Plane3D(0,0,0,0);
		this.empty = new PointCloud();

		//step 6, putting this in a loop
		for(int i = 0; i<numberOfIterations; i++){

			//step 2
			Point3D p1 = pc.getPoint();
			Point3D p2 = pc.getPoint();
			Point3D p3 = pc.getPoint();

			//step 3
			Plane3D currentplane = new Plane3D(p1,p2,p3);

			//step 4
			ListIterator<Point3D> itr = pc.iterator();
			int currsup = 0;

			while(itr.hasNext()){
				Point3D crntpt = itr.next();
				double distance = currentplane.getDistance(crntpt);
				if(distance<this.eps){
					currsup++;
				}
			}

			//step 5
			if(currsup>bestsup){
				bestsup = currsup;
				domplane = currentplane;
			}
		}//end of step 6 loop

		ListIterator<Point3D> itr2 = pc.iterator();
		
		//step 7
		while(itr2.hasNext()){

			Point3D crntpt = itr2.next();
			double distance = domplane.getDistance(crntpt);
			if(distance<this.eps){
				this.empty.addPoint(crntpt);
				itr2.remove();
			}
		}
		//step 7 parte dos
		String fn = filename.substring(0,filename.length() - 4);
		String finalfilename = fn + "_p0.xyz";
		this.pc.save(finalfilename);

		//save to a file with only that plane's points
		String finalfilename2 = fn + "_p" + iteration + ".xyz";
		this.empty.save(finalfilename2);

	}//end of run method

	public static void main(String[] args) {
		//0 - filename
		//1 - eps
		//2 - confidence
		//3 - percentage

		PointCloud maincloud = new PointCloud(args[0]);
		PlaneRANSAC ransacky = new PlaneRANSAC(maincloud);
		ransacky.setEps(Double.parseDouble(args[1]));
		
		for(int i = 0; i<3; i++){
			ransacky.run(ransacky.getNumberOfIterations(Double.parseDouble(args[2]),Double.parseDouble(args[3])), args[0]);
		}
	}

}//end of class