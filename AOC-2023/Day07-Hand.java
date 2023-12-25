import java.util.HashMap;
import java.util.HashSet;

public class Hand implements Comparable<Hand>{
	
	final int FIVE_OF_A_KIND = 6;
	final int FOUR_OF_A_KIND = 5;
	final int FULL_HOUSE = 4;
	final int THREE_OF_A_KIND = 3;
	final int TWO_PAIR = 2;
	final int ONE_PAIR = 1;
	final int HIGH_CARD = 0;
	
	int type;
	String hand;
	int bid;
	
	public Hand(String hand, int bid) {
		this.type = findHandType(hand);
		this.hand = hand;
		this.bid = bid;
		
	}
	
	public int getBid() {
		return bid;
	}
	
	public int findHandType(String hand) {
		HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
		for(char c : hand.toCharArray()) {
			Integer currentFreq = freq.get(c);
			if(currentFreq == null)
				freq.put(c, 1);
			else
				freq.put(c, currentFreq+1);
		}
		int uniqueElements = freq.size();
		
		Integer numberOfJs = freq.get('J');
		if(numberOfJs == null)
			numberOfJs = 0;
		freq.remove('J');
		HashSet<Integer> freqTotals = new HashSet<Integer>(freq.values());
		freq.put('J', numberOfJs);
		
		
		int highestAmount = 0;
		for(int i : freqTotals)
			highestAmount = Math.max(highestAmount, i);
		if(highestAmount + numberOfJs >= 5)
			return FIVE_OF_A_KIND;
		if(highestAmount + numberOfJs >= 4)
			return FOUR_OF_A_KIND;
		if(highestAmount >= 3 && uniqueElements == 2 || (highestAmount + numberOfJs >= 3 && uniqueElements ==3 && numberOfJs > 0))
			return FULL_HOUSE;
		if(highestAmount + numberOfJs >= 3)
			return THREE_OF_A_KIND;
		if(highestAmount == 2 && uniqueElements <= 3)
			return numberOfJs>=1 ? FULL_HOUSE : TWO_PAIR;
		if(highestAmount + numberOfJs >= 2)
			return ONE_PAIR;
		return HIGH_CARD;
	}

	@Override
	public int compareTo(Hand o) {
		int initial = this.type - o.type;
		if(initial != 0)
			return initial;
		HashMap<Character, Integer> conversions = new HashMap<Character, Integer>();
		conversions.put('A', 20);
		conversions.put('K', 19);
		conversions.put('Q', 18);
		conversions.put('J', 2);
		conversions.put('T', 16);
		conversions.put('9', 15);
		conversions.put('8', 14);
		conversions.put('7', 13);
		conversions.put('6', 12);
		conversions.put('5', 11);
		conversions.put('4', 10);
		conversions.put('3', 9);
		conversions.put('2', 8);
		for(int i = 0; i < hand.length(); i++) {
			char thisCard = this.hand.charAt(i);
			char otherCard = o.hand.charAt(i);
			int comparison = conversions.get(thisCard) - conversions.get(otherCard);
			if(comparison != 0)
				return comparison;
		}
		return 0;
	}
	
	public String getHand() {
		return hand;
	}

}
