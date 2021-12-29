package ru.vsu.cs.Person.Step;

import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;

import java.util.ArrayList;
import java.util.List;

public class BotStep implements Step
{

    @Override
    public Figure changeFigure(Cell[][] cells, List<Figure> listFigureCanMove) {
        List<Figure> canKill= new ArrayList<>();
        int randomStep;

        for(Figure f : listFigureCanMove)
        {
            if(f.getPossibleNextStep().size()!=0) {
                for (Coord c : f.getPossibleNextStep()) {
                    if (cells[c.getY()][c.getX()].getFigure() != null) {
                        canKill.add(f);
                    }
                }
            }
        }
        if(canKill.size()!=0) {
            System.out.println("Бить можно!");
            randomStep = (int) (Math.random() * (canKill.size()-1));
            return canKill.get(randomStep);
        }
        else {
            System.out.println("Бить нечего!");
            randomStep = (int) (Math.random() * (listFigureCanMove.size()-1));
            return listFigureCanMove.get(randomStep);
        }
    }

    @Override
    public Coord changeCoord(Cell[][] cells, Figure figure) {
        int randomStep;
        List<Coord> coordOpponentFigure= new ArrayList<>();
        for(Coord c : figure.getPossibleNextStep())
        {
            if(cells[c.getY()][c.getX()].getFigure()!=null)
            {
                coordOpponentFigure.add(c);
            }
        }
        if(coordOpponentFigure.size()!=0) {
            randomStep = (int) (Math.random() * (coordOpponentFigure.size()-1));
            return coordOpponentFigure.get(randomStep);
        }
        else {
            randomStep = (int) (Math.random() * (figure.getPossibleNextStep().size()-1));
            return figure.getPossibleNextStep().get(randomStep);
        }
    }
}
