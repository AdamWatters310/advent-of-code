import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BoatRace {
	
	public long calculateWaysToWin(long distance, long time) {
		int runningTotal = 0;
		for(int i = 0; i < time; i++) {
			if((time-i)*i > distance)
				runningTotal++;
		}
		return runningTotal;
	}
	
	public long calculateP2(long distance, long time) {
		System.out.printf("Distance is %d and time is %d%n", distance, time);
		double lowerBound = time+Math.sqrt(Math.pow(time, 2)+(4*distance))/-2.0;
		double upperBound = time-Math.sqrt(Math.pow(time, 2)+(4*distance))/-2.0;
		System.out.printf("Lower bound is %f and upper bound is %f%n", lowerBound, upperBound);
		return Math.abs((long)(Math.floor(lowerBound) - Math.floor(upperBound))); 
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("input2.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		BoatRace race = new BoatRace();
		long output = 1;
		long time = Long.parseLong(br.readLine().split(":")[1].replaceAll("\\s+", ""));
		long distance = Long.parseLong(br.readLine().split(":")[1].replaceAll("\\s+", ""));
		
		System.out.printf("Total is %d", race.calculateWaysToWin(distance, time));
		br.close();
	}

}
