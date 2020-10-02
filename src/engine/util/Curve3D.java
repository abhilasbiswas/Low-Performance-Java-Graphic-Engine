package engine.util;

import engine.component.Camera;
import engine.component.Screen;

public class Curve3D extends Particle {
    Vec3 a, b, c, d;

    public Curve3D(Vec3 x1, Vec3 y1, Vec3 x2, Vec3 y2)
    {
        a=x1.copy();
        b=y1.copy();
        c=x2.copy();
        d=y2.copy();

//        this.a=new Line(a.copy(),b);
//        this.b=new Line(b,c);
//        this.c=new Line(d,c);
//        this.d=new Line(d,a);
    }


    @Override
    public void onDraw(Camera camera)
    {
        Vec3 Ta=camera.transform(a).p;
        Vec3 Tb=camera.transform(b).p;
        Vec3 Tc=camera.transform(c).p;
        Vec3 Td=camera.transform(d).p;

        Vec3 sa,sb;

        float l1=Ta.sub(Tb).magnitude()*Screen.anti_aliasing;
        float l2=Tc.sub(Td).magnitude()*Screen.anti_aliasing;

        float l= Math.max(l1, l2);

        for (float i = 0; i <= l; i++)
        {
            sa=Vec3.interpolate(Ta,Tb,i,l);
            sb=Vec3.interpolate(Td,Tc,i,l);

            l1=sa.sub(sb).magnitude()*Screen.anti_aliasing;
            for (int j=0;j<=l1;j++)
                camera.canvas.point(Vec3.interpolate(sa,sb,j,l1));
        }
    }

    @Override
    public void rotate(Vec3 position, Vec3 angle) {

    }

    @Override
    public void rotate(Vec3 angle)
    {
        a.rotate(angle);
        a.rotate(angle);
        c.rotate(angle);
        c.rotate(angle);
    }

    @Override
    public void move(Vec3 p)
    {
        a.add(p);
        b.add(p);
        c.add(p);
        d.add(p);

    }

    @Override
    public Particle copy() {
        return new Curve3D(a,b,c,d);
    }
}
