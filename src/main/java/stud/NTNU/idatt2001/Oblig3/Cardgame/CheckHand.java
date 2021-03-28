package stud.NTNU.idatt2001.Oblig3.Cardgame;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents analysis of the cards; sum of all deck of hands, gets all the cards that are heart from the deck of hands, checks if the deck of hands has any spades of queen and flush.
 * This class is used after using the class "DeckOfCards", ie retrieves information from the "DeckOfCards" class to analyze the cards.
 *
 * @author Thadshajini
 * @version 2020-04-08
 */
public class CheckHand {
    //HashSet is the collection that has been selected to return the delHandCards (random cards).
    //The reason why is because:
    //Hashset does not allow duplicates and it fits well in this context due to that a deck of cards where we draw random cards from do not contain cards of the same type, aka (same suit and face).
    //We also do not need the order of the elements, ie the cards that are returned.
    private HashSet<PlayingCard> delHandCards;

    /**
     * Constructor
     * @param delHandCards contains random cards that are based on having entered the amount of cards desired.
     */
    public CheckHand(HashSet<PlayingCard> delHandCards) {
        this.delHandCards = delHandCards;
    }

    /**
     * Calculate the sum of all the values of the cards in hand (ace = 1).
     * @return (int) sum of all deck og hands.
     */
    public int sumOfAllDeckOfHands(){
        int sum = delHandCards.stream().map(PlayingCard::getFace).reduce((a,b) -> a + b).get();
        return sum;
    }

    /**
     * Filters through "delHandCards" and selects every elements/cards that are hearts hearts/ have "H" as suit.
     * @return all cards that are hearts on the form "H12 H9 H1".
     *         returns "No Hearts" if there are no Hearts on "delHandCards".
     */
    public String getAllHearts(){
        List<PlayingCard> fillteredCard = delHandCards.stream().filter(p -> 'H'==p.getSuit()).collect(Collectors.toList());
        if (fillteredCard.isEmpty()){
            return "No hearts";
        }
        else {
            String s = fillteredCard.stream().map(n -> n.getAsString()).collect(Collectors.joining(" "));
            return s;
        }
    }

    /**
     * Checks if the card "Spades of queen" is among the cards on hand, "dealHandCards".
     * @return true if the card "Spades of queen" is among the cards on hand.
     *         false if not.
     */
    public boolean checkSpadesOfQueen(){
       return delHandCards.stream().anyMatch(p -> "S12".equals(p.getAsString()));
    }

    /**
     * Check if "delHandCards" constitute a "5-flush". I.e. 5 cards of the same suit.
     * (5 hearts, 5 spades, 5 diamonds, 5 clubs)
     * @return false if "delHandCards" contains less than 5 cards or if it has not 5 cards with same suit.
     *         true if five or more cards/elements in "delHandCards" have same suit.
     */
    public boolean flush(){
        if (delHandCards.size()<5){
            return false;
        }
        List<Character> allSuits = delHandCards.stream().map(p -> p.getSuit()).collect(Collectors.toList());
        Set<Character> fiveSimilarElements = allSuits.stream().filter(i -> Collections.frequency(allSuits, i) >=5)
                .collect(Collectors.toSet());
        if (fiveSimilarElements.isEmpty()){
            return false;
        }
        return true;
    }

}
