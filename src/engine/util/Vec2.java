package engine.util;

public class Vec2
{
    public float x, y;
    public Vec2()
    {
        x=0;
        y=0;
    }
    public Vec2(float x, float y)
    {
        this.x=x;
        this.y=y;
    }
    public Vec2(Vec2 v)
    {
        this.x=v.x;
        this.y=v.y;
    }
    public void set(Vec2 v)
    {
        x=v.x;
        y=v.y;
    }
    public Vec2 get()
    {
        return new Vec2(x,y);
    }

    public float magnitude()
    {
        return (float) Math.sqrt(x*x+y*y);
    }
    public float angle()
    {
        return (float) Math.atan2(y,x);
    }

    public Vec2 add(Vec2 v)
    {
        return add(this, v);
    }
    public static Vec2 add(Vec2 a, Vec2 b)
    {
        return new Vec2(a.x+b.x,a.y+b.y);
    }
    public Vec2 sub(Vec2 v)
    {
        return sub(this, v);
    }
    public static Vec2 sub(Vec2 a, Vec2 b)
    {
        return new Vec2(a.x-b.x,a.y-b.y);
    }
}
