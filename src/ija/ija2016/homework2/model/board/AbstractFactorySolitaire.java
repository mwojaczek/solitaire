package ija.ija2016.homework2.model.board;

import ija.ija2016.homework2.model.cards.*;
/**
 *
 * @author xdokou12
 */
public abstract class AbstractFactorySolitaire{
    public abstract CardDeck createCardDeck();
    public abstract Card createCard(Card.Color color, int value);
    public abstract CardDeck createTargetPack(Card.Color color);
    public abstract CardStack createWorkingPack();
    public abstract CardStack createBasePack();

}
