/**
 * author: JT Emnett
 */

/**
 * A deck holds an ArrayList of Card objects, and has methods to add and remove cards and shuffle.
 */

import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

class Deck {
    private ArrayList<Card> cards;
    private int numberOfCards;

    // constructor; when no argument is passed, initialize a whole deck with 108 cards
    public Deck () {
        numberOfCards = 108;
        cards = new ArrayList<Card>(numberOfCards);

        String[] colors = {"R", "B", "Y", "G"};

        // iterate through all the colors
        for (int i=0; i<4; i++) {

            // each color only has one "0"
            cards.add(i*25, new Card (colors[i], "0", 1, 0, 0));

            for (int j=1; j<19; j+=2) {
                cards.add(i*25+j, new Card (colors[i], j/2+1+"", 1, 0, 0) ) ;
                cards.add(i*25+j+1, new Card (colors[i], j/2+1+"", 1, 0, 0) );
            }
            // each color has some action cards
            cards.add( 19+i*25, new Card (colors[i], "Draw2", 1, 2, 0) );
            cards.add( 20+i*25, new Card (colors[i], "Draw2", 1, 2, 0) );
            cards.add( 21+i*25, new Card (colors[i], "Reverse", -1, 0, 0) );
            cards.add( 22+i*25, new Card (colors[i], "Reverse", -1, 0, 0) );
            cards.add( 23+i*25, new Card (colors[i], "Skip", 1, 0, 1) );
            cards.add( 24+i*25, new Card (colors[i], "Skip", 1, 0, 1) );
        }

        // wild cards don't have a set color
        for (int i=100; i<104; i++) {
            cards.add( i, new Card ("W", "Wild", 1, 0, 0) );
        }
        for (int i=104; i<108; i++) {
            cards.add( i, new Card ("W", "WDraw4", 1, 4, 0) );
        }
    }

    // constructor with number of cards an argument
    public Deck (int numberOfCards) {
        cards = new ArrayList<Card>(numberOfCards);
        // set to 0 now because the numberOfCards increments when addCard is called later
        this.numberOfCards = 0;
    }

    // constructor with another deck object passed as an argument
    public Deck (Deck d) {
        this.numberOfCards = d.getNumberOfCards();
        this.cards = d.getCards();
    }

    //return number of cards in the deck
    public int getNumberOfCards () {
        return numberOfCards;
    }

    //sets number of cards in the deck
    public void setNumberOfCards (int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

   //returns and ArrayList with cards in the deck
    public ArrayList<Card> getCards () {
        return cards;
    }

    //adds cards to players hand
    public void addCard (Card c) {
        cards.add(c);
        numberOfCards++;
    }

    //removes cards at a given index from the deck
    public Card removeCard (int index) {
        numberOfCards--;
        return cards.remove (index);
    }

    //shuffles the deck
    public void shuffle () {
        // randomly exchange the positions of two cards in the ArrayList for 150 times
        for (int i=0;i<150; i++) {
            int a = UnoPlay.rint(0, cards.size()-1);
            int b = UnoPlay.rint(0, cards.size()-1);
            Card temp = cards.get(a);
            cards.set (a, cards.get(b));
            cards.set (b, temp);
        }
    }
}
