package ija.ija2016.homework2.model.cards;

/**
 *
 * @author xdokou12
 */
public interface Card{
    int value();
    boolean isTurnedFaceUp();
    boolean turnFaceUp();
    boolean turnFaceDown();
    Card.Color color();
    boolean similarColorTo(Card c);
    int compareValue(Card c);
    
    public static enum Color{
        CLUBS("C"), SPADES("S"), DIAMONDS("D"), HEARTS("H");
        String typ;
        Color(String typ){
            this.typ = typ;
        }
        public boolean similarColorTo(Card.Color c){
            if((this == CLUBS || this == SPADES) && (c == CLUBS || c == SPADES))
                return true;
            else if((this == DIAMONDS || this == HEARTS) && (c == DIAMONDS || c == HEARTS))
                return true;
            else return false;
        }
        @Override
        public String toString(){
            return typ;
        } 
    }

}
