package stud.NTNU.idatt2001.Oblig3.Cardgame;

import javafx.util.converter.IntegerStringConverter;

import java.util.*;
import java.util.stream.Collectors;

// denne klassen tas i bruk etter å ha tatt i bruk classen "DeckOfCards"

public class CheckHand {
    private HashSet<PlayingCard> delHandCards;

    public CheckHand(HashSet<PlayingCard> delHandCards) {
        this.delHandCards = delHandCards;
    }

    //Regn ut summen av alle verdiene av kortene på hånd (ess = 1)
    public int sumOfAllDeckOfHands(){
        int sum = delHandCards.stream().map(PlayingCard::getFace).reduce((a,b) -> a + b).get();
        return sum;
    }

    //Hent ut bare kort som er av fargen "Hjerter", og vis i et tekstfelt på formen "H12 H9 H1".
    // Dersom det ikke er noen Hjerter på hånd,kan tekstfeltet inneholde teksten "No Hearts", for eksempel.
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

    //Sjekk om kortet "Spar dame" finnes blant kortene på hånden.
    public boolean checkSpadesOfQueen(){
       return delHandCards.stream().anyMatch(p -> "S12".equals(p.getAsString()));
    }

    //Sjekk om kortene på hånd utgjør en "5-flush". D.v.s. 5 kort av samme farge
    // (5 hjerter eller 5 ruter eller 5 kløver eller 5 spar).

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
