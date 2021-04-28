/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uno;

/**
 * author: JT Emnett
 */

/**
 * This a class to determine what can be played in a players hand hand and extends the deck.
 * It has methods for the computer players
 */

import java.util.*;
class Hand extends Deck {

    // unique field for the name of the hand holder
    private String name;

    // constructor
    public Hand(String name, int numberOfCards) {
        super(numberOfCards);
        this.name = name;
    }


    //sets player name
    public void setName(String name) {
        this.name = name;
    }

    //returns players name
    public String getName() {
        return name;
    }

    //method for computer to decide which card is playable and which one they should play next
    public boolean isLegal(Game game, Card card) {
        if ((game.getCurrentValue().equals("Draw2") || game.getCurrentValue().equals("WDraw4"))
                && game.getCurrentDraw() > 0) {
            if (game.getCurrentValue().equals(card.getValue())) return true;
            else return false;
        }

        if (game.getCurrentValue().equals(card.getValue()) |
                game.getCurrentColor().equals(card.getColor())) {
            return true;
        }

        if (card.getValue().equals("Wild") || card.getValue().equals("WDraw4")) {
            return true;
        }
        return false;
    }


    //determines if a card has a number value or if wild/skip
    public boolean isNormal(Card card) {
        return card.getValue().length() < 2;
    }


    /**
     * A method to find the card for the computer to play next.
     * 1) choose from playable action cards first (skip/draw.), if there are multiple, choose a random one
     * 2) if no legal action cards are present, choose a random playable numeric card
     * 3) if no numeric cards either, play a wild card
     * 4) if not, return -1 to draw from deck
     **/
    public int locateCardToPlay(Game game) {

        ArrayList<Integer> playableNormal = new ArrayList<Integer>();
        ArrayList<Integer> playableOther = new ArrayList<Integer>();
        ArrayList<Integer> wilds = new ArrayList<Integer>();

        // iterate through all the cards and categorize the legal cards into numeric cards,
        // action cards, and wild cards
        for (int i = 0; i < this.getCards().size(); i++) {
            if (isLegal(game, this.getCards().get(i))) {
                if (isNormal(this.getCards().get(i))) {
                    playableNormal.add(i);
                } else if (this.getCards().get(i).getValue().charAt(0) != 'W') {
                    playableOther.add(i);
                } else {
                    wilds.add(i);
                }
            }
        }

        int cardPosition = -1;
        if (playableNormal.size() > 0) {
            if (playableNormal.size() == 1) {
                cardPosition = playableNormal.get(0);
            } else {
                int which = UnoPlay.rint(0, playableNormal.size() - 1);
                cardPosition = playableNormal.get(which);
            }
        } else if (playableOther.size() > 0) {
            if (playableOther.size() == 1) {
                cardPosition = playableOther.get(0);
            } else {
                int which = UnoPlay.rint(0, playableOther.size() - 1);
                cardPosition = playableOther.get(which);
            }
        } else if (wilds.size() > 0) {
            if (wilds.size() == 1) {
                cardPosition = wilds.get(0);
            } else {
                int which = UnoPlay.rint(0, wilds.size() - 1);
                cardPosition = wilds.get(which);
            }
        }
        return cardPosition;
    }

    // strategy for choosing a color for the wild cards: which ever color of cards the computer
    // currently has the most of
    public void setWildCardColor(Card played) {
        int red = 0;
        int blue = 0;
        int green = 0;
        int yellow = 0;
        // iterate through all the cards and count the colors
        for (int i = 0; i < this.getCards().size(); i++) {
            if (this.getCards().get(i).getColor().equals("R")) red++;
            else if (this.getCards().get(i).getColor().equals("B")) blue++;
            else if (this.getCards().get(i).getColor().equals("G")) green++;
            else if (this.getCards().get(i).getColor().equals("Y")) yellow++;
        }
        if (red >= blue && red >= green && red >= yellow) played.setColor("R");
        else if (blue >= red && blue >= green && blue >= yellow) played.setColor("B");
        else if (green >= red && green >= blue && green >= yellow) played.setColor("G");
        else if (yellow >= red && yellow >= green && yellow >= blue) played.setColor("Y");
            // if all are equal, then choose red
        else {
            played.setColor("R");
        }

    }

    //draws cards
    // if no more cards in the deck, shuffle the discarded pile and make it the deck
    public void draw(Deck deck, int num, Deck discarded) {
        for (int i = 0; i < num; i++) {
            if (deck.getCards().size() < 1 || deck.getNumberOfCards() < 1) {
                deck = new Deck(discarded);
                deck.shuffle();
            }
            this.addCard(deck.removeCard(0));
        }
    }

    //Method to play a card or drawing one or more cards.
    public Card comPlay(Game game, Deck deck, Deck discarded) {

        // get the position of the card to play
        int cardPosition = locateCardToPlay(game);

        // if the player plays a card, change the current game color, value, direction, draw, skip
        if (cardPosition != -1) {
            Card played = this.removeCard(cardPosition);

            // strategy for choosing a color for the wild cards: which ever color of cards the player
            // currently has the most
            if (played.getColor().equals("W") && played.getValue().charAt(0) == 'W') {
                setWildCardColor(played);
                game.setCurrentColor(played.getColor());
            } else if (!played.getColor().equals("")) {
                game.setCurrentColor(played.getColor());
            }
            game.setCurrentValue(played.getValue());
            game.setCurrentDirection(played.getDirection() * game.getCurrentDirection());
            game.setCurrentDraw(played.getDraw() + game.getCurrentDraw());
            game.setCurrentSkip(played.getSkip());

            // add the card played to the discard pile
            discarded.addCard(played);
            return played;

        } else {

            // if the draws are because of draw cards, draw the according number; otherwise draw 1
            if (game.getCurrentDraw() > 0) {
                draw(deck, game.getCurrentDraw(), discarded);
                int d = game.getCurrentDraw();
                game.setCurrentDraw(0);
                // return a Card object used in the display
                return new Card("", "Drew " + d, 1, 0, 0);
            } else {
                draw(deck, 1, discarded);
                return new Card("", "Drew 1", 1, 0, 0);
            }
        }
    }
}


