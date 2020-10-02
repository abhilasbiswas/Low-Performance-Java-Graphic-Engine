package engine.component;

import java.awt.*;

abstract public class Painter extends Canvas
{
    Graphics g;
    @Override
    public void paint(Graphics g)
    {
        System.out.println("Painting...");
        this.g=g;
        draw();
    }


    public void point(int x, int y,Color color)
    {
        g.setColor(color);
        g=getGraphics();
        g.drawLine(x,y,x,y);

    }
    abstract public void draw();
}
