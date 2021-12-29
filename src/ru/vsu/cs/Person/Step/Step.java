package ru.vsu.cs.Person.Step;

import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;

import java.util.List;

public interface Step
{
    Figure changeFigure(Cell[][] cells, List<Figure> list);

    Coord changeCoord(Cell[][] cells, Figure figure);

    default void reset(){}

    default void endgame(){}
}
