package ija.ija2016.homework2.model.board;

import ija.ija2016.homework1.cardpack.*;
import ija.ija2016.homework2.model.cards.Card;
import ija.ija2016.homework2.model.cards.CardDeck;
import ija.ija2016.homework2.model.cards.CardStack;

/**
 *
 * @author xdokou12
 */
public class FactoryKlondike extends AbstractFactorySolitaire {
    
    @Override
    public CardDeck createCardDeck() {
        return Balicek.createStandardDeck();
    }

    @Override
    public Card createCard(Card.Color color, int value) {
        if(value > 0 && value < 14)
            return new Karta(color, value);
        else return null;
    }

    @Override
    public CardDeck createTargetPack(Card.Color color) {
        return new Balicek(13, color);
    }

    @Override
    public CardStack createWorkingPack() {
        return new Zasobnik(13);
    }

    @Override
    public CardStack createBasePack(){
        return new Wastepile(6);
    }

    
}
