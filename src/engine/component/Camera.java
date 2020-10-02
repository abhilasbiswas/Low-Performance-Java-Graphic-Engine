package engine.component;

import engine.util.Particle;
import engine.util.Vec3;

import java.util.ArrayList;

public class Camera
{
    public Screen canvas;

    public Vec3 position;
    public Vec3 screen_normal,screen_orientation,light;
    public float focus=600;

    public ArrayList<Particle> particles;

    public boolean DRAWING_3D=false;
    public Vec3 POSITION_2D=new Vec3(0,0,0);

    public float width, height, width_margin, height_margin;

    public Camera()
    {
        particles=new ArrayList<>();

        position=new Vec3(1200f, 800, 1000);
        screen_normal=new Vec3(277.79968f, 317.507f, 426.6341f);
        light=new Vec3(100, 500.507f, 26.6341f);


//        position=new Vec3(600f, 400, 500);
//        screen_normal=new Vec3(277.79968f, -317.507f, 426.6341f);

//        position=new Vec3(2676.6255f, 2377.6516f, 290.75235f);//Pyramid
//        screen_normal=new Vec3(409.06873f, 433.8726f, -66.46317f);
//        position=new Vec3(600,0,-800);
//        screen_normal=new Vec3(500,0,-100);

//        position=new Vec3(1100,600,400);//Mountain View
//        screen_normal=new Vec3(500,0,200);

//        position=new Vec3(1277.7441f, 678.6356f, 322.3225f);//Mountain View 2
//        screen_normal=new Vec3(590.2444f, -4.7219625f, 107.6537f);
        //screen_normal=new Vec3(580,116,-98);
        //focus=screen_normal.magnitude();
        screen_normal=screen_normal.getCosineRatio().mul(focus);
        //screen_normal=new Vec3(94,202,0);

        screen_orientation=screen_normal.getAngles();
    }

    public void setDrawing3D(float position_x, float position_y)
    {
        DRAWING_3D=true;
        POSITION_2D.x=position_x;
        POSITION_2D.y=-position_y;
    }

    public void see()
    {
        //System.out.println("Started...");
        canvas.drawing=true;
        canvas.clear();
        onDraw();
        canvas.drawToCanvas();
        canvas.repaint();
        //System.out.println("Done");
    }

    public void onDraw()
    {
        canvas.drawing=true;
        for(Particle p:particles)
            p.onDraw(this);
        canvas.drawing=false;
    }

    public void add(Particle particle)
    {
        particles.add(particle);
    }

    public void move(Vec3 dp)
    {
        position=position.add(dp);
        //position.log("Position");
    }

    Vec3 move=new Vec3(0,0,100);


    public void up()
    {
        position.z+=10;

        //position=position.add(screen_normal.cross(move).getCosineRatio().mul(10));
        System.out.println("position=new Vec3("+position.x+"f, "+position.y+"f, "+position.z+"f);");
        System.out.println("screen_normal=new Vec3("+screen_normal.x+"f, "+screen_normal.y+"f, "+screen_normal.z+"f);");

    }
    public void down()
    {

        position.z-=10;

        //position=position.add(screen_normal.cross(move).getCosineRatio().mul(10));
        System.out.println("position=new Vec3("+position.x+"f, "+position.y+"f, "+position.z+"f);");
        System.out.println("screen_normal=new Vec3("+screen_normal.x+"f, "+screen_normal.y+"f, "+screen_normal.z+"f);");

    }

