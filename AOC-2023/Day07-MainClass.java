import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class CamelCards {

	
	public static void main(String[] args) throws IOException {
		File file = new File("input2.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		long runningTotal = 0;
		LinkedList<Hand> hands = new LinkedList<Hand>();
		for(String line = br.readLine(); line != null; line = br.readLine()) {
			String[] lineParts = line.split("\\s+");
			hands.add(new Hand(lineParts[0], Integer.parseInt(lineParts[1])));
		}
		hands.sort((a, b) -> a.compareTo(b));
		for(int i = 0; i < hands.size(); i++) {
			Hand currentHand = hands.get(i);
			System.out.printf("Hand is %s, type is %d%n", currentHand.getHand(), currentHand.type);
			runningTotal += currentHand.getBid()*(i+1);
		}
		System.out.printf("Total is %d", runningTotal);
		
		br.close();
	}
}
