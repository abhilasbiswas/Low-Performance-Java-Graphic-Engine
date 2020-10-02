package engine.component;

import engine.util.Vec3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Screen extends JPanel
{
    public float[][] depth;
    public float[][] normal;
    public int[][][] pixels;

    public boolean alpha=false;


    public float width, height;
    int w,h;
    public float cx,cy;
    public static float anti_aliasing =2;

    float max_distance=3000;
    Camera camera;

    public BufferedImage canvas;
    Graphics g;

    float margin=0;

    public Screen(Camera camera,int width, int height)
    {
        this.camera=camera;
        camera.canvas=this;
        depth=new float[(int) (width*anti_aliasing)][(int) (height*anti_aliasing)];
        normal=new float[(int) (width*anti_aliasing)][(int) (height*anti_aliasing)];
        pixels=new int[(int) (width*anti_aliasing)][(int) (height*anti_aliasing)][4];

        this.width=width;
        this.height=height;
        camera.width=width;
        camera.height=height;

        cx=(float)width/2;
        cy=(float)height/2;

        camera.width_margin=cx+margin;
        camera.height_margin=cy+margin;

        setPreferredSize(new Dimension(width , height));
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g=canvas.getGraphics();
        g.setColor(Color.black);
        w= (int) (width* anti_aliasing);
        h= (int) (height* anti_aliasing);
        initMouse();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (canvas != null&&!drawing)
        {
            //System.out.println("Points: "+points);
            //drawToCanvas();
            g.drawImage(canvas, 0, 0, null);
        }
        else
        {
            //System.out.println("Invalid Canvas!");
        }

        /*System.out.println("Drawing Started...");
        g.drawRect(10, 10, 240, 240);
        //filled Rectangle with rounded corners.
        g.fillRoundRect(50, 50, 100, 100, 80, 80);
        camera.draw();
        //new Thread(camera::draw).start();*/
    }

    @Override
    public Dimension getPreferredSize()
    {
        return isPreferredSizeSet() ?
                super.getPreferredSize() : new Dimension((int)width, (int)height);
    }
    boolean drawing=false;
    public void clear()
    {
        depth=new float[(int) (width* anti_aliasing)][(int) (height* anti_aliasing)];
        normal=new float[(int) (width* anti_aliasing)][(int) (height* anti_aliasing)];
        pixels=new int[(int) (width*anti_aliasing)][(int) (height*anti_aliasing)][4];

        canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        g=canvas.getGraphics();
        g.setColor(Color.black);
    }

    int x,y;
    float p,xx,yy,xy,a;

    public void point(Vec3 v) {
        point(v,1);
    }

    public void point(Vec3 v,float orientation) {
        point(v,orientation,new int[]{0xff,0xff,0x00,0x00});
    }

    int points=0;
    public void point(Vec3 v,float orientation, int[] pixel)
    {
        try
        {
            if (v==null)
                return;
            points++;
            //float darkness=1f-((v.z)/max_distance);

            Vec3 vt=transform(v);
            x= (int) Math.floor(vt.x);
            y= (int) Math.floor(vt.y);

            if (x>=0&&y>=0&&x<w&&y<h)
            {
                if (depth[x][y]>vt.z||depth[x][y]==0)
                {
                    depth[x][y]=vt.z;

//                    if (x==53&&y==339)
//                        System.out.println("printed");

                    //float lf= (1f - (vt.z / max_distance))*orientation;//lighting factor based on normal and distance

                    normal[x][y]=orientation;

                    pixels[x][y]=pixel;

//                    pixels[x][y][0]=((float)pixel.getRed()/255f)*lf;
//                    pixels[x][y][1]=((float)pixel.getGreen()/255f)*lf;
//                    pixels[x][y][2]=((float)pixel.getBlue()/255f)*lf;
//                    pixels[x][y][3]=((float)pixel.getAlpha()/255f)*lf;
                }
            }

            //float a1 = ((vt.x+vt.y) -((int)vt.x+(int)vt.y))/2;
            //float a2 = vt.x-((int)vt.x);
            //float a3 = vt.y-((int)vt.y);
            //float a4 = 1-a1;
            //Color c1=new Color(darkness,darkness,darkness,1);
            //Color c2=new Color(darkness,darkness,darkness,1);
            //Color c3=new Color(darkness,darkness,darkness,1);
            //Color c4=new Color(darkness,darkness,darkness,a4);
            //point((int)vt.x,(int)vt.y,c1);
            //point((int)vt.x+1,(int)vt.y,c2);
            //point((int)vt.x,(int)vt.y+11,c3);
            //point((int)vt.x+1,(int)vt.y+1,c4);
            //g.drawOval((int) vt.x-2,(int)vt.y-2,4,4);

        }
        catch (Exception e){e.printStackTrace();}
    }

    public void drawToCanvas()
    {
        try
        {
            Color c;
            float depth,a;
            for (int i = 0; i < width-1; i++)
            {
                for (int j = 0; j < height-1; j++)
                {
                    //depth = depth(i,j);

                    c = pixel(i,j);//new Color(depth*0.5f, depth*0.9f, depth*0.5f, 1);
                    point(i, j, c);
                }
            }
        }
        catch (Exception e){e.printStackTrace();}
    }

    int pix;

    Color pixel(int x, int y)
    {
        float r=0,g=0,b=0, a=0,d = 0,n = 0;
        int xp,yp;
        for (int i = 0; i< anti_aliasing; i++)
        {
            for (int j = 0; j< anti_aliasing; j++)
            {


                xp=(int)(x* anti_aliasing)+i;
                yp=(int)(y* anti_aliasing) +i;

//                if (depth[xp][yp]==0)
//                {
//                    a+=0xff;
//                    r+=0xff0000;
//                    g+=0xff00;
//                    b+=0xff;
//
//                    n+=1;
//                    continue;
//                }

                d+=depth[xp][yp];///normal[xp][yp];
                n+=normal[xp][yp];
                //pix=pixels[xp][yp];


                if (depth[xp][yp]==0)
                {
                    a += 255;
                    r += 255;
                    g += 255;
                    b += 255;
                }
                else {
                    a += pixels[xp][yp][3];//(pix>>24)&0xff;
                    r += pixels[xp][yp][0];//pix&0xff0000;//pixels[xp][yp][0];
                    g += pixels[xp][yp][1];//pix&0xff00;//pixels[xp][yp][1];
                    b += pixels[xp][yp][2];//pix&0xff;//pixels[xp][yp][2];
                    //a+=1;//pixels[xp][yp][3];
                }

            }
        }
        d = d/(anti_aliasing * anti_aliasing);
        n = n/(anti_aliasing * anti_aliasing);
        d = 1f - (d / max_distance);
        d=d*n;
        d=(d<=0)?0:(d>=1?1:d);




//        return d;
        r = r/(anti_aliasing * anti_aliasing);
        g = g/(anti_aliasing * anti_aliasing);
        b = b/(anti_aliasing * anti_aliasing);
        a = a/(anti_aliasing * anti_aliasing);


        a=a/255f;
        r=r/255f;//(float)(((int)r)>>16)/255f;
        g=g/255f;//(float)(((int)g)>>8)/255f;
        b=b/255f;
        d=d==0?1:d;

//        if (a>0.1&&a<0.9)
//            System.out.println(a);

        return new Color(normalize(r*d),normalize(g*d),normalize(b*d),alpha?a:1);//normalize(a*d));
    }
    float normalize(float c)
    {
        return c>=1?1:(c<=0?0:c);
    }

    void point(int x, int y, Color color)
    {

            g.setColor(color);
            g.drawLine(x, y, x, y);
    }

    public void drawline(Vec3 a, Vec3 b)
    {
        Vec3 aa=transform(a);
        Vec3 bb=transform(b);
        //g=getGraphics();
        g.drawLine((int)aa.x,(int)aa.y,(int)bb.x,(int)bb.y);
    }

    public Vec3 transform(Vec3 v)
    {
        float x=(v.x+cx)* anti_aliasing;
        float y=(v.y+cy)* anti_aliasing;
        return new Vec3(x,y,v.z);
    }
    float mx,my,dx,dy;
    Vec3 camera_orientation,o;
    public boolean dragging=false;
    void initMouse()
    {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mx=e.getX();
                my=e.getY();
                camera_orientation=camera.getOrientation();
                //System.out.println("Mouse Pressed: x:"+mx+"  y:"+my);
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (drawing) {
                    dragging=false;
                    return;
                }

                dragging=true;
                dx=e.getX()-mx;
                dy=e.getY()-my;

                o=camera_orientation.copy();
                o.y+=dy*0.001;
                o.z+=dx*0.001;
                camera.setOrientation(o);

                //System.out.println("Mouse dragging x:"+dx+"  y:"+dy);
                dragging=false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println("X:"+e.getX()+" Y:"+e.getY());
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (drawing)
                    return;
                super.keyPressed(e);
                char c=e.getKeyChar();

                if (c=='a')
                {
                    camera.left();
                }
                else if (c=='d')
                {
                    camera.right();
                }
                else if (c=='w')
                {
                    camera.forward();
                }
                else if (c=='s')
                {

                    camera.backward();
                }
                else if (c=='q')
                {

                    camera.down();
                }
                else if (c=='e')
                {

                    camera.up();
                }
                //System.out.println("Key pressed "+e.getKeyChar());
            }
        });
        setFocusable(true);
    }
}
