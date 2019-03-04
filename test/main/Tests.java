package main;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Tests {
    @Test
    public void rowRotate() {
        Color[] colorsCW = {Color.WHITE, Color.BLUE, Color.ORANGE};
        Row testRow = new Row(colorsCW, 3);
        Col testCol = new Col(colorsCW, 3);
        assertEquals(testCol, testRow.rotateCW());

        Color[] colorsCCWCol = {Color.ORANGE, Color.BLUE, Color.WHITE};
        Row testRowCCW = new Row(colorsCW, 3);
        Col testColCCW = new Col(colorsCCWCol, 3);
        assertEquals(testColCCW, testRowCCW.rotateCCW());

        Color[] colorsCW2 = {Color.WHITE, Color.BLUE, Color.ORANGE, Color.GREEN};
        Row testRow2 = new Row(colorsCW2, 4);
        Col testCol2 = new Col(colorsCW2, 4);
        assertEquals(testCol2, testRow2.rotateCW());

        Color[] colorsCCWCol2 = {Color.GREEN, Color.ORANGE, Color.BLUE, Color.WHITE};
        Row testRowCCW2 = new Row(colorsCW2, 4);
        Col testColCCW2 = new Col(colorsCCWCol2, 4);
        assertEquals(testColCCW2, testRowCCW2.rotateCCW());
    }

    @Test
    public void colRotate() {
        Color[] colorsCW = {Color.WHITE, Color.BLUE, Color.YELLOW, Color.GREEN};
        Col testColCW = new Col(colorsCW, 4);
        Color[] colorsCWRow = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Row testRowCW = new Row(colorsCWRow, 4);
        assertEquals(testRowCW, testColCW.rotateCW());

        Color[] colorsCCW = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Col testColCCW = new Col(colorsCCW, 4);
        Color[] colorsCCWRow = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Row testRowCCW = new Row(colorsCCWRow, 4);
        assertEquals(testRowCCW, testColCCW.rotateCCW());

        Color[] colorsCol = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Col testCol = new Col(colorsCol, 4);

        Color[] colorsInvertedCol = { Color.WHITE, Color.BLUE, Color.YELLOW, Color.GREEN };
        Col invertedCol = new Col(colorsInvertedCol, 4);
        assertEquals(testCol.invert(), invertedCol);
    }

    @Test
    public void sideRotate() {
        Color[][] colorsOrig = {
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
        };
        Side origSide = new Side(colorsOrig, 4);

        Color[][] colorsRotatedCW = {
                {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
                {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW},
                {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE},
                {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE},
        };
        Side rotatedSideCW = new Side(colorsRotatedCW, 4);
        assertEquals(rotatedSideCW.toString(), origSide.rotateCW().toString());

        Color[][] colorsRotatedCCW = {
                {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE},
                {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE},
                {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW},
                {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
        };
        Side rotatedSideCCW = new Side(colorsRotatedCCW, 4);
        assertEquals(rotatedSideCCW.toString(), origSide.rotateCCW().toString());


    }

    @Test
    public void cubeRotate() {
        Cube testCube = new Cube(3);

        System.out.println(testCube.toString());
        testCube.rotateRowCW("top", 0, 0);
        //testCube.rotateRowCCW("top", 0, 0);
        //testCube.rotateColUp("back", 0, 0);
        //testCube.rotateColDown("back", 0, 0);
        //testCube.randomizeState(1000);
        System.out.println(testCube.toString());
    }
}
