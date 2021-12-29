package ru.vsu.cs.Person.Inventory;

import ru.vsu.cs.Person.Player.Player;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;

import java.util.List;

public class Dask
{
    private Cell[][] cells = new Cell[8][8];

    public boolean isEmpty(Coord coord)
    {
        return cells[coord.getY()][coord.getX()].getFigure() == null;
    }


    public Figure getFigure(Coord coord)
    {
        return cells[coord.getY()][coord.getX()].getFigure();
    }

    public Dask()
    {
        initializationCells();

    }

    public Cell[][] getCells() {
        return cells;
    }

    public void addPlayerFigure(Player player)
    {
        List<Figure> list = player.getListFigure();
        for (Figure figure : list) {
            cells[figure.getCoord().getY()][figure.getCoord().getX()].setFigure(figure);
        }
    }

    private void initializationCells()
    {
        for (int i=0; i<cells.length; i++)
        {
            for(int k=0; k< cells[i].length; k++)
            {
                cells[i][k]= new Cell(new Coord(k, i), null);
            }
        }
    }


}
