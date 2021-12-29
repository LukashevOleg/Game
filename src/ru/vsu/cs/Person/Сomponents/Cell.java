package ru.vsu.cs.Person.Сomponents;

import ru.vsu.cs.Person.Inventory.Figure;

public class Cell
{
    private Coord coord;
    private Figure figure;
//    private boolean isEmptyl

    public Cell(Coord coord, Figure figure) {
        this.coord = coord;
        this.figure = figure;
    }


    public Coord getCoord() {
        return coord;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
