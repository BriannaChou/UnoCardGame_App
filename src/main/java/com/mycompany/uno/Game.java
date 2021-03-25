/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uno;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

/**
 *
 * @author briannachou
 */

public class Game {
    private int currentPlayer;
    private String[] playerIDs;
    private UnoDeck unoDeck;
    private ArrayList<ArrayList<UnoCard>> playerHand;
    private ArrayList<UnoCard> pile; //Goes to this pile deck after a player clicks on a card
    private UnoCard.Color validColor;
    private UnoCard.Value validValue;
    boolean gameDirection;

    public Game(String[] plIDs){
        unoDeck = new UnoDeck();
        unoDeck.shuffle();
        pile = new ArrayList<UnoCard>();

        playerIDs = plIDs;
        currentPlayer = 0;
        gameDirection = false; //Standard game direction

        playerHand = new ArrayList<ArrayList<UnoCard>>();
        //Fill up each player's hand with 7 uno cards.
        for (int i = 0; i < playerIDs.length; i++){
            ArrayList<UnoCard> hand = new ArrayList<UnoCard>(Arrays.asList(unoDeck.drawMultipleCards(7)));
            playerHand.add(hand);
        }
    }

    public void start(Game game){
        UnoCard card = unoDeck.drawCard(); //Start the game off by drawing a card
        validColor = card.getColor(); //Checking the color of the first card to know what is a valid color for the next player to play.
        validValue = card.getValue(); //Checking the value of the first card to know what is a valid value for the next player to play.

        //Now checking for the special cases
        if (card.getValue() == UnoCard.Value.Wild || card.getValue() == UnoCard.Value.WildDrawFour || card.getValue() == UnoCard.Value.DrawTwo){
            start(game); //START OVER!!! Do not have first card be a wild card.
        }
        if (card.getValue() == UnoCard.Value.Skip){
            JLabel msg = new JLabel(playerIDs[currentPlayer] + " has been skipped!");
            msg.setFont(new Font("Arial", Font.BOLD, 25));
            JOptionPane.showMessageDialog(null,msg);

            if (gameDirection == false){
                currentPlayer = (currentPlayer + 1) % playerIDs.length; //Go through the list of players and set it to correct player
            }
            else if (gameDirection == true){
                currentPlayer = (currentPlayer - 1) % playerIDs.length;
                if (currentPlayer == -1){
                    currentPlayer = playerIDs.length - 1;
                }
            }
        }

        pile.add(card);
    }

    /**
     * @return the top card
     */
    public UnoCard getTopCard(){
        return new UnoCard(validColor, validValue);
    }

    /**
     * @return the image of the top card
     */
    public ImageIcon getTopCardImg(){
        return new ImageIcon(validColor + "_" + validValue + ".png");
    }

