import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class LRNetwork {

	public static long GCD(long a, long b) {
		if(b == 0)
			return a;
		return GCD(b, a%b);
	}
	
	
	
	public static void main(String[] args) throws IOException {
		HashMap<String, GrNode> nodeMap = new HashMap<String, GrNode>();
		File file = new File("input2.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String sequence = br.readLine().trim();
//		System.out.printf("Sequence is %s%n", sequence);
		br.readLine();		//pass the empty line between inputs
		
		LinkedList<String> startingPositions = new LinkedList<String>();
		
		for(String line = br.readLine(); line != null; line = br.readLine()) {
			String graphPos = line.substring(0, 3);
			if(graphPos.charAt(2) == 'A')
				startingPositions.add(graphPos);
			String leftChild = line.substring(line.indexOf('(')+1, line.indexOf(','));
			String rightChild = line.substring(line.indexOf(',')+2, line.indexOf(')'));
			nodeMap.put(graphPos, new GrNode(leftChild, rightChild));
//			System.out.printf("Position is \"%s\", left child is \"%s\", right child is \"%s\"%n", graphPos, leftChild, rightChild);
		}
		String pos = "AAA";
		long runningTotal = 0;
		int inputIndex = 0;
		
		LinkedList<Long> outputTimes = new LinkedList<Long>();
		
		for(String p : startingPositions) {
			pos = new String(p);
			inputIndex = 0;
			runningTotal = 0;
			while(pos.charAt(2) != 'Z') {
				GrNode relevantNode = nodeMap.get(pos);
				switch(sequence.charAt(inputIndex)) {
				case 'R':
					pos = relevantNode.right;
					break;
				case 'L':
					pos = relevantNode.left;
					break;
				default:
					System.out.println("ERROR: unexpected character in input sequence");
					break;
				}
				
				inputIndex++;
				inputIndex %= sequence.length();
				runningTotal++;
			}
			outputTimes.add(runningTotal);
		}
		
		long lcm = outputTimes.get(0);
		long gcd = lcm;
		for(int i = 1; i < outputTimes.size(); i++) {
			gcd = GCD(outputTimes.get(i), lcm);
			lcm = (lcm*outputTimes.get(i))/gcd;
		}
		
		System.out.printf("Result is %d", lcm);
		
		br.close();
	}
}
