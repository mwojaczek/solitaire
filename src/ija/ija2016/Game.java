
package ija.ija2016.proj;

import ija.ija2016.*;
import ija.ija2016.homework1.cardpack.*;
import ija.ija2016.homework2.model.board.*;
import ija.ija2016.homework2.model.cards.*;


	/**
	*
	* @author xdokou12
	*/

public class Game{
    private AbstractFactorySolitaire factory;
    private String[] lastStates = new String[6];
	private CardDeck deck;
	private CardDeck[] foundations = new Balicek[4];
	private CardStack[] bases = new Wastepile[7];
	private CardStack[] stacks = new Zasobnik[7];
	private CardStack waste;
	private Card selected;	// vybraná karta
	private int a,b;		// souřadnice selectu


	/**
	* Konstruktor nové hry
	*
	*/

	public Game(){
		for (int i = 0; i<6 ; i++ ) {
			lastStates[i] = null;
		}
		a = 0;
		b = 0;
		deck = new Balicek(52);	
		waste = new Wastepile(30);
		factory = new FactoryKlondike();	
		deck = factory.createCardDeck();
        System.out.println(deck.size());

		foundations[0] = factory.createTargetPack(Card.Color.SPADES);
		foundations[1] = factory.createTargetPack(Card.Color.CLUBS);
		foundations[2] = factory.createTargetPack(Card.Color.DIAMONDS);
		foundations[3] = factory.createTargetPack(Card.Color.HEARTS);


		for(int i =0; i<7; i++){
			stacks[i] = factory.createWorkingPack();
			bases[i] = factory.createBasePack();
		}

		deck.shuffleDeck();
		initGame();
		saveUndo();
	}

	/**
	* Převede string (ve tvaru, který vrátí metoda Card.toString()) na kartu.
	* @param s
	*/


	public Card strtoCard(String s){
		if (s.isEmpty())
			return null;

		int val = 0;
		if(s.length() == 3)
			val = 10;
		else if(s.startsWith("A"))
			val = 1;
		else if(s.startsWith("J"))
			val = 11;
		else if(s.startsWith("Q"))
			val = 12;
		else if(s.startsWith("K"))
			val = 13;
		else
			val = s.charAt(0) - '0';

		Card.Color col = null;
			if(s.endsWith("C"))
		            col = Card.Color.CLUBS;
		    if(s.endsWith("S"))
		            col = Card.Color.SPADES;
		    if(s.endsWith("D"))
		            col = Card.Color.DIAMONDS;
		    if(s.endsWith("H"))
		            col = Card.Color.HEARTS;

	    return new Karta(col,val);

	}

	/**
	* Vezme string (ve tvaru, který vrací metoda Game.toString()) a podle něj nastaví stav hry na stav uložený dříve. Implementuje i operaci undo.
	* @param s
	*/

	public void loadGame(String s){

		String[] ar = s.split(":");
		String[] pom = ar[0].split(",");
		Card c;

		while(!deck.isEmpty())		// vyprázdní deck
			deck.pop();

		for(int i = 0; i < pom.length; i++){		// naplní deck
			c = strtoCard(pom[i]);
			if(c != null)
				deck.put(c, true);
		}
		
		pom = ar[1].split(",");
		while(!waste.isEmpty())			// vyprázdní waste
			waste.pop();

		for(int i = 0; i < pom.length; i++){		// naplní waste
			c = strtoCard(pom[i]);
			if(c != null){
				c.turnFaceUp();
				waste.put(c, true);
			}
		}
		for(int j = 0; j<4; j++){			//
			pom = ar[j+2].split(",");
			
			while(!foundations[j].isEmpty())
				foundations[j].pop();
			if(pom != null){
				System.out.println(pom[0]);
				for(int i = 0; i < pom.length; i++){
					c = strtoCard(pom[i]);
					if(c!=null){
						c.turnFaceUp();
						foundations[j].put(c, true);
					}
				}
			}
		}

		for(int j = 0; j<7; j++){	
			pom = ar[j+6].split(",");
			
			while(!bases[j].isEmpty())
				bases[j].pop();
			if(pom != null)
				for(int i = 0; i < pom.length; i++){
					c = strtoCard(pom[i]);
					if(c!=null)
						bases[j].put(c, true);
				}
		}

		for(int j = 0; j<7; j++){	
			pom = ar[j+13].split(",");
				while(!stacks[j].isEmpty())
					stacks[j].pop();

			if(pom != null)
				for(int i = 0; i < pom.length; i++){
					c = strtoCard(pom[i]);
					if(c!=null){
						c.turnFaceUp();
						stacks[j].put(c, true);
				}
			}
		}

		System.out.println(this.toString());
	}




