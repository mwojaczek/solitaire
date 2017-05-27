package ija.ija2016.homework2.model.cards;

/**
 *
 * @author xdokou12
 */
public interface CardStack extends CardDeck {
    public boolean put(CardStack stack);
    public CardStack pop(Card card);
    public void printStack();
    public CardStack takeFrom(Card card);
}

