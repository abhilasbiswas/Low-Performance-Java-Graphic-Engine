package engine;

import engine.component.Camera;
import engine.component.Object3D;
import engine.component.Screen;
import engine.layouts.GUI;
import engine.util.Curve3D;
import engine.util.Particle;
import engine.util.Plane;
import engine.util.Vec3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainView extends Camera {
    Screen screen;
    JFrame frame;
    GUI gui;

    int width;
    int height;
    String image="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\depth.png";
    String texture="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\texture.png";

    String display="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\display.png";
    String display_depth="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\display_depth.png";
    String keyboard="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\keyboard.png";
    String keyboard_depth="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\keyboard_depth.png";
    String scr="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\screen.png";
    String scr_depth="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\screen_depth.png";
    String bg="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\bg.png";
    String bg_texture="D:\\_Dev\\Project\\Java\\DirectVision\\src\\engine\\bg_texture.png";

    public static void main(String[] args)
    {
        System.out.println(Runtime.getRuntime().totalMemory());
        new MainView(1900,1080);
    }

    public MainView(int x, int y)
    {
        width=x;
        height=y;
        setDrawing3D(-100,-100);
        initFrame();
        draw();
        see();
        //cycle();
    }

    public void initFrame()
    {
        frame=new JFrame("DirectVision");
        frame.setSize(width,height);
        screen= new Screen(this,width, height);
        gui = new GUI(this);

        frame.getContentPane().add(screen);
        //frame.getContentPane().add(gui, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo( null );
        frame.pack();
        frame.setVisible(true);
    }

    Vec3 a=new Vec3(200,200,100);
    Vec3 b=new Vec3(-200,200,-100);
    Vec3 c=new Vec3(-200,-200,100);
    Vec3 d=new Vec3(200,-200,-100);

    Plane p1,p2,p3;
    Curve3D cureve;

    Plane depth_map1,depth_map2,depth_map3,depth_map4;

    public void draw()
    {
        cureve=new Curve3D(a,b,c,d);
        //add(cureve);
        //p2=new Plane(a,b,c,d);
        //p1.rotate(new Vec3((float) (Math.PI/2),0,0));

        //p1=new Plane(200,200);
        File d1=new File(display);
        File d2=new File(display_depth);
        File k1=new File(keyboard);
        File k2=new File(keyboard_depth);
        File s1=new File(bg);
        File s2=new File(bg_texture);

        try {
            p2=new Plane(ImageIO.read(d2),ImageIO.read(d1),0.01f);//new Plane(200,200);
            Plane bg=new Plane(ImageIO.read(s1),ImageIO.read(s2),0.01f);//new Plane(200,200);
            bg.move(-912,-1360,-100);
            //add(bg);
            Particle p4=p2.copy();

            p4.rotate(0, (float) (Math.PI),0);
            p2.move(0,0,95f);
            Object3D dice=new Object3D(p2,p4);

            p4=dice.copy();

            p4.rotate(0,(float) (Math.PI/2),0);
            p4.move(47.5f,0,47.5f);
            Particle p5=p4.copy();
            p5.rotate((float) (Math.PI/2),0,0);

            dice=new Object3D(p4,dice,p5);

            Particle c1=dice.copy();
            Particle c2=dice.copy();
            Particle c3=dice.copy();

            c1.move(100,0,0);
            c3.move(-100,0,0);
            dice=new Object3D(c1,c2,c3);

            c1=dice.copy();
            c2=dice.copy();
            c3=dice.copy();
            c1.move(0,100,0);
            c3.move(0,-100,0);

            dice=new Object3D(c1,c2,c3);

            c1=dice.copy();
            c2=dice.copy();
            c3=dice.copy();
            c1.move(0,0,100);
            c3.move(0,0,-100);

            dice=new Object3D(c1,c2,c3);

            add(dice);

            //p3=new Plane(ImageIO.read(k2),ImageIO.read(k1),0.1f);//new Plane(200,200);
            //p1=new Plane(ImageIO.read(s2),ImageIO.read(s1),0.1f);//new Plane(200,200);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //p1.rotate(new Vec3(0,0,-(float) (Math.PI/2)));
        //p2.rotate(new Vec3(0,(float) (Math.PI/2),0));
        //p3.rotate(new Vec3((float) (Math.PI/2),0,0));
        //p3.move(new Vec3(200,0,0));
        //p1.move(new Vec3(-630,-760,0));
        //add(p1);

        //add(p3);
        try
        {
            File file=new File(image);
            File file2=new File(texture);

            BufferedImage depth=ImageIO.read(file);
            //add(new Plane(depth,ImageIO.read(file2),1f));

//            depth_map1=new Plane(depth,ImageIO.read(file2),0.01f);
//            depth_map2=new Plane(depth,ImageIO.read(file2),0.01f);
//            depth_map3=new Plane(depth,ImageIO.read(file2),0.01f);
//            depth_map4=new Plane(depth,ImageIO.read(file2),0.01f);



            //depth_map1.rotate(new Vec3(0,(float) (Math.PI/3),0));
            //depth_map1.move(new Vec3(100,250,0));
            //depth_map2.rotate(new Vec3(0,(float) (-Math.PI/3),0));


            //depth_map.move(new Vec3(0,-914,0));

            //add(depth_map1);
            //add(depth_map2);
            //add(depth_map3);
            //add(depth_map4);

        }
        catch (Exception e){e.printStackTrace();}

        //drawline(a,b);
        //drawline(b,c);
        //drawline(c,d);
        //drawline(d,a);
        //drawPoint(a);
    }

    Vec3 center=new Vec3(0,0,100);

    public void cycle()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long t1=System.nanoTime(),t2;
                float dt;
                float da=1f;

                while (true)
                {
                    t2=System.nanoTime();
                    dt=((float)t2-(float)t1)/1000000000;
                    t1=t2;
                    float a=da*dt;
                    //p1.rotate(new Vec3(a,a,a));
                    //p2.rotate(new Vec3(a,a,a));

//                    a.rotateAround(center,da*dt);
//                    b.rotateAround(center,da*dt);
//                    c.rotateAround(center,da*dt);
//                    d.rotateAround(center,da*dt);
                    see();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
