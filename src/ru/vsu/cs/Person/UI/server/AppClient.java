package ru.vsu.cs.Person.UI.server;


import ru.vsu.cs.Person.Step.StageStep;
import ru.vsu.cs.Person.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AppClient {

    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public static void main(String[] args) throws IOException {
        AppClient client = new AppClient("localhost", 7777);
        client.start();
    }

    public AppClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException
    {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        String command;
        while (!socket.isClosed() && (command = in.readLine()) != null) {
            String[] parsed = command.split(StageStep.SEPARATOR);
            System.out.println("From server:"+command);
            if (StageStep.ENDGAME.getName().equals(parsed[0]))
                socket.close();

//            if(StageStep.TAKE_FIGURE.getName().equals(parsed[0]))
//            {
//                takeFigure();
//            }
//            else if(StageStep.MOVE_FIGURE.getName().equals(parsed[0]))
//            {
//                moveFigure(parsed[1]);
//            }
        }
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



}