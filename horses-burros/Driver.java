package herd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalTime;


public class Driver {
	public static void main(String[] args) {
		
		final String FILE_NAME = "src/herd/serialize.ser";
		
		//-------------------------------GET USER INPUT-----------------------------------------------------
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the file containing data...");
		String filename = sc.nextLine();			// give it the full directory path (resources/herdManagement.csv)
		System.out.println("How many header rows does the file contain?");
		int headerRows = sc.nextInt();
		
		
		
		
		
		
		//--------------------------------------------------------------------------------------------------
		//----------------Initialize DataSet with no parameters. Populate with loadStatistics---------------
		//----------------------------------------DISPLAY STATE DATA----------------------------------------
		
		DataSet dataset = new DataSet();
		try {
			loadStatistics(dataset, filename, headerRows);
		}
		catch (StatisticDataNotFoundException e) {
			System.out.println(e.getMessage());
		}
				
		// display statistics for all states
		displayStatistics(dataset);
		
		
		
		
		
		
		//--------------------------------------------------------------------------------------------------
		//--------------------Get Random State, serialize, deserialize--------------------------------------
		//--------------------------------------------------------------------------------------------------
		
		
		Random random = new Random();
		ArrayList<Statistic> statistics = dataset.getStats();
		if (statistics.size() > 0) {
			int randomState = random.nextInt(statistics.size());
			Statistic state = statistics.get(randomState);
			
			
			System.out.println("--------------------Serialize--------------------");
			System.out.printf("Serialized data for state '%s' is stored in %s\n", ((StateStatistic) state).getState(), FILE_NAME);
			state.serialize(FILE_NAME);
			
			
			System.out.println("Serialized Version UID: " + Statistic.getID());
			
			System.out.println("\n--------------------Deserialize------------------");
			Statistic der = Statistic.deserialize(FILE_NAME);
			System.out.println(der);
		}
		sc.close();
	}
	
	
	
	
	
	
	//--------------------------------------------------------------------------------------------------
	//----------------------------------LOAD DATASET ARRAY OF STATISTICS--------------------------------
	//--------------------------------------------------------------------------------------------------
	private static void loadStatistics(DataSet dataset, String filename, int headerRows) throws StatisticDataNotFoundException {
		
		// try with resources to ensure automatic closing of the buffered reader object
		try (BufferedReader br = new BufferedReader(new FileReader(filename))){
			
			// skip the header rows by reading them
			for (int i = 1; i <= headerRows; i++) {
				br.readLine();
			}
								
			String thisLine;
			
			while((thisLine = br.readLine()) != null) {
				System.out.println(thisLine);
				
				// Split each line using comma as a delimiter
				String[] fields = thisLine.split(",");
				
				// Take each value and assign it to the respective variables
				State state = State.valueOf(fields[0]);
				long herdAreaAcresBLM = Long.parseLong(fields[1]);
				long herdAreaAcresOther = Long.parseLong(fields[2]);
				long herdManagementAreaAcresBLM = Long.parseLong(fields[3]);
				long herdManagementAreaAcresOther = Long.parseLong(fields[4]);
				long numHorses = Long.parseLong(fields[5]);
				long numBurros = Long.parseLong(fields[6]);
				
				// populate the data set with the above values
				dataset.addStatistic(new StateStatistic(state, herdAreaAcresBLM, herdAreaAcresOther,
						herdManagementAreaAcresBLM, herdManagementAreaAcresOther, numHorses, numBurros));		
			}
		}
		catch (IOException e) {
			throw new StatisticDataNotFoundException("File not found: " + filename + "\nAttempted access at: " + LocalTime.now());
		}
	}
	
	
	
	
	
	//--------------------------------------------------------------------------------------------------
	//-------------------------------------DISPLAY STATISTICS FOR EACH STATE----------------------------
	//--------------------------------------------------------------------------------------------------
	private static void displayStatistics(DataSet dataset) {
		int horses = 0;
		int burros = 0;
		
		for (Statistic statistic : dataset.getStats()) {
			horses += ((StateStatistic) statistic).getNumHorses();
			burros += ((StateStatistic) statistic).getNumBurros();
						
			System.out.println(statistic);
		}
		
		if (dataset.getStats().size() > 0) {
			System.out.println("\nTotal number of horses: " + horses + "\nTotal number of burros: " + burros + "\n");
		}
	
	}
	

}
