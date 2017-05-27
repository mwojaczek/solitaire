package ija.ija2016.homework2.model.cards;

/**
 *
 * @author xdokou12
 */
public interface CardDeck {
    public int size();
    public boolean put(Card card);
    public void put(Card card, boolean x);
    public Card pop();
    public boolean isEmpty();
    public Card get(int index);
    public Card get();
    public Card.Color getColor();
    public void shuffleDeck();
}