    /**
     * Check if the game is over
     */
    public boolean isGameOver(){
        for(String player : this.playerIDs){
            if(isHandEmpty(player)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the current player from the String array of player IDs.
     */
    public String getCurrentPlayer(){
        return this.playerIDs[this.currentPlayer];
    }

    public String getPreviousPLayer(int i){
        int index = this.currentPlayer - i;
        if(index == -1){
            index = playerIDs.length - 1;
        }
        return this.playerIDs[index];
    }

    /**
     * @return all the players
     */
    public String[] getAllPlayers(){
        return playerIDs;
    }

    /**
     * @return a specific player's hand
     */
    public ArrayList<UnoCard> getPlayerHand(String plid){
        int index = Arrays.asList(playerIDs).indexOf(plid);
        return playerHand.get(index);
    }

    public int getPlayerHandSize(String plid){
        return getPlayerHand(plid).size();
    }

    /**
     * get the card that the player chooses to play
     */
    public UnoCard getPlayerCard(String plid, int choice){
        ArrayList<UnoCard> hand = getPlayerHand(plid);
        return hand.get(choice);
    }

    public boolean isHandEmpty(String plid){
        return getPlayerHand(plid).isEmpty();
    }

    public boolean validCardPlay(UnoCard card){
        return card.getColor() == validColor || card.getValue() == validValue;
    }

    /**
     * Checks if it is a player's turn
     */
    public void checkPlayerTurn(String plid) throws InvalidPlayerTurnException {
        if(this.playerIDs[this.currentPlayer] != plid){
            throw new InvalidPlayerTurnException("It is not " + plid + "'s turn!", plid);
        }
    }

    /**
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public void submitDraw(String plid) throws InvalidPlayerTurnException {
        checkPlayerTurn(plid);
        
        if (unoDeck.isEmpty()){
            unoDeck.replaceDeckWith(pile);
            unoDeck.shuffle();
        }

        getPlayerHand(plid).add(unoDeck.drawCard());
        if(gameDirection == false){
            currentPlayer = (currentPlayer + 1) % playerIDs.length;
        }
        else if(gameDirection == true){
            currentPlayer = (currentPlayer - 1) % playerIDs.length;
            if(currentPlayer == -1){
                currentPlayer = playerIDs.length - 1;
            }
        }
    }

    public void setCardColor(UnoCard.Color initialColor){
        validColor = initialColor;
    }

    /**
     * Bulk of the game
     */
    public void submitPlayerCard(String plid, UnoCard initialCard, UnoCard.Color declaredColor) 
    throws InvalidColorSubmissionException, InvalidValueSubmissionException, InvalidPlayerTurnException{
        checkPlayerTurn(plid);
        ArrayList<UnoCard> pHand = getPlayerHand(plid);

        if(!validCardPlay(initialCard)){
            if(initialCard.getColor() == UnoCard.Color.Wild){
                validColor = initialCard.getColor();
                validValue = initialCard.getValue();
            }
            
            if(initialCard.getColor() != validColor){
                JLabel msg1 = new JLabel("Invalid player move, expected color: " + validColor + " but got color " + initialCard.getColor());
                msg1.setFont(new Font("Arial", Font.BOLD, 25));
                JOptionPane.showMessageDialog(null,msg1);
                throw new InvalidColorSubmissionException(msg1,initialCard.getColor(),validColor);
            }
            else if(initialCard.getValue() != validValue){
                JLabel msg2 = new JLabel("Invalid player move, expected value: " + validValue + " but got value " + initialCard.getValue());
                msg2.setFont(new Font("Arial", Font.BOLD, 25));
                JOptionPane.showMessageDialog(null,msg2);
                throw new InvalidValueSubmissionException(msg2,initialCard.getValue(),validValue);
            }
        }

        pHand.remove(initialCard);
        //WE WILL WANT TO CHANGE THIS METHOD BECAUSE WE DO NOT WANT THE GAME TO END LIKE THAT WE NEED IT TO GO BACK TO THE APP
        if(isHandEmpty(this.playerIDs[currentPlayer])){
            JLabel msg3 = new JLabel(this.playerIDs[currentPlayer] + " won the game! Thank you for playing");
            msg3.setFont(new Font("Arial", Font.BOLD, 25));
            JOptionPane.showMessageDialog(null,msg3);
            //System.exit(0);
        }

        validColor = initialCard.getColor();
        validValue = initialCard.getValue();
        pile.add(initialCard);
        if(gameDirection == false){
            currentPlayer = (currentPlayer + 1) % playerIDs.length;
        }
        else if(gameDirection == true){
            currentPlayer = (currentPlayer - 1) % playerIDs.length;
            if(currentPlayer == -1){
                currentPlayer = playerIDs.length - 1;
            }
        }

        if(initialCard.getColor() == UnoCard.Color.Wild){
            validColor = declaredColor;
        }

        if(initialCard.getValue() == UnoCard.Value.WildDrawFour){
            validColor = declaredColor;
            plid = playerIDs[currentPlayer];
            getPlayerHand(plid).add(unoDeck.drawCard());
            getPlayerHand(plid).add(unoDeck.drawCard());
            getPlayerHand(plid).add(unoDeck.drawCard());
            getPlayerHand(plid).add(unoDeck.drawCard());
            JLabel msg4 = new JLabel(plid + " draw 4 cards!");
            msg4.setFont(new Font("Arial", Font.BOLD, 25));
            JOptionPane.showMessageDialog(null,msg4);
        }

        if(initialCard.getValue() == UnoCard.Value.DrawTwo){
            plid = playerIDs[currentPlayer];
            getPlayerHand(plid).add(unoDeck.drawCard());
            getPlayerHand(plid).add(unoDeck.drawCard());
            JLabel msg4 = new JLabel(plid + " draw 2 cards!");
            msg4.setFont(new Font("Arial", Font.BOLD, 25));
            JOptionPane.showMessageDialog(null,msg4);
        }
        
        if(initialCard.getValue() == UnoCard.Value.Skip){
            JLabel msg4 = new JLabel(playerIDs[currentPlayer] + " was Skipped!");
            msg4.setFont(new Font("Arial", Font.BOLD, 25));
            JOptionPane.showMessageDialog(null,msg4);
            if(gameDirection == false){
                currentPlayer = (currentPlayer + 1) % playerIDs.length;
            }
            else if(gameDirection == true){
                currentPlayer = (currentPlayer - 1) % playerIDs.length;
                if(currentPlayer == -1){
                    currentPlayer = playerIDs.length - 1;
                }
            }
        }
    }
}

/**
 * Create some exceptions we will need
 */
class InvalidPlayerTurnException extends Exception {
    String playerId;

    public InvalidPlayerTurnException(String msg, String plid){
        super(msg);
        playerId = plid;
    }

    public String getPlid(){
        return playerId;
    }
}

class InvalidColorSubmissionException extends Exception{
    private UnoCard.Color expected;
    private UnoCard.Color actual;

    public InvalidColorSubmissionException(JLabel msg, UnoCard.Color initialActual, UnoCard.Color initialExpected){
        this.actual = initialActual;
        this.expected = initialExpected;
    }
}

class InvalidValueSubmissionException extends Exception {
    private UnoCard.Value expected;
    private UnoCard.Value actual;

    public InvalidValueSubmissionException(JLabel msg, UnoCard.Value initialActual, UnoCard.Value initialExpected){
        this.actual = initialActual;
        this.expected = initialExpected;
    }
}
