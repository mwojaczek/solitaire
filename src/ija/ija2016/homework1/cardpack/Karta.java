package ija.ija2016.homework1.cardpack;

import java.util.Objects;
import ija.ija2016.homework2.model.cards.*;

/**
 *
 * @author xdokou12
 */
public class Karta implements Card{
    private int val;
    private Card.Color barva;
    private int size;
    private Card[] karty;
    private int cnt = 0;
    private boolean turned = false;

    public Karta(Card.Color c, int value){
        this.val = value;
        this.barva = c;
    }
    
    public Card.Color color(){
        return this.barva;
    }


    
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj instanceof Karta){
            Karta card = (Karta)obj;
            if(card.barva == barva && card.barva == barva){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        String strValue = "";
        if(val == 1){
            strValue = "A";
        }else if(val < 11){
            strValue += val;
        }else if(val == 11){
            strValue = "J";
        }else if(val == 12){
            strValue = "Q";
        }else if(val == 13){
            strValue = "K";
        }
        return strValue + barva.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.val;
        hash = 67 * hash + Objects.hashCode(this.barva);
        return hash;
    }

    @Override
    public int value(){
        return this.val;
    }
    
    @Override
    public boolean isTurnedFaceUp() {
        if(turned == false)
            return false;
        else if(turned == true)
            return true;
        return false;
    }

    @Override
    public boolean turnFaceDown() {
        if(turned == true){
            turned = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean turnFaceUp() {
        if(turned == false){
            turned = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean similarColorTo(Card c) {
        return this.color().similarColorTo(c.color());
    }

    @Override
    public int compareValue(Card c) {
        return (this.value() - c.value());
    }
}
