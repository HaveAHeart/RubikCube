package main;

import org.junit.Assert;
import org.junit.Test;



public class Tests {
    @Test
    public void rotate() {
        Color[] colors = { Color.WHITE, Color.BLUE, Color.ORANGE };
        Row testRow = new Row(colors, 3);
        Col checkCol = new Col(colors, 3);
        Assert.assertSame(checkCol, testRow.rotateCW());

    }
}
