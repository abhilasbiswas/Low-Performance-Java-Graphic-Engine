


import engine.component.Camera;
import engine.util.Vec3;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *  Note: Normally the ButtonPanel and DrawingArea would not be static classes.
 *  This was done for the convenience of posting the code in one class and to
 *  highlight the differences between the two approaches. All the differences
 *  are found in the DrawingArea class.
 */
public class Main
{
    public static void main(String[] args)
    {
        Vec3 p=new Vec3(0,1,-1);




        Vec3 x11=new Vec3(0,0,0).add(p);
        Vec3 x12=new Vec3(0,0,10).add(p);
        Vec3 x21=new Vec3(0,10,0).add(p);

        Vec3 c=new Vec3(200,100,-100);

        Vec3 n1=x12.sub(x11);
        Vec3 n2=x21.sub(x11);
        Vec3 cr=Vec3.cross(n2,n1);
        Vec3 ray=c.sub(x11);//.sub(camera.position);

//        System.out.println(Math.atan2(141,200)*180/Math.PI);

        float normal=Vec3.getAngle(ray,cr);
//        ray.log("ray");
//        cr.log("normal");
//        System.out.println("angle: "+normal);
//        Vec3 a=new Vec3(100,-100,0);
//        Vec3 b=new Vec3(100,0,0);
//
//        float normal=Vec3.getAngle(a,b);
//        //float normal= (float) Math.acos(a.dot(b)/(a.magnitude()*b.magnitude()));//

//        p=new Vec3(0,107,-49);
//        normal= Vec3.getCosine(p,new Vec3(200,100,-100).sub(p));



//        System.out.println(Math.cos(normal));
//        normal = (float) (normal* (180/ Math.PI));
//        System.out.println(normal);


        /*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });*/

        int color=0xff00ff00;
        int red=(color>>24)&0xff;
        int green=(color&0xff00)*3;

        green=(green/3)>>8;

        int blue=color&0xff;

        //System.out.println(red+" "+green+" "+blue+" "+color);



        Vec3 px=new Vec3(1,1,0).rotateYtoX((float) (Math.PI/2));
        Vec3 cx=new Vec3(0,0,1);
//
//        px.log("Point");
//        cx.log("Center");
//        Vec3.cross(px,cx).log("Cross");

        int x=10;
        float y=10.5f;

        float r=y/x;
        System.out.println(r);

    }

    private static void createAndShowGUI()
    {
        DrawingArea drawingArea = new DrawingArea();
        ButtonPanel buttonPanel = new ButtonPanel( drawingArea );

        JFrame frame = new JFrame("Draw On Image");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add(drawingArea);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }

    static class ButtonPanel extends JPanel implements ActionListener
    {
        private DrawingArea drawingArea;

        public ButtonPanel(DrawingArea drawingArea)
        {
            this.drawingArea = drawingArea;

            add( createButton("	", Color.BLACK) );
            add( createButton("	", Color.RED) );
            add( createButton("	", Color.GREEN) );
            add( createButton("	", Color.BLUE) );
            add( createButton("	", Color.ORANGE) );
            add( createButton("	", Color.YELLOW) );
            add( createButton("Clear Drawing", null) );
        }

        private JButton createButton(String text, Color background)
        {
            JButton button = new JButton( text );
            button.setBackground( background );
            button.addActionListener( this );

            return button;
        }

        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton)e.getSource();

            if ("Clear Drawing".equals(e.getActionCommand()))
                drawingArea.clear();
            else
                drawingArea.setForeground( button.getBackground() );
        }
    }

    static class DrawingArea extends JPanel
    {
        private final static int AREA_SIZE = 400;
        private BufferedImage image =
                new BufferedImage(AREA_SIZE, AREA_SIZE, BufferedImage.TYPE_INT_ARGB);
        private Rectangle shape;

        public DrawingArea()
        {
            setBackground(Color.WHITE);

            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
            addMouseMotionListener(ml);
        }

        @Override
        public Dimension getPreferredSize()
        {
            return isPreferredSizeSet() ?
                    super.getPreferredSize() : new Dimension(AREA_SIZE, AREA_SIZE);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            //  Custom code to support painting from the BufferedImage

            if (image != null)
            {
                g.drawImage(image, 0, 0, null);
            }

            //  Paint the Rectangle as the mouse is being dragged

            if (shape != null)
            {
                Graphics2D g2d = (Graphics2D)g;
                g2d.draw( shape );
            }
        }

        public void addRectangle(Rectangle rectangle, Color color)
        {
            //  Draw the Rectangle onto the BufferedImage

            Graphics2D g2d = (Graphics2D)image.getGraphics();
            g2d.setColor( color );
            g2d.draw( rectangle );
            repaint();
        }

        public void clear()
        {
            createEmptyImage();
            repaint();
        }

        private void createEmptyImage()
        {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D)image.getGraphics();
            g2d.setColor(Color.BLACK);
            g2d.drawString("Add a rectangle by doing mouse press, drag and release!", 40, 15);
        }

        class MyMouseListener extends MouseInputAdapter
        {
            private Point startPoint;

            public void mousePressed(MouseEvent e)
            {
                startPoint = e.getPoint();
                shape = new Rectangle();
            }

            public void mouseDragged(MouseEvent e)
            {
                int x = Math.min(startPoint.x, e.getX());
                int y = Math.min(startPoint.y, e.getY());
                int width = Math.abs(startPoint.x - e.getX());
                int height = Math.abs(startPoint.y - e.getY());

                shape.setBounds(x, y, width, height);
                repaint();
            }

            public void mouseReleased(MouseEvent e)
            {
                if (shape.width != 0 || shape.height != 0)
                {
                    addRectangle(shape, e.getComponent().getForeground());
                }

                shape = null;
            }
        }
    }
}














/*import engine.component.Camera;
import engine.util.Vec3;

public class Main extends Camera
{
    public static void main(String[] arg)
    {
        System.out.println("Running...");
        engine.Frame frame=new engine.Frame(new Main(),800,600);
    }

    Vec3 a=new Vec3(0,200,200);
    Vec3 b=new Vec3(0,-200,200);
    Vec3 c=new Vec3(0,-200,-200);
    Vec3 d=new Vec3(0,200,-200);
    @Override
    public void draw()
    {
        drawline(a,b);
        drawline(b,c);
        drawline(c,d);
        drawline(d,a);
//        drawPoint(a);
//        drawPoint(b);
//        drawPoint(c);
//        drawPoint(d);

    }
}
*/