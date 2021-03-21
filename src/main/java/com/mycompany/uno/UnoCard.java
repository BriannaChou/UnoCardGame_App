/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uno;

/**
 *
 * @author briannachou
 */

public class UnoCard {

    enum Color{
        Red, Green, Blue, Yellow, Wild;

        private static final Color[] colors = Color.values(); //Create an array of our enumeration and filling it with the color values
        public static Color getColor(int i){
            return Color.colors[i];
        }
    }

    enum Value{
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, DrawFour, Skip, Wild, WildDrawFour;

        private static final Value[] values = Value.values(); //Create an array of our enumeration and filling it with the value values
        public static Value getValue(int i){
            return Value.values[i];
        }
    }

    private final Color color; //attribute cannot be changed!
    private final Value value;

    public UnoCard(final Color initialColor, final Value initialValue){
        this.color = initialColor;
        this.value = initialValue;
    }

    public Color getColor(){
        return this.color;
    }

    public Value getValue(){
        return this.value;
    }

    public String toString(){
        return this.color + "_" + this.value;
    }
}

