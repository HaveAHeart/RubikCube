package main;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;

public final class Row implements Serializable {
    private int dim;
    private Color[] colors;

    //overriding equals and hashcode for tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return dim == row.dim &&
                Arrays.equals(colors, row.colors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dim);
        result = 31 * result + Arrays.hashCode(colors);
        return result;
    }

    //deep cloning methods for row replacing while rotating
    public Row deepClone() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        Row returnRow = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            returnRow = (Row) new ObjectInputStream(bais).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnRow;
    }

    //generators
    public Row(Color[] input, int dimension) {
        dim = dimension;
        if (input.length != dim) {
            throw new InputMismatchException("wrong Color[] array size while creating row");
        }

        colors = input;
    }

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

    //access methods
    public Color getValue(int num) {
        if (num >= colors.length || num < 0) {
            throw new InputMismatchException("incorrect number while trying to get a value");
        }
        return colors[num];
    }

    public int getDim() { return this.dim; }
}
