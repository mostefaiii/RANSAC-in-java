/*
Name: Meriem Mostefai
Student number: 300255443
*/

import java.util.ArrayList;
import java.util.ListIterator;
import java.io.*;

public class PointCloud {

	private ArrayList<Point3D> list;


	/**
	 * constructor that reads points from .xyz file
	 * and stores them as Point3D objects in an arraylist
	 */
	public PointCloud(String filename){
		list = new ArrayList<Point3D>();
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filename));
            
            int start = 0;

            while((line = reader.readLine()) != null){

                String[] row = line.split("	");

                if(start>=1){ //skipping line 0 because it contains "x,y,z"
                list.add(new Point3D(Double.parseDouble(row[0]),
                Double.parseDouble(row[1]),
                Double.parseDouble(row[2]))); //add a new point to the list using our convenient constructor
                }
                start++;
            }//end of while block
        }
        catch(Exception e) {

            e.printStackTrace();
        }
        finally{
            try {
                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
        	}
        }//end of finally block
    }//end of constructor


    /**
     * constructs an empty point cloud
     */
	public PointCloud(){
		list = new ArrayList<Point3D>();
	}


	/**
	 * adds a point to the point cloud
	 */
	public void addPoint(Point3D pt){
		this.list.add(pt);
	}

	/**
	 * returns a random point from the cloud
	 */
	public Point3D getPoint(){
		return this.list.get((int)(Math.random()*this.list.size()));

	}


	/**
	 * saves the point cloud as a .xyz file
     */
	public void save(String filename){
		//TODO: test this

        String trimname = filename.substring(0,filename.length() - 4);
        //^just cutting this down to not include the ".xyz"
        try{
            FileWriter writer = new FileWriter( trimname + ".xyz"); //creating the output file including its name format

            BufferedWriter bwr = new BufferedWriter(writer);

            bwr.write("x\ty\tz\n"); //add the first line

            for(Point3D P : this.list){
                bwr.write(Double.toString(P.getX()));
                bwr.write("\t");
                bwr.write(Double.toString(P.getY()));
                bwr.write("\t");
                bwr.write(Double.toString(P.getZ()));
                bwr.write("\n");
            }

            bwr.close();

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }//end of save method

	
	/**
	 * returns an iterator on our list of points
	 */ 
	public ListIterator<Point3D> iterator(){
		return this.list.listIterator();
	}

	/**
	 * returns string formatting of the cloud (for ease of debugging)
	 */
	public String toString(){
		ListIterator<Point3D> itr1 = this.iterator();
		String dastring = "";
		while(itr1.hasNext()){
			Point3D point = itr1.next();
			dastring+= point.toString() + "\n";
		}
		return dastring;
	}//end of toString


}//end of class