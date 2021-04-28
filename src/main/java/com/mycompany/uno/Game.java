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
 * This class contains fields and methods related to the status of a game, like the value and color of the last card
 * played, the current direction the game is going and how many cards should be drawn or if a player should be skipped.
 * It also contains a method to make the next player play.
 */
public class Game {
    // fields for the current game status
    private String currentColor;
    private int currentDirection;
    private String currentValue;
    private int currentDraw;
    private int currentSkip;
    private Card lastCardPlayed;
    private int turn;
    private boolean gameOver;

    // fields for players and decks in the game
    private Deck deck;
    private Deck discarded;
    private Hand [] hands;
    private Hand user;
    private Hand firstCom;
    private Hand secondCom;
    private Hand thirdCom;

    // constructor for playing
    public Game (UnoPlay uno) {
        currentColor = "";
        currentDirection = 1;
        currentValue = "";
        currentDraw = 0;
        currentSkip = 0;
        deck = new Deck();
        deck.shuffle();
        discarded = new Deck(0);
        firstCom = new Hand("Brianna", 7);
        secondCom = new Hand("Alexis", 7);
        thirdCom = new Hand("Adeline", 7);
        user = new Hand(uno.getName(), 7);
        hands = new Hand[4];
        hands[0] = user;
        hands[1] = firstCom;
        hands[2] = secondCom;
        hands[3] = thirdCom;
        turn = 0;
        gameOver = false;
        // give each player 7 cards to start
        for(int i=0; i<7; i++) {
            user.draw(deck, 1, discarded);
            firstCom.draw(deck, 1, discarded);
            secondCom.draw(deck, 1, discarded);
            thirdCom.draw(deck, 1, discarded);
        }
    }

    //method to have the computer play the next turn
    public void play(UnoPlay u) {

        Hand [] players = {user, firstCom, secondCom, thirdCom};
        String [] names = {"You", "Brianna", "Alexis", "Adeline"};

        // a computer player plays a card, which is passed to the GUI for display;
        u.setLastCardPlayed (players[turn].comPlay(this, deck, discarded));

        // if no card is left in the player's hand, end the game
        if (players[turn].getCards().size()<=0 || players[turn].getNumberOfCards() <= 0) {
            u.setWinner(names[turn]);
            endGame();
        }

        // determine next turn, which depends on the current game direction and number of skip
        turn += currentDirection;
        if (this.currentSkip == 1) {
            turn += currentDirection;
            this.setCurrentSkip(0);
        }
        // if turn is out of bound, change accordingly
        if (turn == -1) {
            turn = 3;
        } else if (turn == -2) {
            turn = 2;
        } else if (turn == 4) {
            turn = 0;
        } else if (turn == 5) {
            turn = 1;
        }
    }

    //Getters and setters for the game
    //sets gameOver to true
    public void endGame() {
        gameOver = true;
    }

    //determines if the game is over
    public boolean getGameOver() {
        return gameOver;
    }

    //returns the deck that players draw from
    public Deck getDeck() {
        return deck;
    }

   //return the discarded pile
    public Deck getDiscarded() {
        return discarded;
    }

    //returns the current card color
    public String getCurrentColor () {
        return currentColor;
    }

    //sets the current card color
    public void setCurrentColor (String currentColor) {
        this.currentColor = currentColor;
    }

    //returns the number of cards the player has
    public String getCurrentValue () {
        return currentValue;
    }

    //sets the current value of cards the player has
    public void setCurrentValue (String currentValue) {
        this.currentValue = currentValue;
    }

    //returns the current game direction
    public int getCurrentDirection () {
        return currentDirection;
    }

    //sets the current game direction
    public void setCurrentDirection (int currentDirection) {
        this.currentDirection = currentDirection;
    }

    //returns the current number of draws
    public int getCurrentDraw () {
        return currentDraw;
    }

    //sets the current number of draws
    public void setCurrentDraw (int currentDraw) {
        this.currentDraw = currentDraw;
    }

    //returns the current skip (either yes or no)
    public int getCurrentSkip () {
        return currentSkip;
    }

    //sets the current skip (either yes or no)
    public void setCurrentSkip (int currentSkip) {
        this.currentSkip = currentSkip;
    }

    //returns an array of all hands in the game
    public Hand [] getHands() {
        return hands;
    }

    //returns the users hand
    public Hand getUsersHand() {
        return user;
    }

    //returns the next turn
    public int getTurn() {
        return turn;
    }

    //sets the next turn
    public void setTurn(int turn) {
        this.turn = turn;
    }

    //returns the last played card
    public Card getLastCardPlayed () {
        return lastCardPlayed;
    }

    //sets the color of the last card played to a string
    public void setLastCardPlayed (Card lastCardPlayed) {
        this.lastCardPlayed = lastCardPlayed;
    }

}

