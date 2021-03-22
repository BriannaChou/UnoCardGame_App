/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uno;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author briannachou
 * Deck contains 108 cards.
 */


public class UnoDeck {
    private UnoCard[] cards;
    private int cardsInDeck;

    public UnoDeck(){
        cards = new UnoCard[120]; //originally 108
        reset();
    }

    public void reset(){
        UnoCard.Color[] colors = UnoCard.Color.values();
        cardsInDeck = 0;

        for(int i = 0; i < colors.length-1;i++){ //Add color
            UnoCard.Color color = colors[i];
            cards[cardsInDeck] = new UnoCard(color, UnoCard.Value.getValue(0)); //Add a single zero card !!!!!!!!!!!!!!!!!!!!!!!!!!

            for(int j = 1; j < 10; j++){ //Add two cards of each number. 1-9 cards.
                cards[cardsInDeck++] = new UnoCard(color,UnoCard.Value.getValue(j));
                cards[cardsInDeck++] = new UnoCard(color,UnoCard.Value.getValue(j));
            }

            UnoCard.Value[] specialValues = new UnoCard.Value[]{UnoCard.Value.DrawTwo, UnoCard.Value.Skip};
            for(UnoCard.Value value : specialValues){ //Starting at first value in array values, and creating two cards of special cards.
                cards[cardsInDeck++] = new UnoCard(color, value);
                cards[cardsInDeck++] = new UnoCard(color, value);
            }

            UnoCard.Value[] wildValues = new UnoCard.Value[]{UnoCard.Value.Wild, UnoCard.Value.WildDrawFour};
            for(UnoCard.Value value : wildValues){
                for(int x = 0; x < 4; x++){ 
                    cards[cardsInDeck++] = new UnoCard(UnoCard.Color.Wild, value);
                }

            }
        }
    }

    /**
     * @param cards (pile)
     * After deck runs out, it will replace it with the pile.
     */
    public void replaceDeckWith(ArrayList<UnoCard> cards){
        this.cards = cards.toArray(new UnoCard[cards.size()]);
        this.cardsInDeck = this.cards.length;
    }

    /**
     * @return
     * Checks if there are no cards in the deck.
     */
    public boolean isEmpty(){
        return cardsInDeck == 0;
    }

    /**
     * Shuffles the deck
     */
    public void shuffle(){
        int len = this.cards.length;
        Random random = new Random();

        for(int i = 0; i < this.cards.length; i++){
            /**
             * Get a random index of the array past its current index
             * ... Then the argument is an exclusive bound.
             * Swap the random element with the present element.
             */

            int randomVal = i + random.nextInt(len - i);
            UnoCard randomCard = cards[randomVal];
            cards[randomVal] = cards[i]; //Swap
            cards[i] = randomCard; //Swap
        }
    }

    /**
     * Draws a single uno card from the deck
     */
    public UnoCard drawCard() throws IllegalArgumentException {
        if (isEmpty()){
            throw new IllegalArgumentException("A card cannot be drawn since there is no card in the deck!");
        }
        return cards[--cardsInDeck]; //Returns the card at the top of the deck
    }

    /**
     * Draw multiple uno cards from deck. Used for when someone hits you with the +2 or +4
     */
    public UnoCard[] drawMultipleCards(int num) throws IllegalArgumentException {
        //Checking for illegal plays
        if (isEmpty()){
            throw new IllegalArgumentException("A card cannot be drawn since there is no card in the deck!");
        }
        if (num < 0){
            throw new IllegalArgumentException("Must draw positive number of cards. Tried to draw" + num + " cards");
        }
        if (num > cardsInDeck){
            throw new IllegalArgumentException("Cannot draw" + num + " cards since there are only " + cardsInDeck + " cards in the deck.");

        }

        UnoCard[] returnCards = new UnoCard[num];
        for (int i = 0; i < num; i++){
            returnCards[i] = cards[--cardsInDeck];
        }
        return returnCards;
    }

    /**
     * GUI game, so...
     * We need top card image, so we need to return an image icon.
     */
    public ImageIcon drawCardImage() throws IllegalArgumentException { //HELP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! WEEWOO
        if (isEmpty()){
            throw new IllegalArgumentException("A card cannot be drawn since there is no card in the deck!");
        }
        return new ImageIcon(cards[--cardsInDeck].toString() + "png");
    }
}
