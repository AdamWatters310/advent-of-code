import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Stack;

class Range {
	long destStart, sourceStart, count;
	public Range(long destStart, long sourceStart, long count) {
		this.destStart = destStart;
		this.sourceStart = sourceStart;
		this.count = count;
	}
}

class Interval {
	long start, end;	// range is [start, end)
	Interval(long start, long end) {
		this.start = start;
		this.end = end;
	}
}

public class Almanac {
	final String inputFile = "input2.txt";
	
	public Almanac() {
		
	}	
	
	public Range lineToRange(String line) {
		String[] lineInputs = line.split("\\s+");
		return new Range(Long.parseLong(lineInputs[0]), Long.parseLong(lineInputs[1]), Long.parseLong(lineInputs[2]));
	}
	
	public static void main(String[] args) throws IOException {
		Almanac almanac = new Almanac();		
		String fileContent = Files.readString(Path.of(almanac.inputFile));
		
		String seeds = fileContent.substring(fileContent.indexOf(':')+1, fileContent.indexOf('\n')).trim();
		
		// parse the seed values
		String[] seedsSplit = seeds.split("\\s+");
		Stack<Interval> seedIntervals = new Stack<Interval>();
		for(int i = 0; i < seedsSplit.length; i+=2) {
			long start = Long.parseLong(seedsSplit[i]);
			long end = start + Long.parseLong(seedsSplit[i+1]);
			seedIntervals.push(new Interval(start, end));
		}
		
		String[] blocks = fileContent.substring(fileContent.indexOf('\n')+1).trim().split("\r\n\r\n");
		for(String b : blocks) 
			b = b.trim();
		
		for(String block : blocks) {
			LinkedList<Range> ranges = new LinkedList<Range>();
			String[] blockLines = block.split("\r\n");
			for(String line : blockLines) {
				if(line.indexOf(':') == -1) {
					// this is not the first (label) line
					ranges.add(almanac.lineToRange(line));
				}
			}
			//now we need to re-map seeds
			Stack<Interval> newList = new Stack<Interval>();
			while(seedIntervals.size() > 0) {
				Interval current = seedIntervals.pop();
				boolean foundOverlap = false;
				for(Range currentRange : ranges) {
					long overlapStart = Math.max(currentRange.sourceStart, current.start);
					long overlapEnd = Math.min(currentRange.sourceStart+currentRange.count, current.end);
					if(overlapStart < overlapEnd) {
						//the ranges do actually overlap
						foundOverlap = true;
						newList.push(new Interval(overlapStart-currentRange.sourceStart + currentRange.destStart, 
								overlapEnd - currentRange.sourceStart + currentRange.destStart));
						if(overlapStart > current.start)
							seedIntervals.push(new Interval(current.start, overlapStart));
						if(overlapEnd < current.end)
							seedIntervals.push(new Interval(overlapEnd, current.end));
						break;
					}
				}
				if(!foundOverlap)
					newList.push(current);
			}
			seedIntervals = newList;
		}
		long output = Integer.MAX_VALUE;
		while(seedIntervals.size() > 0) {
			output = Math.min(output, seedIntervals.pop().start);
		}
		
		System.out.printf("Result is %d", output);
	}
}