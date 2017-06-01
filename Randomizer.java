import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RandomizePicks {
	private static final String TEAMOWNERS = "owners.txt";

	// Reads input file
	public static List<String> inputToArrayList(String filename) {
		List<String> aggList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))){
			String currLine;

			while ((currLine = br.readLine()) != null) {     
				aggList.add(currLine);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();	        
		} catch (IOException e) {
			e.printStackTrace();
		}

		return aggList;
	}

	// Randomly assigns each owner to a list of competitors
	public static HashMap<String, List<String>> randomMatch(List<String> ownersList, List<String> competitorsList) {
		int numOfOwners =  ownersList.size();
		int numOfCompetitors = competitorsList.size();
		int partitionSize = numOfCompetitors / numOfOwners;
		int remainderSize = numOfCompetitors % numOfOwners;
		HashMap<String, List<String>> randomizedMap = new HashMap<String, List<String>>();

		// Shuffle the lists
		Collections.shuffle(ownersList);
		Collections.shuffle(competitorsList);

		for (int i = 0, lowerBound = 0; i < numOfOwners; i++, lowerBound += partitionSize) {
			List<String> partition = new ArrayList<>();
			
			// Add group of competitors
			for (int j = lowerBound; j < lowerBound + partitionSize; j++) {
				partition.add(competitorsList.get(j));
			}

			// Add remainder competitors for lucky team owners
			if (i < remainderSize) {
				partition.add(competitorsList.get(numOfCompetitors - 1 - i));
			}

			randomizedMap.put(ownersList.get(i), partition);
		}

		return randomizedMap;
	}

	public static void main (String [] args) {
		List<String> inputOwners = inputToArrayList(TEAMOWNERS);
		List<String> inputCompetitors = inputToArrayList(args[0]);

		HashMap<String, List<String>> results = randomMatch(inputOwners, inputCompetitors);

		for (String owner : results.keySet()) {
			System.out.println(owner + " = " + results.get(owner));
		}
	}
}