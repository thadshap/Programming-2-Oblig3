package stud.NTNU.idatt2001.Oblig3.Cardgame;

import stud.NTNU.idatt2001.Oblig3.Cardgame.exception.WrongInputException;

import java.util.*;

public class DeckOfCards {
    private final char[] suit = { 'S', 'H', 'D', 'C' };
    private Map<String,PlayingCard> cards;

    public DeckOfCards() {
        cards = new HashMap<>();
        fillDeckOfCards();
    }

    private void fillDeckOfCards(){
        for (char s:suit) {
            for (int i = 1; i <= 13 ; i++){
                String keycard = ""+s+i;
                PlayingCard playingCard = new PlayingCard(s,i);
                cards.put(keycard,playingCard);
            }
        }
    }

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


