package ru.vsu.cs.Person.Step;

import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class StepServer implements Step
{
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public StepServer(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot connect to client", ex);
        }
    }


    @Override
    public Figure changeFigure(Cell[][] cells, List<Figure> listFigure) {
        String answer = "";
        try {
            String command = StageStep.TAKE_FIGURE.getName()+ StageStep.SEPARATOR;
            System.out.println("To client: "+command);
            out.println(command);

            while ((answer = in.readLine()) == null) {
            }

            System.out.println("From client: "+answer);
            String[] answerParsed = answer.split(StageStep.SEPARATOR);
            if (StageStep.MOVE_FIGURE.getName().equals(answerParsed[0])) {
                System.out.println("Player's change figure: " + answerParsed[1]);
            } else {
                throw new IllegalArgumentException("Client response is not recognized: "+answer);
            }

            Coord coord;
            String[] arr = answerParsed[1].split(" ");
            coord = new Coord(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));


            for(Figure f : listFigure)
            {
                if(f.getCoord().getX() == coord.getX() && f.getCoord().getY() == coord.getY())
                    return f;
            }

        } catch (IOException ex) {
            throw new IllegalStateException("Cannot communicate with a client", ex);
        }
        return null;
    }


    @Override
    public void reset() {
        Step.super.reset();
        String command = StageStep.TAKE_FIGURE.getName();
        System.out.println("To client:"+command);
        out.println(command);
    }

    @Override
    public void endgame() {
        Step.super.endgame();
        try {
            String command = StageStep.ENDGAME.getName();
            System.out.println("To client:"+command);
            out.println(command);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Cannot close socket: "+e.getMessage());
            }
        }
    }

    @Override
    public Coord changeCoord(Cell[][] cells, Figure figure)
    {
        String answer = "";
        try {
            String command = StageStep.MOVE_FIGURE.getName()+ StageStep.SEPARATOR + figure.getCoord();
            System.out.println("To client from changeCoord: "+command);
            out.println(command);

            while ((answer = in.readLine()) == null) {
            }

            System.out.println("From client: "+answer);
            String[] answerParsed = answer.split(StageStep.SEPARATOR);
            if (StageStep.MOVE_FIGURE.getName().equals(answerParsed[0])) {
                System.out.println("Player's move figure on: " + answerParsed[1]);
            } else {
                throw new IllegalArgumentException("Client response is not recognized: "+answer);
            }

            String[] arr = answerParsed[1].split(" ");

            return new Coord(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));

        } catch (IOException ex) {
            throw new IllegalStateException("Cannot communicate with a client", ex);
        }
    }
}
