package ija.ija2016.homework1.cardpack;

import java.util.Arrays;
import ija.ija2016.homework2.model.cards.*;
import java.util.Random;


/**
 *
 * @author xdokou12
 */
public class Balicek implements CardDeck{
    private int size;
    private Card[] karty;
    private int cnt = 0;
    private Card.Color color;

    public Balicek(int size){
        karty = new Karta[size];
        this.size = size;
    }
    
    public Balicek(int size, Card.Color color){

        this.color = color;
        karty = new Karta[size];
        this.size = size;
    }
    
    public static Balicek createStandardDeck(){
        Balicek balicek = new Balicek(52);
        for(int i = 1; i <= 13; i++){
            balicek.put(new Karta(Karta.Color.CLUBS, i),true);
        }
        for(int i = 1; i <= 13; i++){
            balicek.put(new Karta(Karta.Color.SPADES, i),true);
        }
        for(int i = 1; i <= 13; i++){
            balicek.put(new Karta(Karta.Color.DIAMONDS, i),true);
        }
        for(int i = 1; i <= 13; i++){
            balicek.put(new Karta(Karta.Color.HEARTS, i),true);
        }
        
        return balicek;
    }

    @Override
    public Card.Color getColor(){
        return color;
    }
    
    @Override
    public void put(Card card, boolean x){
        if(cnt >= karty.length){
            return;
        }else{
            karty[cnt] = card;
            cnt++;
        }
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if(obj instanceof Balicek){
            Balicek cardDack = (Balicek)obj;
            if(size() != cardDack.size()){
                return false;
            }
            for(int i = 0; i < cnt; i++){
                if(!cardDack.karty[i].equals(karty[i])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.size;
        hash = 73 * hash + Arrays.deepHashCode(this.karty);
        hash = 73 * hash + this.cnt;
        return hash;
    }

    @Override
    public Card get() {
        return karty[cnt-1];
    }

    @Override
    public Card get(int index) {
        if(isEmpty() || index < 0 || index > karty.length)
            return null;
        else return karty[index];
    }

    @Override
    public boolean isEmpty() {
        if(cnt == 0)
            return true;
        else
            return false;
    }

    @Override
    public Card pop() {
        if(cnt == 0)
            return null;
        else {
            cnt--;
            return karty[cnt];
        }
    }

    @Override
    public void shuffleDeck(){
        int index;
        Card temp;
        Random random = new Random();

        for (int i = size - 1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = karty[index];
            karty[index] = karty[i];
            karty[i] = temp;
            }
    }

    @Override
    public boolean put(Card card) {
                System.out.println("put");                

      
        if(color != card.color())
            return false;

        else if(cnt >= karty.length)
            return false;

        else if((cnt == 0 && card.value() == 1) || (cnt > 0 && karty[cnt - 1].value() + 1 == card.value())){
            karty[cnt] = card;
            cnt++;
            return true;
        }

        return false;
    }

    @Override
    public int size() {
        return cnt;
    }
}
