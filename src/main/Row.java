package main;

import java.util.InputMismatchException;

public class Row {
    private int dim;
    private Color[] colors;

    //generator
    public Row(Color[] input, int dimension) {
        dim = dimension;
        if (input.length != dim) {
            throw new InputMismatchException("wrongColor[] array size while creating row");
        }
        colors = input;
    }

    //------------------
    //rotation methods
    public Col rotateCW() { return new Col(colors, dim); }

    public Col rotateCCW() {
        for (int i = 0; i < colors.length/2; i++){
            Color temp = colors[i];
            colors[i] = colors[colors.length - 1 - i];
            colors[colors.length - 1 - i] = temp;
        }
        return new Col(colors, dim);
    }

    //------------------
    //access methods
    public Color getValue(int num) {
        if (num >= colors.length) {
            throw new InputMismatchException("index of element is too big while " +
                    "trying to get a value from row");
        }
        return colors[num];
    }
}