	@Override
	public String toString(){


		String ret = "";
		if(!deck.isEmpty()){
			for (int i = 0; i<deck.size() - 1 ;i++ ) {
				ret += deck.get(i).toString();
				ret += ",";
			}
			ret += deck.get();
		}
		ret += ":";




		if(!waste.isEmpty()){
			for (int i = 0; i<waste.size() - 1 ;i++ ) {
				ret += waste.get(i).toString();
				ret += ",";
			}
			ret += waste.get();
		}
		ret += ":";




		for(int i = 0; i < 4; i++){
			if(!foundations[i].isEmpty()){
				for (int j = 0; j<foundations[i].size() - 1 ;j++ ) {
					ret += foundations[i].get(j).toString();
					ret += ",";
				}
				ret += foundations[i].get();
			}
			ret += ":";
			}



		for(int i = 0; i < 7; i++){
			if(!bases[i].isEmpty()){
				for (int j = 0; j<bases[i].size() - 1 ;j++ ) {
					ret += bases[i].get(j).toString();
					ret += ",";
				}
				ret += bases[i].get();
			}
				ret += ":";
			}


		for(int i = 0; i < 7; i++){
			if(!stacks[i].isEmpty()){
				for (int j = 0; j<stacks[i].size() - 1 ;j++ ) {
					ret += stacks[i].get(j).toString();
					ret += ",";
				}
				ret += stacks[i].get();
			}
				ret += ":";

			}
		return ret;


	}


	public void undo(){
		if (lastStates[1] == null)
			return;
		loadGame(lastStates[1]);
		for (int i = 0; i<5 ; i++)
			lastStates[i] = lastStates[i+1];
		lastStates[5] = null;
		
	}


	public void saveUndo(){
		if(lastStates[0]==null){
			lastStates[0] = toString();
			return;}
		if(lastStates[0].equals(toString()))
			return;
		for (int i = 0; i<5 ;i++ ) 
			lastStates[5-i] = lastStates[4-i];
		lastStates[0] = toString();
		
	}


	public void transfer(int x, int y, Card c){

		if (selected != null)		// tahám kartu u prázdného pole
			selected.turnFaceUp();


		if(a == x && b == y){			// stejné místo zmáčknutí a puštění myši
			if(a == 0 && b == 0){		// otoč kartu z balíčku
				if(deck.isEmpty())
					turnWaste();
				else{
					selected = deck.pop();
					selected.turnFaceUp();
					waste.put(selected,true);
				}
			}
			if(b == 1){					// kliknutí v poli
				if(stacks[x].isEmpty())
					if(!bases[x].isEmpty()){	// otoč kartu v poli
					selected = bases[x].pop();
					selected.turnFaceUp();
					stacks[x].put(selected,true);
				}
			}
		}

		if(selected == null)
			return;




		if(a == 1 && b == 0){			// bereme kartu z odhazovacího balíčku
			
			if(waste.size() != 0){
					if(y == 1){				// karta jde do hry
						if(stacks[x].put(selected))
							waste.pop();
					}
					else if(x > 2){			// karta jde do foundations
					System.out.println("foundation - " +(x-3));				

						if(foundations[x-3].put(selected))
							waste.pop();
					}
					
					
			}
			


		}

		if(b == 1)
			if(y == 1) {				// karty jdou ze hry do hry
				CardStack s = new Zasobnik(13);
				if(c == null){			// prázdné pole ve hře
					System.out.println("prazdne pole");				

					if(selected.value() == 13){		// můžem dát jen krále
						for(int i = 0; i < stacks[a].size(); i++)
							stacks[x].put(stacks[a].get(i),true);
						while(!stacks[a].isEmpty())
							stacks[a].pop();
					}


				}
				else{
					s.put(c, true);		//karta (stack) na stack
					System.out.println("jsme tuuuuuuu");				
					if(s.put(selected)){
						s = stacks[a].takeFrom(selected);
						if(!stacks[x].put(s))
            				stacks[a].put(s);

					}
					else
						stacks[a].put(s);
				}
			}
			else{
				if(x > 2){				// karta jde ze hry do foundation

					System.out.println("foundation - " +(x-3));				

					if(selected.equals(stacks[a].get())){		// vybrána horní karta

						foundations[x-3].put(selected);		// zkusíme vložit kartu
						if(foundations[x-3].get().equals(selected))
							stacks[a].pop();
					}
				}
			}
		deselect();
	}
	
	public boolean checkVictory(){
		boolean val = true;
		for (int i = 0; i < 4 ; i++ ) 
			if(foundations[i].size() != 13)
				val = false;
		return val;
	}

	private void turnWaste(){
		Card c;
		while(!waste.isEmpty()){
			c = waste.pop();
			c.turnFaceDown();
			deck.put(c, true);
		}
	}


	public void deselect(){

		selected = null;
		a = 0;
		b = 0;
			

	}

	public void select(int x, int y, Card karta){
		a = x;
		b = y;
		if(karta != null)
			selected = new Karta(karta.color(), karta.value());

		}



	private void initGame(){


		for (int i=0;i<7;i++) 
			for (int j=6;j>i;j--) 
				bases[j].put(deck.pop(),true);

		for (int i=0;i<7;i++) {
			stacks[i].put(deck.pop(),true);
			stacks[i].get().turnFaceUp();

			}
	}

	public CardStack getBase(int pos){
		return bases[pos];
	}

	public CardDeck getDeck(){
		return deck;
	}

	public CardStack getStack(int pos){
		return stacks[pos];
	}

	public CardStack getWaste(){
		return waste;
	}

	public CardDeck getFoundation(int i){
			return foundations[i];			
	}

}
