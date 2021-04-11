/**
 * author: JT Emnett
 **/

/**
 * This class represents a card used in Uno
 */

import java.util.ArrayList;

class Card {
    private String color; // "R", "B", "G", or "Y"
    private String value; //1-9
    private int direction; // forward(1) or backward(-1)
    private int draw; //  number of cards to draw: 0, 2 or 4
    private int skip; // each card has either 0 skips or 1 skip

    // constructor
    public Card(String color, String value, int direction, int draw, int skip) {
        this.color = color;
        this.value = value;
        this.direction = direction;
        this.draw = draw;
        this.skip = skip;
    }

    // getter and setters for private fields

    /** Return the color of the card
     **/
    public String getColor() {
        return color;
    }

    /** Set the color of the card
     **/
    public void setColor(String color) {
        this.color = color;
    }

    /** Return the value of the card
     **/
    public String getValue() {
        return value;
    }

    /** Return the direction of the card
     **/
    public int getDirection () {
        return direction;
    }

    /** Return the number of draws of the card
     **/
    public int getDraw () {
        return draw;
    }

    /** Return the number of skips of the card
     **/
    public int getSkip () {
        return skip;
    }
}