    public void left()
    {
        Vec3 sn=screen_normal.copy();
        sn.z=0;
        sn.rotateXtoY((float) (-Math.PI/2));
        sn=sn.getCosineRatio().mul(10);
        position=position.add(sn);

        //position=position.add(screen_normal.cross(move).getCosineRatio().mul(10));
        System.out.println("position=new Vec3("+position.x+"f, "+position.y+"f, "+position.z+"f);");
        System.out.println("screen_normal=new Vec3("+screen_normal.x+"f, "+screen_normal.y+"f, "+screen_normal.z+"f);");

    }
    public void right()
    {
        Vec3 sn=screen_normal.copy();
        sn.z=0;
        sn.rotateXtoY((float) (Math.PI/2));
        sn=sn.getCosineRatio().mul(10);
        position=position.add(sn);

        //position=position.add(move.cross(screen_normal).getCosineRatio().mul(10));
        System.out.println("position=new Vec3("+position.x+"f, "+position.y+"f, "+position.z+"f);");
        System.out.println("screen_normal=new Vec3("+screen_normal.x+"f, "+screen_normal.y+"f, "+screen_normal.z+"f);");

    }
    public void forward()
    {
        position.moveTowards(screen_normal,-10);
        System.out.println("position=new Vec3("+position.x+"f, "+position.y+"f, "+position.z+"f);");
        System.out.println("screen_normal=new Vec3("+screen_normal.x+"f, "+screen_normal.y+"f, "+screen_normal.z+"f);");

        //position.log("Position");
    }
    public void backward()
    {
        position.moveTowards(screen_normal,10);

        System.out.println("position=new Vec3("+position.x+"f, "+position.y+"f, "+position.z+"f);");
        System.out.println("screen_normal=new Vec3("+screen_normal.x+"f, "+screen_normal.y+"f, "+screen_normal.z+"f);");

//        position.log("position");
//        screen_normal.log("Normal");
    }



    public void setOrientation(Vec3 polar)
    {
        screen_normal.setPolar(polar);
        screen_orientation=screen_normal.getAngles();
        System.out.println("position=new Vec3("+position.x+"f, "+position.y+"f, "+position.z+"f);");
        System.out.println("screen_normal=new Vec3("+screen_normal.x+"f, "+screen_normal.y+"f, "+screen_normal.z+"f);");

    }
    public Vec3 getOrientation()
    {
        return screen_normal.getPolar();
    }
    public void rotate(Vec3 a)
    {

        Vec3 o=screen_normal.getPolar();
        o.y+=a.y;
        o.x+=a.x;
        screen_normal.setPolar(screen_orientation);

        screen_orientation=screen_normal.getAngles();
        //Vec3 ang=screen_normal.getPlaneAngles();
        //ang.z=0;
        //screen_normal.rotateXtoY(a.x);//setPlaneAngleXY(screen_normal.getPlaneAngleXY()+a.x);
        //screen_orientation=screen_normal.getAngles();
        //screen_normal.magnitude();
        //screen_normal.alignToX(screen_normal);
        //screen_normal.rotate(a);
        //screen_normal.rotate(ang);
        //screen_normal.log("Rotation "+a.x);
    }

    public void drawline(Vec3 a, Vec3 b)
    {
        //a.log("pa");
        //b.log("pb");
        Vec3 pa=transform(a).p;
        Vec3 pb=transform(b).p;
        //a.log("a");
        //b.log("b");
        if (pa==null||pb==null)
            return;
        //canvas.drawline(pa,pb);

        //a.log("a");
        //b.log("b");

        float length=pa.sub(pb).magnitude()* Screen.anti_aliasing;

        for (int i=0;i<=length;i++)
        {
            canvas.point(Vec3.interpolate(pa,pb,i,length));
        }
    }
    public void drawPoint(Vec3 p)
    {
        if (p!=null)
        canvas.point(transform(p).p);
    }


    public Transformation transform(Vec3 p)
    {
        if (DRAWING_3D)
        {
            return transform2D(p);
        }
        else
        {
            return transform3D(p);
        }
    }

    public Vec3 in3D(Vec3 position, Vec3 p)
    {
        float r=(position.z/(position.z-p.z));
        Vec3 px=p.copy();
        px.x-=position.x;
        px.y-=position.y;

        float x=((px.y*r)+position.y);//position.y+((p.y-position.y)*r);
        float y=((px.x*r)+position.x);//position.x+((p.x-position.x)*r);
        return new Vec3(x,y,0).add(POSITION_2D);
    }

