package engine.util;

import engine.component.Camera;

public abstract class Particle
{
    public Vec3 position = new Vec3(0,0,0);
    public Vec3 cg = new Vec3(0,0,0);

    public void rotate(float xy, float xz, float yz)
    {
        rotate(new Vec3(xy,xz,yz));
    }
    public void rotate(Vec3 p,float xy, float xz, float yz)
    {
        rotate(p,new Vec3(xy,xz,yz));
    }
    public void move(float x,float y,float z)
    {
        move(new Vec3(x,y,z));
    }

    public abstract void onDraw(Camera camera);
    public abstract void rotate(Vec3 position, Vec3 angle);
    public abstract void rotate(Vec3 angle);
    public abstract void move(Vec3 p);
    public abstract Particle copy();

}
