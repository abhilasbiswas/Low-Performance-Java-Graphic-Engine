package engine.component;

import engine.util.Particle;
import engine.util.Vec3;

import java.util.ArrayList;
import java.util.Arrays;

public class Object3D extends Particle
{
    ArrayList<Particle> particles;

    public Object3D(Particle... particles)
    {
        this.particles=new ArrayList<>();

        this.particles.addAll(Arrays.asList(particles));
        calculateCG();
    }

    void calculateCG()
    {
        Vec3 center = new Vec3(0,0,0);
        for (Particle p: particles)
        {
            center=center.add(p.cg);
        }
        cg=center.div(particles.size());
    }

    public Object3D bind(Particle... particles)
    {
        if (this.particles==null)
        this.particles=new ArrayList<>();
        this.particles.addAll(Arrays.asList(particles));
        calculateCG();
        return this;
    }

    public Object3D remove(Particle particle)
    {
        if (particles==null)
            this.particles=new ArrayList<>();

        particles.remove(particle);
        return this;
    }

    @Override
    public void onDraw(Camera camera) {
        for (Particle p: particles)
            p.onDraw(camera);
    }

    @Override
    public void rotate(Vec3 position, Vec3 angle) {
        for (Particle p: particles)
            p.rotate(position,angle);
    }

    @Override
    public void rotate(Vec3 angle) {
        for (Particle p: particles)
            p.rotate(cg.add(position),angle);
    }

    @Override
    public void move(Vec3 dp) {
        for (Particle p: particles)
            p.move(dp);
    }

    @Override
    public Particle copy() {

        Object3D object3D=new Object3D();
        object3D.particles=new ArrayList<>();
        for (Particle p: particles)
        {
            object3D.particles.add(p.copy());
        }
        object3D.position=position.copy();
        object3D.cg=cg.copy();

        return object3D;
    }
}