    public Transformation transform2D(Vec3 p)
    {
        Vec3 ray=position.sub(p);
        float z=ray.magnitude();

        if (position.z<p.z)
            return null;



        /*float r=(position.z/(position.z-p.z));
        Vec3 px=p.copy();
        px.x-=position.x;
        px.y-=position.y;

        float x=px.y*r;//position.y+((p.y-position.y)*r);
        float y=px.x*r;//position.x+((p.x-position.x)*r);
        Vec3 projection=new Vec3(x,y,z);
        projection=projection.add(new Vec3(0,0,0));*/

        Vec3 projection=in3D(position,p);
        projection.z=z;

        ray.alignToX(screen_orientation.x,screen_orientation.y);
        //ray.log("Ray");
        Vec3 direction=ray.getCosineRatio();

        ray.setAngleYtoZ(0);
        if (ray.x<10)
        {
            return null;
        }
        float project_distance = focus/(ray.x/ray.dXY());
        Vec3 screen_pos =  direction.mul(project_distance);
        //projection.l=screen_pos.magnitude();
        return new Transformation(projection,screen_pos.magnitude());
    }

    public Transformation transform3D(Vec3 p)
    {
        //p.log("P");
        Vec3 ray=position.sub(p);
        //Vec3 a=ray.getPlaneAngles();
        //Vec3 b=screen_normal.getPlaneAngles();

        //a.logAngle("Ray");
        //b.logAngle("Normal");

        //Vec3 c=ray.copy();
        //c.alignToX(screen_normal);
        //c.log("Point");
        //screen_normal.log("Normal");

        //ray.log("Ray");
        float z=ray.magnitude();

        ///
        ray.alignToX(screen_orientation.x,screen_orientation.y);
        //ray.log("Ray");
        Vec3 direction=ray.getCosineRatio();

        ray.setAngleYtoZ(0);

//        float angle=ray.angleXtoY();
//
//        if (angle>Math.PI/2||ray.x<0)
//        {
//            return null;
//        }

        if (ray.x<10)
        {
            return null;
        }

        /*Vec3 ra=a.getCosineRatio();
        Vec3 rb=b.getCosineRatio();

        float d=distance(ra,rb);
        return (float) (Math.asin(d/2)*2);*/
        //return c.angleXtoY();
        ///

        //float angle=screen_normal.getAngle(ray);
        //System.out.println("Angle:"+deg(angle));
        float project_distance = focus/(ray.x/ray.dXY());//focus/(float)Math.cos(angle);
        //System.out.println("Projection Distance: "+project_distance);
        Vec3 screen_pos =  direction.mul(project_distance);




        //screen_pos.log("Screen Position");
        //screen_normal.log("Screen Normal");
        //screen_pos.alignToX(screen_normal);
        //screen_pos.log("Aligned");
        //p.log();
//        Vec3 r=position.sub(p);
//        r.log("Distance");
//        float z = r.magnitude();
//        //d(z+" "+r.x+" "+r.y);
//        float d = screen_normal.dXY();
//        d(d);
//        float phi=r.phi()-screen_normal.phi();
//        float y = (float) (d*Math.tan(phi));
//        float theta=r.theta()-screen_normal.theta();
//        float x = (float) (r.magnitude()*Math.tan(theta));
//
//        Vec3 v=new Vec3(x,y,z);

        //v.log();
        //p=new Vec3(-screen_pos.y,screen_pos.z,z);
        //p.l=screen_pos.magnitude();
        //p.log("P-out");
        return new Transformation(new Vec3(-screen_pos.y,screen_pos.z,z),screen_pos.magnitude());
    }

    public static float deg(float rad)
    {
        return (float) (rad*180/Math.PI);
    }
    public void d(Object s)
    {
        System.out.println(s);
    }

    public static class Transformation
    {
        public Vec3 p;
        public float l;
        public Transformation(Vec3 p, float l)
        {
            this.p=p;
            this.l=l;
        }
    }
}
