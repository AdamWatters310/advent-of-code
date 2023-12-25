import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Engine {
	HashMap<String, Integer> gears = new HashMap<String, Integer>();
	char[][] board;
	
	public Engine() {
		
	}
	
	boolean isNumber(char toCheck) {
		return toCheck >= '0' && toCheck <= '9';
	}
	
	boolean checkCell(int row, int col) {
		if(row >= board.length || row < 0)
			return false;
		if(col >= board[0].length || col <= 0)
			return false;
		return (!isNumber(board[row][col])) && (board[row][col] != '.');
	}
		
	boolean checkAdjacency(int row, int col) {
		int currentPos = col;
		if(checkCell(row-1, col-1)) return true;
		if(checkCell(row, col-1)) return true;
		if(checkCell(row+1, col-1)) return true;
		while(currentPos < board[row].length && isNumber(board[row][currentPos])) {
			if(checkCell(row+1, currentPos)) return true;
			if(checkCell(row-1, currentPos)) return true;
			currentPos++;
		}
		if(checkCell(row-1, currentPos)) return true;
		if(checkCell(row, currentPos)) return true;
		if(checkCell(row+1, currentPos)) return true;
		
		return false;
		
	}
	
	String findGear(int row, int col) {
		if(row >= board.length || row < 0 || col >= board[0].length || col < 0)
			return "N";
		int currentPos = col;
		if(row>0 && col>0 && board[row-1][col-1] == '*') return (row-1)+"-"+(col-1);
		if(col>0 && board[row][col-1] == '*') return (row)+"-"+(col-1);
		if(col>0 && row < board.length-1 && board[row+1][col-1] == '*') return (row+1)+"-"+(col-1);
		while(currentPos < board[row].length && isNumber(board[row][currentPos])) {
			if(row<board.length-1 && board[row+1][currentPos] == '*') return (row+1)+"-"+(currentPos);
			if(row>0 && checkCell(row-1, currentPos)) return (row-1)+"-"+(currentPos);
			currentPos++;
		}
		if(currentPos>=board[0].length)
			return "N";
		if(row>0 && board[row-1][currentPos] == '*') return (row-1)+"-"+(currentPos);
		if(board[row][currentPos] == '*') return (row)+"-"+(currentPos);
		if(row<board.length-1 && board[row+1][currentPos] == '*') return (row+1)+"-"+(currentPos);
		
		return "N";
		
	}
	
	public int checkBoard() {
		boolean lookingAtNumber = false;
		boolean includeNumber = false;
		int currentNumber = 0;
		int runningTotal = 0;
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(isNumber(board[row][col])) {
					if(!lookingAtNumber) {
						if(checkAdjacency(row, col)) {
							//number at position row, col should be included in result
							includeNumber = true;
						}
					}
					if(includeNumber) {
						currentNumber *= 10;
						currentNumber += board[row][col]-'0';
					}
					lookingAtNumber = true;
				} else {
					if(includeNumber)
						runningTotal += currentNumber;
					lookingAtNumber = includeNumber = false;
					currentNumber = 0;
				}
			}
			if(includeNumber)
				runningTotal += currentNumber;
			lookingAtNumber = includeNumber = false;
		}
		return runningTotal;
	}
	
	public int checkBoardPart2() {
		boolean lookingAtNumber = false;
		boolean includeNumber = false;
		int firstNumber = 0;
		int currentNumber = 0;
		int runningTotal = 0;
		String location = "";
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(isNumber(board[row][col])) {
					if(!lookingAtNumber) {
						location = findGear(row, col);
						if(location.compareTo("N") != 0) {
							includeNumber = true;
							if(gears.get(location) != null) {
								firstNumber = gears.get(location);
								
							} 
						}
					}
					if(includeNumber) {
						currentNumber *= 10;
						currentNumber += board[row][col]-'0';
					}
					lookingAtNumber = true;
				} else {
					if(includeNumber) {
						if(firstNumber == 0) {
							gears.put(location, currentNumber);
						} else {
							runningTotal += (firstNumber * currentNumber);
							firstNumber = 0;
						}
					}
					lookingAtNumber = includeNumber = false;
					currentNumber = firstNumber = 0;
				}
			}
			if(includeNumber) {
				if(firstNumber == 0) {
					gears.put(location, currentNumber);
				} else {
					runningTotal += (firstNumber * currentNumber);
					firstNumber = 0;
				}
			}
			lookingAtNumber = includeNumber = false;
		}
		return runningTotal;
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("input.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		Engine engine = new Engine();
		engine.board = new char[140][140];
		int currentLine = 0;
		for(String line = br.readLine(); line != null; line = br.readLine()) {
			engine.board[currentLine] = line.toCharArray();
			currentLine++;
		}
		
		System.out.printf("Total is %d", engine.checkBoardPart2());
		
		br.close();
	}
}
