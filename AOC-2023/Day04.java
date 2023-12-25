import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Cards {
	
	HashSet<Integer> lineValues;
	
	public Cards() {
		lineValues = new HashSet<Integer>();
	}
	
	public int getLineValue(String currentLine) {
		lineValues.clear();
		int currentValue = 0;
		String winningValues = currentLine.substring(currentLine.lastIndexOf(':')+1, currentLine.lastIndexOf('|'));
		String hasValues = currentLine.substring(currentLine.lastIndexOf('|')+1, currentLine.length());
		
		String[] winningValueStrings = winningValues.trim().split("\\s+");
		String[] hasValuesStrings = hasValues.trim().split("\\s+");
		for(String cur : winningValueStrings) {
			lineValues.add(Integer.parseInt(cur));
		}
		for(String s : hasValuesStrings) {
			if(lineValues.contains(Integer.parseInt(s))) {
				currentValue++;
			}
		}
		return currentValue;
		
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("input2.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		Cards card = new Cards();
        int runningTotal = 0;
        HashMap<Integer, Integer> duplicates = new HashMap<Integer, Integer>();
        int currentGame = 1;
        duplicates.put(1, 1);
		for(String line = br.readLine(); line != null; line = br.readLine()) {
			if(duplicates.get(currentGame) == null)
				duplicates.put(currentGame, 1);
		//	else if(currentGame != 1)
		//		duplicates.put(currentGame, duplicates.get(currentGame)+1);
			int res = card.getLineValue(line);
		//	runningTotal += res;
			Integer instOfCurr = duplicates.get(currentGame);
			if(instOfCurr == null) {
				duplicates.put(instOfCurr, 1);
				instOfCurr = 1;
			}
			for(int i = currentGame+1; i <= currentGame+res; i++) {
				Integer cardCount = duplicates.get(i);
				if(cardCount == null)
					cardCount = 1;
				int toStore = (cardCount + instOfCurr);
				duplicates.put(i, toStore);
			}
			runningTotal += duplicates.get(currentGame);
			
			currentGame++;
		}
		
		System.out.printf("Total is %d", runningTotal);
		
		br.close();
	}

}
