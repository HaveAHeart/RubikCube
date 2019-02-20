package main;

import java.util.InputMismatchException;

public class Col {
    private int dim;
    private Color[] colors;

    //generator
    public Col(Color[] input, int dimension) {
        dim = dimension;
        if (input.length != dim) {
            throw new InputMismatchException("wrong Color[] array size while creating column");
        }
        colors = input;
    }

    //------------------
    //rotation methods
    public Row rotateCW() {
        for (int i = 0; i < colors.length/2; i++){
            Color temp = colors[i];
            colors[i] = colors[colors.length - 1 - i];
            colors[colors.length - 1 - i] = temp;
        }
        return new Row(colors, dim);
    }

    public Row rotateCCW() { return new Row(colors, dim); }

    //------------------
    //access methods
    public Color getValue(int num) {
        if (num >= colors.length) {
            throw new InputMismatchException("index of element is too big while " +
                    "trying to get a value from column");
        }
        return colors[num];
    }
}