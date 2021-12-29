package ru.vsu.cs.Person.game;

import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.Player.Player;
import ru.vsu.cs.Person.Step.StageStep;
import ru.vsu.cs.Person.UI.server.AppClient;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class SGame extends Game {
    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private PrintWriter out;
    private BufferedReader in;

    public SGame(Player player1, Player player2) throws IOException {
        super(player1, player2);

//        AppClient client = new AppClient("localhost", 8887);
//        socket = new Socket("localhost", 8887);
//        client.setGame(this);
//        client.start();
    }


    @Override
    public void makeMove(Figure figure, Coord coord) throws IOException {
        super.makeMove(figure, coord);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println(conversionDaskToString());
    }


    @Override
    public boolean checkWinner()  {
        boolean isEnd = getCurrentPlayer().getListFigure().size() == 0;

        if(isEnd) {
            getCurrentPlayer().endGame();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isEnd;
    }

    private String conversionDaskToString()
    {
        StringBuilder dask = new StringBuilder(64);
        String[] arrDask= new String[64];
        Cell[][] cells = getDask().getCells();
        int index=0;
        for(int i=0;i< cells.length;i++)
        {
            for(int k=0; k< cells.length;k++)
            {
                arrDask[index] = "#";
                index++;
            }
        }

        String whichPlayer="";
        List<Figure> figureList = getCurrentPlayer().getListFigure();
        if(getCurrentPlayer().isFirstPlayer())
            whichPlayer="1";
        else whichPlayer="2";

        for(Figure f: figureList)
        {
            index = f.getCoord().getY() * 8 + f.getCoord().getX();
            arrDask[index] = whichPlayer;
        }

        figureList = getUnCurrentPlayer().getListFigure();
        if(getUnCurrentPlayer().isFirstPlayer())
            whichPlayer="1";
        else whichPlayer="2";

        for(Figure f: figureList)
        {
            index = f.getCoord().getY() * 8 + f.getCoord().getX();
            arrDask[index] = whichPlayer;
        }


        for (String s : arrDask) {
            dask.append(s);
        }


        return dask.toString();
    }

    public void takeFigure()
    {
//        game.printBoard();
        System.out.print("Enter which figure(coord(<x y> через пробел просто))");
//                int nextInt = new Scanner(System.in).nextInt();
        String coord = new Scanner(System.in).nextLine();
        String resp = StageStep.MOVE_FIGURE.getName() + StageStep.SEPARATOR + coord;
        System.out.println("To server:"+resp);
        out.println(resp);
    }

    public void moveFigure(String coordFigure)
    {
        System.out.print("Enter which coord figure with "+ coordFigure + " step(coord(<x y> через пробел просто))");
        String coord = new Scanner(System.in).nextLine();
        String resp = StageStep.MOVE_FIGURE.getName() + StageStep.SEPARATOR + coord;
        System.out.println("To server:"+resp);
        out.println(resp);
    }

    @Override
    public void makeMove() {
        super.makeMove();
    }
}
