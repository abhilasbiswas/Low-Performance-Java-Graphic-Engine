package engine.util;

import engine.component.Camera;
import engine.component.Screen;

public class Line extends Particle {
    Vec3 start,end;

    public Line(Vec3 start, Vec3 end)
    {
        this.start=start;
        this.end=end;
        cg=start.add(end).div(2);
    }

    @Override
    public void onDraw(Camera camera)
    {
        Vec3 pa=camera.transform(start).p;
        Vec3 pb=camera.transform(end).p;
        if (pa==null||pb==null)
            return;
        float length=pa.sub(pb).magnitude()* Screen.anti_aliasing;

        for (int i=0;i<=length;i++)
        {
            camera.canvas.point(Vec3.interpolate(pa,pb,i,length));
        }
    }

    @Override
    public void rotate(Vec3 position, Vec3 angle) {
        start.rotateRespectWith(position,angle);
        end.rotateRespectWith(position,angle);
    }

    @Override
    public void rotate(Vec3 angle) {
        start.rotateRespectWith(cg.add(position),angle);
        end.rotateRespectWith(cg.add(position),angle);
    }

    @Override
    public void move(Vec3 p) {
        position=position.add(p);
        start=start.add(p);
        end=end.add(p);
    }

    @Override
    public Particle copy() {
        return new Line(start.copy(),end.copy());
    }

    public float length()
    {
        return end.sub(start).magnitude();
    }
    public Vec3 interpolate(float i, float d)
    {
        return Vec3.interpolate(start,end,i,d);
    }
}
