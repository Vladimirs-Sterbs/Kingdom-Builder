package application;
import java.util.*;


public class Deck {
	
	private ArrayList<Integer> deck;
	private ArrayList<Integer> discardPile;
	private ArrayList<Integer> objCards;
	
	public Deck(){
		deck = new ArrayList<>();
		discardPile = new ArrayList<>();
		fillBiomes();
	}
	public void fillBiomes() {
		//change to 2 for 10 card deck; should be 5
		for(int i = 0;i<5;i++){
			for(int n = 0;n<5;n++)
				deck.add(i);
		}
		Collections.shuffle(deck);
	}
	public int drawNextTerrain(int current){
		discardPile.add(current);
		if(deck.size()>0){
			int i = deck.remove(0);
			return i;
		}
		refill();	
		int i = deck.remove(0);
		return i;
	}
	public int drawNextTerrain(){
		if(deck.size()>0){
			int i = deck.remove(0);
			return i;
		}
		refill();	
		int i = deck.remove(0);
		return i;
	}
	public void refill(){
		for(int i: discardPile)
			deck.add(i);
		discardPile = new ArrayList<>();
		Collections.shuffle(deck);
	}
	
	public void addObjCards(int x) {
		
	}
	
	public void discard() {
		
	}
	
	public ArrayList<Integer> getDeck() {
		return deck;
	}
	
	public int getSize() {
		return deck.size();
	}
	
	public int getObjSize() {
		return objCards.size();
	}
	
	public int getDisSize() {
		return discardPile.size();
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}

	
	public void printDeck() {
		for (int x:deck) {
			System.out.print(x + " ");
		}
	}
	
	public boolean isEmpty() {
		return deck.size() == 0;
	}
	
	public ArrayList<Integer> getObj() {
		return objCards;
	}
	
	
	public ArrayList<Integer> getDiscard() {
		return discardPile;
	}
	
}
