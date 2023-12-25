import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Pattern {
	
	public static int checkCurrentPattern(int[] pattern) {
		if(pattern.length == 2)
			return pattern[1] - pattern[0];
		if(pattern.length < 2)
			return 0;
		int indicator = 0;
		int[] additionalPattern = new int[pattern.length-1];
		for(int i = 0; i < pattern.length-1; i++) {
			additionalPattern[i] = pattern[i+1] - pattern[i];
			indicator |= additionalPattern[i];
		}
		if(indicator == 0)
			return pattern[pattern.length-1];
		return pattern[pattern.length-1] + checkCurrentPattern(additionalPattern);		
	}
	
	public static int[] lineToIntArray(String line) {
		String[] splitVals = line.split("\\s+");
		int[] output = new int[splitVals.length];
		for(int i = 0; i < splitVals.length; i++) {
			output[i] = Integer.parseInt(splitVals[i]);
		}
		return output;
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("input2.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		int runningTotal = 0;
		
		for(String line = br.readLine(); line != null; line = br.readLine()) {
			int[] lineValues = lineToIntArray(line);
			for(int i = 0; i < lineValues.length/2; i++) {
				int temp = lineValues[i];
				lineValues[i] = lineValues[lineValues.length-1-i];
				lineValues[lineValues.length-1-i] = temp;
			}
			runningTotal += checkCurrentPattern(lineValues);
			System.out.println("New running total is " + runningTotal);
		}
		
		System.out.printf("Total is %d", runningTotal);
		
		br.close();
	}

}
