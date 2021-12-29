package ru.vsu.cs.Person.UI.Window;

import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.Player.Player;
import ru.vsu.cs.Person.Сomponents.Coord;

import java.awt.*;
import java.util.List;


public class Drawer
{
    private final int x;
    private final int y;
    private final int size;
    private Graphics2D g;

    public Drawer(int x, int y, int size, Graphics2D g) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.g = g;
    }

    public void drawBack()
    {
        drawPlayingArea();
        g.setColor(Color.decode("#663905"));
        g.setStroke(new BasicStroke(20));
        g.drawRect(x, y, size, size);
        int step= size/8;
        g.setColor(Color.BLACK);
        g.setFont(new Font("courier", Font.BOLD|Font.ITALIC, (int)(0.35*step) ));
        int curCoord=x+step/2;
        for(int i=0; i<8; i++)
        {
            g.drawString(Integer.toString(i), curCoord, y+10 );
            curCoord+=step;
        }

        curCoord=y+step/2;
        for(int i=0; i<8; i++)
        {
            g.drawString(Integer.toString(i), x-5, curCoord );
            curCoord+=step;
        }
    }

    public void drawActive(Coord coord)
    {
        int step=size/8;
        g.setColor(Color.decode("#c4000d"));
        g.drawRect(coord.getX()*step+x, coord.getY()*step+y, size, size);

    }

    private void drawPlayingArea()
    {
        int step =size/8;
        int curX=x;
        int curY=y;
        for(int k=0; k<8;k++) {
            for (int i = 0; i < 8; i++) {
                if((i+k) % 2 ==0)
                    g.setColor(Color.decode("#855014"));
                else g.setColor(Color.WHITE);

                g.fillRect(curX, curY, step, step);
                curX+=step;
            }
            curY+=step;
            curX=x;
        }
    }

    public void drawFigure(Player player)
    {
        int step=size / 8;

        List<Figure> figureList = player.getListFigure();
        if(player.isFirstPlayer())
            g.setColor(Color.white);
        else g.setColor(Color.BLACK);

        for(Figure figure : figureList)
        {
            figure.paint(g, figure.getCoord().getX()*step +x, figure.getCoord().getY()*step + y, step);
        }
    }

}
