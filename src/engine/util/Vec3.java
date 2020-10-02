package engine.util;

public class Vec3
{
    public float x,y,z;

    public Vec3()
    {
        x=0;
        y=0;
        z=0;
    }
    public Vec3(float x, float y, float z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public  Vec3(Vec3 v)
    {
        this.x=v.x;
        this.y=v.y;
        this.z=v.z;
    }
    public void set(Vec3 v)
    {
        this.x=v.x;
        this.y=v.y;
        this.z=v.z;
    }
    public Vec3 copy()
    {
        return new Vec3(x,y,z);
    }
    public Vec3 negative()
    {
        return new Vec3(-x,-y,-z);
    }

    public void setPolar(Vec3 polar)
    {
        float p= (float) (polar.x*Math.sin(polar.y));

         z= (float) (polar.x*Math.cos(polar.y));
         y= (float) (p*Math.sin(polar.z));
         x= (float) (p*Math.cos(polar.z));
    }
    public static Vec3 setPolar(float r, float theta, float phi)
    {
        float p= (float) (r*Math.sin(theta));

        float z= (float) (r*Math.cos(theta));
        float y= (float) (p*Math.sin(phi));
        float x= (float) (p*Math.cos(phi));

        return new Vec3(x,y,z);
    }

    public Vec3 getPolar()
    {
        float r= magnitude();
        float theta= (float) Math.acos(z/r);
        float phi= (float) Math.atan2(y,x);

        //System.out.println("R: "+r);
        return new Vec3(r,theta,phi);
    }

    public static Vec3 getPolar(Vec3 v)
    {
        float r= magnitude(v);
        float theta= (float) Math.acos(v.z/r);
        float phi= (float) Math.atan2(v.y,v.x);

        return new Vec3(r,theta,phi);
    }

    public float magnitude()
    {
        return magnitude(this);
    }
    public static float magnitude(Vec3 v)
    {
        return (float) Math.sqrt((v.x*v.x)+(v.y*v.y)+(v.z*v.z));
    }
    public float theta()
    {
        return theta(this);
    }
    public static float theta(Vec3 v)
    {
        float r= (float) Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
        return (float) Math.acos(v.z/r);
    }

    public float phi()
    {
        return phi(this);
    }
    public static float phi(Vec3 v)
    {
        return  (float) Math.atan2(v.y,v.x);
    }

    public Vec3 getCosineAngles()
    {
        return getCosineAngles(this);
    }
    public static Vec3 getCosineAngles(Vec3 v)
    {
        float r=magnitude(v);
        float a= (float) Math.acos(v.x/r);
        float b= (float) Math.acos(v.y/r);
        float c= (float) Math.acos(v.z/r);
        return new Vec3(a,b,c);
    }
    public Vec3 getCosineRatio()
    {
        return getCosineRatio(this);
    }
    public static Vec3 getCosineRatio(Vec3 v)
    {
        float r=magnitude(v);
        return new Vec3(v.x/r,v.y/r,v.z/r);
    }

    public Vec3 extend(float l)
    {
        l=l+magnitude();
        return extend(getCosineRatio(),l);
    }

    public static Vec3 extend(Vec3 ratios, float r)
    {
        return ratios.mul(r);
    }

    public float dXY()
    {
        return (float) Math.sqrt(x*x+y*y);
    }
    public float dXZ()
    {
        return (float) Math.sqrt(x*x+z*z);
    }
    public float dYZ()
    {
        return (float) Math.sqrt(z*z+y*y);
    }

    public float angleXtoY()
    {
        return (float) Math.atan2(y,x);
    }
    public float angleYtoX()
    {
        return (float) Math.atan2(x,y);
    }
    public float angleXtoZ()
    {
        return (float) Math.atan2(z,x);
    }
    public float angleZtoX()
    {
        return (float) Math.atan2(x,z);
    }
    public float angleYtoZ()
    {
        return (float) Math.atan2(z,y);
    }
    public float angleZtoY()
    {
        return (float) Math.atan2(y,z);
    }

    public Vec3 getAngles()
    {
        Vec3 v=copy();
        float xy=v.angleXtoY();
        v.rotateXtoY(-xy);
        float xz=v.angleXtoZ();
        v.rotateXtoZ(-xz);
        float yz=v.angleYtoZ();
        return new Vec3(xy,xz,yz);
    }
    public Vec3 setAngles(Vec3 a)
    {
        return setAngleYtoZ(a.z).setAngleXtoZ(a.y).setAngleXtoY(a.x);
    }

    public Vec3 getPlaneAngles()
    {
        return new Vec3(angleXtoY(),angleXtoZ(),angleYtoZ());
    }

    public Vec3 setAngleXtoY(float a)
    {
        float d=dXY();
        x= (float) (d*Math.cos(a));
        y= (float) (d*Math.sin(a));
        return this;
    }
    public Vec3 setAngleYtoX(float a)
    {
        float d=dXY();
        x= (float) (d*Math.sin(a));
        y= (float) (d*Math.cos(a));
        return this;
    }
    public Vec3 setAngleXtoZ(float a)
    {
        float d=dXZ();
        x= (float) (d*Math.cos(a));
        z= (float) (d*Math.sin(a));
        return this;
    }
    public Vec3 setAngleZtoX(float a)
    {
        float d=dXZ();
        x= (float) (d*Math.sin(a));
        z= (float) (d*Math.cos(a));
        return this;
    }
    public Vec3 setAngleYtoZ(float a)
    {
        float d=dYZ();
        y= (float) (d*Math.cos(a));
        z= (float) (d*Math.sin(a));
        return this;
    }
    public Vec3 setAngleZtoY(float a)
    {
        float d=dYZ();
        y= (float) (d*Math.sin(a));
        z= (float) (d*Math.cos(a));
        return this;
    }

    public static Vec3 setAngleXtoY(Vec3 v, float a)
    {
        float d=v.dXY();
        float x= (float) (d*Math.cos(a));
        float y= (float) (d*Math.sin(a));
        return new Vec3(x,y,v.z);
    }
    public static Vec3 setAngleYtoX(Vec3 v, float a)
    {
        float d=v.dXY();
        float x= (float) (d*Math.sin(a));
        float y= (float) (d*Math.cos(a));
        return new Vec3(x,y,v.z);
    }
    public static Vec3 setAngleXtoZ(Vec3 v, float a)
    {
        float d=v.dXZ();
        float x= (float) (d*Math.cos(a));
        float z= (float) (d*Math.sin(a));
        return new Vec3(x,v.y,z);
    }
    public static Vec3 setAngleZtoX(Vec3 v, float a)
    {
        float d=v.dXZ();
        float x= (float) (d*Math.sin(a));
        float z= (float) (d*Math.cos(a));
        return new Vec3(x,v.y,z);
    }
    public static Vec3 setAngleYtoZ(Vec3 v, float a)
    {
        float d=v.dYZ();
        float y= (float) (d*Math.cos(a));
        float z= (float) (d*Math.sin(a));
        return new Vec3(v.x,y,z);
    }
    public static Vec3 setAngleZtoY(Vec3 v, float a)
    {
        float d=v.dYZ();
        float y= (float) (d*Math.sin(a));
        float z= (float) (d*Math.cos(a));
        return new Vec3(v.x,y,z);
    }


    /////
//    public void setPlaneAnglesX(Vec3 v)
//    {
//        setPlaneAngleXY(v.x);
//        setPlaneAngleXZ(v.y);
//    }
//    public void setPlaneAnglesX(float xy, float xz)
//    {
//        setPlaneAngleXY(xy);
//        setPlaneAngleXZ(xz);
//    }
//    public void setPlaneAnglesY(Vec3 v)
//    {
//        setPlaneAngleXY((float) ((Math.PI/2) - v.x));
//        setPlaneAngleYZ(getPlaneAngleYZ() + v.z);
//    }
//    public void setPlaneAnglesZ(Vec3 v)
//    {
//        setPlaneAngleXZ((float) ((Math.PI/2) - v.y));
//        setPlaneAngleYZ(((float)Math.PI/2) - v.z);
//    }

    ////

    public Vec3 rotateXtoY(float x)
    {
        float a=angleXtoY()+x;
        setAngleXtoY(a);
        return this;
    }
    public Vec3 rotateYtoX(float y)
    {
        float a=angleYtoX()+y;
        setAngleYtoX(a);
        return this;
    }

    public Vec3 rotateXtoZ(float x)
    {
        float a=angleXtoZ()+x;
        setAngleXtoZ(a);
        return this;
    }
    public Vec3 rotateZtoX(float y)
    {
        float a=angleZtoX()+y;
        setAngleZtoX(a);
        return this;
    }
    public Vec3 rotateYtoZ(float x)
    {
        float a=angleYtoZ()+x;
        setAngleYtoZ(a);
        return this;
    }
    public Vec3 rotateZtoY(float y)
    {
        float a=angleZtoY()+y;
        setAngleZtoY(a);
        return this;
    }

    public Vec3 rotate(Vec3 a)
    {
        return setAngleYtoZ(angleYtoZ() + a.z).setAngleXtoZ(angleXtoZ() + a.y).setAngleXtoY(angleXtoY() + a.x);
    }
    public void rotateRespectWith(Vec3 p, Vec3 a)
    {
        Vec3 v=sub(p);
        set(v.rotate(a).add(p));
    }

    public void alignToX(Vec3 reference)
    {
        Vec3 r=reference.copy();
        float ref_xy=reference.angleXtoY();
        r.rotateXtoY(-ref_xy);
        rotateXtoY(-ref_xy);
        float ref_xz=r.angleXtoZ();
        rotateXtoZ(-ref_xz);
    }
    public void alignToX(float xy, float xz)
    {
        rotateXtoY(-xy);
        rotateXtoZ(-xz);
    }


    public void rotateAround(Vec3 axis, float a)
    {
        //Vec3 angles=axis.getPlaneAngles();
        //angles.z=0;
        //rotate(angles.negative());

        Vec3 v=axis.copy();
        float xy=v.angleXtoY();
        v.rotateXtoY(-xy);
        float xz=v.angleXtoZ();

        //alignToX(axis);

        rotateXtoY(-xy);
        rotateXtoZ(-xz);

        rotateYtoZ(a);

        rotateXtoZ(xz);
        rotateXtoY(xy);

        //rotateXtoY(axis.angleXtoY());
        //rotateXtoZ(axis.angleXtoZ());

        //setPlaneAngleYZ(getPlaneAngleYZ()+a);

        //rotate(angles);
    }

    public void moveTowards(Vec3 direction, float d)
    {
        Vec3 cr=direction.getCosineRatio().mul(d);
        //cr.log("direction");
        set(add(cr));
    }

    public float distance(Vec3 v)
    {
        return distance(this,v);
    }
    public static float distance(Vec3 a, Vec3 b)
    {
        return a.sub(b).magnitude();
    }

    public Vec3 angleDifference(Vec3 v)
    {
        return getAngleDifference(this,v);
    }
    public static Vec3 getAngleDifference(Vec3 a, Vec3 b)
    {
        return a.getPlaneAngles().sub(b.getPlaneAngles());
    }

    public float getCosine(Vec3 v)
    {
        return getCosine(this,v);
    }
    public static float getCosine(Vec3 a, Vec3 b)
    {
        /*Vec3 c=b.copy();
        c.alignToX(a);
        c.setAngleYtoZ(0);
        return c.angleXtoY();*/

        /*Vec3 ra=a.getCosineRatio();
        Vec3 rb=b.getCosineRatio();

        float d=distance(ra,rb);
        return (float) (Math.asin(d/2)*2);*/

        return a.dot(b)/(a.magnitude()*b.magnitude());
    }

    public static float getCosineSpecial(Vec3 a, Vec3 b, float min) {
        float r=a.dot(b)/(a.magnitude()*b.magnitude());
        if (r>0)
            r=r*(1-min)+min;

        return r;
    }

    public float getAngle(Vec3 v)
    {
        return getAngle(this,v);
    }
    public static float getAngle(Vec3 a, Vec3 b)
    {
        /*Vec3 c=b.copy();
        c.alignToX(a);
        c.setAngleYtoZ(0);
        return c.angleXtoY();*/
        return (float) Math.acos(a.dot(b)/(a.magnitude()*b.magnitude()));
    }

    public static Vec3 interpolate(Vec3 a, Vec3 b, float current, float total)
    {
        return a.sub(b).mul(current/total).add(b);
    }
    public Vec3 add(float x, float y, float z)
    {
        return new Vec3(this.x-x, this.y-y, this.z-z);
    }
    public Vec3 add(Vec3 v)
    {
        return add(this, v);
    }
    public static Vec3 add(Vec3 a, Vec3 b)
    {
        return new Vec3(a.x+b.x,a.y+b.y,a.z+b.z);
    }
    public Vec3 sub(float x, float y, float z)
    {
        return new Vec3(this.x-x,this.y-y,this.z-z);
    }
    public Vec3 sub(Vec3 v)
    {
        return sub(this, v);
    }
    public static Vec3 sub(Vec3 a, Vec3 b)
    {
        return new Vec3(a.x-b.x,a.y-b.y,a.z-b.z);
    }

    public Vec3 mul(float m)
    {
        return mul(this,m);
    }
    public static Vec3 mul(Vec3 v,float m)
    {
        return new Vec3(v.x*m,v.y*m,v.z*m);
    }
    public float dot(Vec3 v)
    {
        return dot(this,v);
    }
    public static float dot (Vec3 a, Vec3 b)
    {
        return a.x*b.x+a.y*b.y+a.z*b.z;
    }
    public Vec3 cross (Vec3 v)
    {
        return cross(this,v);
    }
    public static Vec3 cross (Vec3 a, Vec3 b)
    {
        float x= ((a.y*b.z) - (b.y*a.z));
        float y= ((a.x*b.z) - (b.x*a.z));
        float z= ((a.x*b.y) - (b.x*a.y));

        return new Vec3(x,y,z);
    }

    public void log()
    {
        System.out.println("x : "+x+" y : "+y+" z : "+z);
    }
    public void log(String d)
    {
        System.out.println(d+" - x : "+x+" y : "+y+" z : "+z);
    }

    public void logAngle(String d)
    {
        System.out.println(d+" : x :"+deg(x)+" y :"+deg(y)+" z :"+deg(z));
    }
    public static float deg(float rnd)
    {
        return (float) (rnd*180/Math.PI);
    }

    public Vec3 div(float i) {
        return new Vec3(x/i,y/i,z/i);
    }


    public static Vec3 copy(Vec3 v)
    {
        if (v!=null)
            return v.copy();
        return null;
    }
    public static Vec3[] copy(Vec3[] v)
    {
        Vec3[] cv=new Vec3[v.length];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=Vec3.copy(v[i]);
        }
        return cv;
    }
    public static Vec3[][] copy(Vec3[][] v)
    {
        Vec3[][] cv=new Vec3[v.length][];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=Vec3.copy(v[i]);
        }
        return cv;
    }
    public static Vec3[][][] copy(Vec3[][][] v)
    {
        Vec3[][][] cv=new Vec3[v.length][][];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=Vec3.copy(v[i]);
        }
        return cv;
    }
    public static int[] copy(int[] v)
    {
        int[] cv=new int[v.length];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=v[i];
        }
        return cv;
    }
    public static int[][] copy(int[][] v)
    {
        int[][] cv=new int[v.length][];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=Vec3.copy(v[i]);
        }
        return cv;
    }
    public static int[][][] copy(int[][][] v)
    {
        int[][][] cv=new int[v.length][][];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=Vec3.copy(v[i]);
        }
        return cv;
    }
    public static float[] copy(float[] v)
    {
        float[] cv=new float[v.length];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=v[i];
        }
        return cv;
    }
    public static float[][] copy(float[][] v)
    {
        float[][] cv=new float[v.length][];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=Vec3.copy(v[i]);
        }
        return cv;
    }
    public static float[][][] copy(float[][][] v)
    {
        float[][][] cv=new float[v.length][][];
        for (int i=0;i<v.length;i++)
        {
            cv[i]=Vec3.copy(v[i]);
        }
        return cv;
    }
}
