package ija.ija2016.homework1.cardpack;

import java.util.Arrays;
import ija.ija2016.homework2.model.cards.*;

/**
 *
 * @author xdokou12
 */
public class Wastepile implements CardStack{
    private int size;
    private Card[] karty;
    private int cnt = 0;
    public Wastepile(int size){
        this.size = size;
        karty = new Karta[this.size];
    }

    public void put(Card card, boolean x){
        if(cnt < karty.length){
            karty[cnt] = card;
            cnt++;       
        }
    }

    public void printStack(){
        for (int i = 0; i < cnt; i++)
            System.out.println("Printing card on index "+ i + ": " + karty[i].toString());

    }

    
    public void put(Zasobnik stack){
        cnt = stack.size();
        for(int i = stack.size(); i >= 0; i--){
            karty[i] = stack.pop();
        }
    }
    public boolean isEmpty(){
        if(cnt == 0)
            return true;
        else
            return false;
    }
    
    public int size(){
        return cnt;
    }
    
    public Zasobnik takeFrom(Card card){
        Zasobnik pom = null;
        for(int i = 0; i < cnt; i++){
            if(karty[i].value() == card.value() && karty[i].color() == card.color()){
                pom = new Zasobnik(cnt - 1);
                for(int j = i; j < cnt; j++)
                    pom.put(karty[j]);
                cnt = cnt - (cnt - i);
                return pom;
            }
        }
        return pom;
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if(obj instanceof Zasobnik){
            Zasobnik cardStack = (Zasobnik)obj;
            if(size() != cardStack.size()){
                return false;
            }
            for(int i = 0; i < cnt; i++){
                if(!cardStack.get(i).equals(karty[i])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.size;
        hash = 23 * hash + Arrays.deepHashCode(this.karty);
        hash = 23 * hash + this.cnt;
        return hash;
    }

    @Override
    public boolean put(CardStack stack) {

        for(int i = 0; i < stack.size(); i++)
            if(!put(stack.get(i)))
                return false;

        return true;
    }

    @Override
    public CardStack pop(Card card) {
        return takeFrom(card);
    }

    @Override
    public Card.Color getColor(){
        return null;
    }
    @Override
    public boolean put(Card card) {

        if(cnt >= karty.length)
            return false;
        karty[cnt] = card;
        cnt++;
        return true;
        
    }

    @Override
    public void shuffleDeck(){}

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
    public Card get(int index) {
        if(isEmpty() || index < 0 || index > karty.length)
            return null;
        else return karty[index];
    }

    @Override
    public Card get() {
        if(cnt == 0)
            return null;
        return karty[cnt-1];
    }
}
