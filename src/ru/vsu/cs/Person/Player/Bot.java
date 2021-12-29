package ru.vsu.cs.Person.Player;

import ru.vsu.cs.Person.Step.Step;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;
import ru.vsu.cs.Person.Inventory.Figure;

import java.util.ArrayList;
import java.util.List;

public class Bot extends Player {
    private List<Coord> allStepList= new ArrayList<>();
    private List<Figure> listFigureCanMove= new ArrayList<>();

    public Bot(String name, Step stepStrategy, boolean isFirstPlayer) {
        super(name, stepStrategy, isFirstPlayer);
    }

    @Override
    public void updatePossibleStep()
    {
        listFigureCanMove.clear();
        allStepList.clear();
        for (Figure figure : getListFigure()) {
            List<Coord> list = figure.getPossibleNextStep();
            if(list.size()!=0)
                listFigureCanMove.add(figure);
            allStepList.addAll(list);
        }
    }

    @Override
    public void doStep(Cell[][] cells) {
        updatePossibleStep();
        if(getActiveFigureForStep() == null)
            setActiveFigureForStep(getStepStrategy().changeFigure(cells, listFigureCanMove));

        setActiveCoordForStep(getStepStrategy().changeCoord(cells, getActiveFigureForStep()));
    }

    @Override
    public boolean isBot() {
        return true;
    }

//    @Override
//    public Coord changeCoord(Cell[][] cells, Figure figure)
//    {
//        int randomStep;
//        List<Coord> coordOpponentFigure= new ArrayList<>();
//        for(Coord c : figure.getPossibleNextStep())
//        {
//            if(cells[c.getY()][c.getX()].getFigure()!=null)
//            {
//                coordOpponentFigure.add(c);
//            }
//        }
//        if(coordOpponentFigure.size()!=0) {
//            randomStep = (int) (Math.random() * (coordOpponentFigure.size()-1));
//            return coordOpponentFigure.get(randomStep);
//        }
//        else {
//            randomStep = (int) (Math.random() * (figure.getPossibleNextStep().size()-1));
//            return figure.getPossibleNextStep().get(randomStep);
//        }
//
//
//
//    }
}
