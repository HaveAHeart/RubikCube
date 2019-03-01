package main;

import java.io.Serializable;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;

public final class Col implements Serializable {
    private int dim;
    private Color[] colors;

    //overriding equals and hashcode for tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Col col = (Col) o;
        return dim == col.dim &&
                Arrays.equals(colors, col.colors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dim);
        result = 31 * result + Arrays.hashCode(colors);
        return result;
    }

    //deep cloning methods for row replacing while rotating
    public Col deepClone() {
        Color[] newColors = this.colors;
        return new Col(newColors, dim);
    }

    //generator
    public Col(Color[] input, int dimension) {
        dim = dimension;
        if (input.length != dim) {
            throw new InputMismatchException("wrong Color[] array size while creating column");
        }
        colors = input;
    }

    //rotation methods
    public Col invert() {
        return new Col(colors, dim).rotateCW().rotateCW().deepClone();
    }

    public Row rotateCW() {
        for (int i = 0; i < colors.length/2; i++){
            Color temp = colors[i];
            colors[i] = colors[colors.length - 1 - i];
            colors[colors.length - 1 - i] = temp;
        }
        return new Row(colors, dim);
    }

    public Row rotateCCW() { return new Row(colors, dim); }

    //access methods
    public Color getValue(int num) {
        if (num >= colors.length) {
            throw new InputMismatchException("index of element is too big while " +
                    "trying to get a value from column");
        }
        return colors[num];
    }
}