package engine.util;

import engine.component.Camera;

public class Point extends Particle {
    Vec3 p;
    public Point(Vec3 p)
    {
        this.p=p;
    }
    @Override
    public void onDraw(Camera camera) {
        camera.drawPoint(p);
    }

    @Override
    public void rotate(Vec3 position, Vec3 angle) {
        p.rotateRespectWith(position,angle);
    }

    @Override
    public void rotate(Vec3 angle) {
        p.rotate(angle);
    }

    @Override
    public void move(Vec3 p) {
        this.p=p.add(p);
    }

    @Override
    public Particle copy() {
        return new Point(p.copy());
    }
}
