package stud.NTNU.idatt2001.Oblig3.Cardgame;

import stud.NTNU.idatt2001.Oblig3.Cardgame.exception.WrongInputException;

import java.util.*;

/**
 * Represents a complete deck of cards (52 cards).
 * In addition the class contains a methode called "dealHand(int n)" which represents a hand of random cards.
 *
 * @author Thadshajini
 * @version 2020-04-08
 */
public class DeckOfCards {
    private final char[] suit = { 'S', 'H', 'D', 'C' };
    //Map is the collection that has been selected to contain a deck of cards (52 elements/cards).
    //The reason why is because:
    //Map does not allow duplicates and it fits well in this context due to that a deck of cards do not contain cards of the same type (same suit and face).
    //We also do not need the order of the elements, ie the cards that are returned.
    private Map<String,PlayingCard> cards;

    /**
     * Constructor
     */
    public DeckOfCards() {
        cards = new HashMap<>();
        fillDeckOfCards();
    }

    /**
     * Files "cards" with all the 52 cards.
     */
    private void fillDeckOfCards(){
        for (char s:suit) {
            for (int i = 1; i <= 13 ; i++){
                String keycard = ""+s+i;
                PlayingCard playingCard = new PlayingCard(s,i);
                cards.put(keycard,playingCard);
            }
        }
    }



    /**
     * Deals a hand of random cards from the deck "cards".
     * @param n amount of cards desired.
     * @return n amount of random cards.
     * @throws WrongInputException if the input of amount of cards entered is not between 1-52.
     */
    public HashSet<PlayingCard> dealHand(int n) throws WrongInputException {
        if (n < 1 || n > 52) {
            throw new WrongInputException("Please specify a valid number of cards (1-52).");
        }
        List<PlayingCard> valuesList = new ArrayList<>(cards.values());
        Collections.shuffle(valuesList);
        HashSet<PlayingCard> delHandCards = new HashSet<>();
        for (int i = 0; i < n; i++) {
            delHandCards.add(valuesList.get(i));
        }
        return delHandCards;
    }

}


