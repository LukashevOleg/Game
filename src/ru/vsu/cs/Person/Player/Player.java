package ru.vsu.cs.Person.Player;

import ru.vsu.cs.Person.Step.Step;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;
import ru.vsu.cs.Person.Inventory.Figure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player
{
    private List<Figure> listFigure = new ArrayList<>();
    private String name;
    private boolean isFirstPlayer;
    private Step stepStrategy;

    private Figure activeFigureForStep = null;
    private Coord activeCoordForStep = null;





    public Player(String name, Step stepStrategy, boolean isFirstPlayer) {
        this.name = name;
        this.stepStrategy = stepStrategy;
        this.isFirstPlayer = isFirstPlayer;
    }


    public void setActiveCoordForStep(Coord activeCoordForStep) {
        this.activeCoordForStep = activeCoordForStep;
    }

    public Step getStepStrategy() {
        return stepStrategy;
    }

    public Figure getActiveFigureForStep() {
        return activeFigureForStep;
    }

    public Coord getActiveCoordForStep() {
        return activeCoordForStep;
    }

    public List<Figure> getListFigure() {
        return listFigure;
    }

    public int whichFigure(Coord coord)
    {
        int i=0;
        for (Figure figure : listFigure) {
            if (figure.getCoord().getX() == coord.getX() &&
                    figure.getCoord().getY() == coord.getY()) {
                return i;
            } else i++;
        }
        return -1;
    }

    public boolean isFirstPlayer()
    {
        return isFirstPlayer; }

    public String getName()
    { return name;
    }


    public void endStep()
    {
        stepStrategy.reset();
    }

    public void endGame()
    {
        stepStrategy.endgame();
    }

    public void setActiveFigureForStep(Figure activeFigureForStep) {
        this.activeFigureForStep = activeFigureForStep;
    }

    public void doStep(Cell[][] cells)
    {
        if(activeFigureForStep == null)
            activeFigureForStep = stepStrategy.changeFigure(cells, listFigure);

        activeCoordForStep = stepStrategy.changeCoord(cells, activeFigureForStep);
    }

    private void moveFigure(Figure figure, Coord coord)
    {
        figure.setCoord(coord);
    }


    public void arrangeFigure()
    {
        int x, y;
        Color color;
        if(isFirstPlayer)
        {
            x = 0;
            y=0;
            color = Color.WHITE;
        }
        else
        {
            x=1;
            y=7;
            color = Color.BLACK;
        }

        for (int i=0; i<12; i+=4)
        {
            for (int k = 0; k < 4; k++)
            {
                listFigure.add(new Figure(color, new Coord(x, y)));
                x+=2;

            }
            if(isFirstPlayer)
                y+=1;
            else y-=1;

            if(x%2 == 0)
            {
                x=1;
            } else x=0;
        }
    }

    public boolean isBot()
    {
        return false;
    }

    public void updatePossibleStep(){ }

    public Coord changeCoord(Cell[][] cells, Figure figure)
    {
        return null;
    }

    public Figure changeFigure(Cell[][] cells)
    {
        return null;
    }










}
