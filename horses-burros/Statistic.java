package herd;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A superclass to allow for future expansion in which additional forms
 * of statistics may be introduced.
 * 
 * @author Izhar Ali
 *
 */
public class Statistic implements Serializable {

	private static final long serialVersionUID = 4360250497916681230L;
	

	/**
	 * This method serializes a given state data.
	 * @param filename the name of the file to store the serialized data in.
	 * @return a boolean to indicate if the serialization was successful.
	 */
	public boolean serialize(String filename) {	
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(this);
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * This method deserializes the previously serialized data.
	 * @param filename the name of the file that holds the serialized data.
	 * @return the deserialized version of the serialized data.
	 */
	public static Statistic deserialize(String filename) {
		try (ObjectInputStream in  = new ObjectInputStream(new FileInputStream(filename))) {
			Statistic statistic = (Statistic) in.readObject();
			return statistic;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException e) {
			System.out.println("Statistic class not found");
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Get the Serial Version ID for this version of the program
	 * @return serialVersionUID
	 */
	public static long getID() {
		return serialVersionUID;
	}
	
}
