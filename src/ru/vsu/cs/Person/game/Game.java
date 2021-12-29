package ru.vsu.cs.Person.game;

import ru.vsu.cs.Person.Inventory.Dask;
import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.Player.Player;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game
{
    private Player player1;
    private Player player2;
    private Dask dask = new Dask();
    private boolean currentPlayerIsFirst= true;

    public Game(Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        arrangeFigure();
    }

    public void changeCurrPlayer()
    {
        System.out.println("Поменял");

        getCurrentPlayer().endStep();
        currentPlayerIsFirst = !currentPlayerIsFirst;


        getCurrentPlayer().updatePossibleStep();
        getUnCurrentPlayer().updatePossibleStep();
        System.out.println("Your turn! " + getCurrentPlayer().getName());


    }

    public void printBoard()
    {
        Cell[][] cells = dask.getCells();
        System.out.print("     ");
        for (int i=0; i<cells[0].length;i++)
        {
            System.out.print(i+ "    ");
        }
        System.out.println();
        System.out.println();
        int i=0;
        for (Cell[] cell : cells) {
            System.out.print(i+"    ");
            for (Cell value : cell) {
                if (value.getFigure() != null && value.getFigure().getColor().equals(Color.WHITE)) {
                    System.out.print("&    ");
                } else if(value.getFigure() != null && value.getFigure().getColor().equals(Color.BLACK))
                    System.out.print("%    ");
                else System.out.print("#    ");
            }
            i++;
            System.out.println();
            System.out.println();
        }
    }

    public void makeMove(Figure figure, Coord coord) throws IOException {
        Player currPlayer = getCurrentPlayer();
        currPlayer.setActiveFigureForStep(figure);
        currPlayer.setActiveCoordForStep(coord);

        if(!initializationStep(currPlayer.getActiveFigureForStep(), currPlayer.getActiveCoordForStep()))
            currPlayer.setActiveFigureForStep(null);



    }
    public void makeMove()
    {
        Player currPlayer = getCurrentPlayer();

        createForFigureStep(currPlayer);

        currPlayer.doStep(dask.getCells());


        if(currPlayer.getActiveFigureForStep() != null) {
//            System.out.println("Делаю ход");
            if(!initializationStep(currPlayer.getActiveFigureForStep(), currPlayer.getActiveCoordForStep()))
                currPlayer.setActiveFigureForStep(null);
        }

        try {
            if (checkWinner()) {
                JOptionPane.showMessageDialog(null, getUnCurrentPlayer().getName() + " are winner!!!");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public Player getCurrentPlayer()
    {
        return currentPlayerIsFirst ? player1 : player2;
    }

    public Player getUnCurrentPlayer()
    {
        return currentPlayerIsFirst ? player2 : player1;
    }

//    public void newGame(Player player1, Player player2)
//    {
//        this.player1 = player1;
//        this.player2 = player2;
//        arrangeFigure();
//    }

    public void doMissis(Figure figure)
    {
        figure.setMissis(true);
    }


    public boolean initializationStep(Figure activeFigure, Coord coord)
    {
        boolean isRight=false; // правильная ли координата для определенной шашки, чтобы переместить
        List<Coord> listPossibleMove =activeFigure.getPossibleNextStep();
        for(Coord c : listPossibleMove)
        {
            if (c.getX() == coord.getX() && c.getY() == coord.getY()) {
                isRight = true;
                break;
            }
        }

        int whichFigureOpponent = getUnCurrentPlayer().whichFigure(coord);
        if(isRight && whichFigureOpponent ==-1)
        {
            moveFigure(coord, activeFigure);

            if(getCurrentPlayer().isFirstPlayer() && coord.getY()==7 && !activeFigure.isMissis())
                doMissis(activeFigure);
            else if(coord.getY()==0 && !activeFigure.isMissis())
                doMissis(activeFigure);


            if(activeFigure.isMissis()) {
                List<Coord>coordList = getListPossibleMove(activeFigure, false);
                activeFigure.setPossibleNextStep(coordList);
            }

            changeCurrPlayer();
            return false;
        }
        else if(isRight)
        {
            boolean isStepFromLeft = coord.getX() > activeFigure.getCoord().getX();
            boolean fromDown = activeFigure.getCoord().getY() > coord.getY();
            Coord coord1;
            if(isStepFromLeft)
            {
                coord1 = new Coord(coord.getX()+ 1,
                        coord.getY() + (fromDown? (-1) : (+1)));
            }
            else
            {
                coord1 = new Coord(coord.getX() - 1,
                        coord.getY() + (fromDown ? (-1) : (+1)));
            }

            dask.getCells()[activeFigure.getCoord().getY()][activeFigure.getCoord().getX()].setFigure(null);
            dask.getCells()[coord1.getY()][coord1.getX()].setFigure(activeFigure);
            dask.getCells()[coord.getY()][coord.getX()].setFigure(null);
            getUnCurrentPlayer().getListFigure().remove(whichFigureOpponent);
            activeFigure.setCoord(coord1);

            activeFigure.setPossibleNextStep(getListPossibleMove(activeFigure, false));

            if(getCurrentPlayer().isFirstPlayer() && coord1.getY() == 7 && !activeFigure.isMissis()) {
                doMissis(activeFigure);
                List<Coord>coordList = getListPossibleMove(activeFigure, false);
                activeFigure.setPossibleNextStep(coordList);
            }
            else if(!getCurrentPlayer().isFirstPlayer() &&coord1.getY()==0 && !activeFigure.isMissis()) {
                doMissis(activeFigure);
                List<Coord>coordList = getListPossibleMove(activeFigure, false);
                activeFigure.setPossibleNextStep(coordList);
            }



            if(activeFigure.getPossibleNextStep().size()==0 )
            {

                changeCurrPlayer();
//                activeFigure=null;
                return false;
            }



        }
        return true;
    }


//    public void makeStep()
//    {
////        Player currPlayer = getCurrentPlayer();
////        createForFigureStep(currPlayer);
////
////        if(currPlayer.getActiveFigureForStep() != null) {
//////            System.out.println("Делаю ход");
////            if(!initializationStep(currPlayer.getActiveFigureForStep(), currPlayer.getActiveCoordForStep()))
////                 currPlayer.setActiveFigureForStep(null);
////        }
//
//    }

    public void moveFigure(Coord coord, Figure activeFigure)
    {
        dask.getCells()[activeFigure.getCoord().getY()][activeFigure.getCoord().getX()].setFigure(null);
        dask.getCells()[coord.getY()][coord.getX()].setFigure(activeFigure);
        activeFigure.setCoord(coord);

        System.out.println("Передвинул");
    }

    public boolean checkStep(Coord coord, Figure figure)
    {
        boolean isStepFromLeft = coord.getX() > figure.getCoord().getX();
        Player unCurrent = getUnCurrentPlayer();
        int whichFigureOpponent = unCurrent.whichFigure(coord);
        if (getCurrentPlayer().whichFigure(coord)!=-1)
        {
            return false;
        }
        else if(figure.isMissis() && getUnCurrentPlayer().whichFigure(coord)==-1)
            return true;
        else if (isStepFromLeft)
        {
            boolean fromDown = figure.getCoord().getY() > coord.getY();
            Coord coord1 = new Coord(coord.getX() + 1,
                    coord.getY() + (fromDown ? (-1) : (+1)));
            Coord forMissis= new Coord(coord.getX() - 1,
                    coord.getY() + (fromDown ? (+1) : (-1)));

            if(coord1.getX() >=0 && coord1.getX() <=7 && coord1.getY()>=0 && coord1.getY() <=7) {
                return dask.isEmpty(coord1);
            }
        }
        else
        {
            boolean fromDown = figure.getCoord().getY() > coord.getY();
            Coord coord1 = new Coord(coord.getX() - 1,
                    coord.getY() + (fromDown ? (-1) : (+1)));
            Coord forMissis= new Coord(coord.getX() + 1,
                    coord.getY() + (fromDown ? (-1) : (+1)));
            if(coord1.getX() >=0 && coord1.getX() <=7 && coord1.getY()>=0 && coord1.getY() <=7) {

                return dask.isEmpty(coord1);
            }
        }
        return false;
    }

    public List<Coord> getListPossibleMove(Figure figure, boolean isFirstStep)
    {
        Coord coord= figure.getCoord();
        List<Coord> coordList = new ArrayList<>();

        if(!figure.isMissis())
        {
            coordList.add(new Coord(coord.getX()+1, coord.getY()+1));
            coordList.add(new Coord(coord.getX()+1, coord.getY()-1));
            coordList.add(new Coord(coord.getX()-1, coord.getY()+1));
            coordList.add(new Coord(coord.getX()-1, coord.getY()-1));
        }
        else
        {
            for(int i=coord.getX()+1, k=1;i<8;i++, k++)
            {
                Coord c = new Coord(coord.getX()+k, coord.getY()+k);
                coordList.add(c);
                if(!checkStep(c, figure))
                    break;
            }

            for(int i=coord.getX()+1, k=1;i<8;i++, k++)
            {
                Coord c = new Coord(coord.getX()+k, coord.getY()-k);
                coordList.add(c);
                if(!checkStep(c, figure))
                    break;
            }

            for(int i=coord.getX()-1, k=1;i>-1;i--, k++)
            {
                Coord c = new Coord(coord.getX()-k, coord.getY()+k);
                coordList.add(c);
                if(!checkStep(c, figure))
                    break;
            }

            for(int i=coord.getX()-1, k=1;i>-1;i--, k++)
            {
                Coord c = new Coord(coord.getX()-k, coord.getY()-k);
                coordList.add(c);
                if(!checkStep(c, figure))
                    break;

            }

        }


        int count = coordList.size();
        if(figure.getColor().equals(Color.WHITE) )
        {
            for(int i=0,  t=0; i<count;i++, t++)
            {
                Coord c=coordList.get(t);
                if(c.getX() >=0 && c.getX() <=7 && c.getY()>=0 && c.getY() <=7)
                {
                    if( dask.getFigure(c)!=null) {
                        if(!checkStep(c, figure))
                        {
                            coordList.remove(t);
                            t--;
                        }
                    }
                    else if((!isFirstStep || c.getY() < figure.getCoord().getY()) && !figure.isMissis() )
                    {
                        coordList.remove(t);
                        t--;
                    }
                    else if(figure.isMissis() && !isFirstStep)
                    {
                        coordList.remove(t);
                        t--;

                    }
                }
                else
                {
                    coordList.remove(t);
                    t--;
                }
            }
        }
        else
        {
            for (int i = 0, t = 0; i < count; i++, t++)
            {
                Coord c = coordList.get(t);
                if (c.getX() >= 0 && c.getX() <= 7 && c.getY()>=0 && c.getY() <=7)
                {
                    if(dask.getFigure(c)!=null)
                    {
                        if(!checkStep(c, figure))
                        {
                            coordList.remove(t);
                            t--;
                        }
                    }
                    else
                        if((!isFirstStep || c.getY() > figure.getCoord().getY()) && !figure.isMissis() )
                    {
                        coordList.remove(t);
                        t--;
                    }
                    else if(figure.isMissis() && !isFirstStep)
                    {
                        coordList.remove(t);
                        t--;
                    }
                }
                else
                {
                    coordList.remove(t);
                    t--;
                }
            }
        }
        return coordList;
    }


    public boolean checkWinner() throws IOException {
        boolean isEnd = getCurrentPlayer().getListFigure().size() == 0;

        if(isEnd)
            getCurrentPlayer().endGame();
        return isEnd;
    }



    private void arrangeFigure()
    {
        player1.arrangeFigure();
        player2.arrangeFigure();

        dask.addPlayerFigure(player1);
        dask.addPlayerFigure(player2);

    }

    public void createForFigureStep(Player player)
    {
        for(Figure figure : player.getListFigure())
        {
            figure.setPossibleNextStep(getListPossibleMove(figure, true));
        }
    }

    public Dask getDask() {
        return dask;
    }
}

