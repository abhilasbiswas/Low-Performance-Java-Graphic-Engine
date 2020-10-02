package engine.util;

import engine.component.Camera;
import engine.component.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Plane extends Particle
{
    public boolean wrap=false;
    Vec3[][] pixel;
    Vec3[][] normals1;
    Vec3[][] normals2;
    int width,height;
    //BufferedImage texture;
    int[][][] texture;
    float[] scale_x;
    float[] scale_y;
    int t_width;
    int t_height;
    float t_width_scale;
    float t_height_scale;

    @Override
    public Particle copy()
    {
        Plane plane=new Plane();
        plane.position=position.copy();
        plane.cg=cg.copy();
        plane.width=width;
        plane.height=height;
        plane.wrap=wrap;
        plane.t_width=t_width;
        plane.t_height=t_height;
        plane.t_height_scale=t_height_scale;
        plane.t_width_scale=t_width_scale;

        plane.pixel=Vec3.copy(pixel);
        plane.normals1=Vec3.copy(normals1);
        plane.normals2=Vec3.copy(normals2);
        plane.texture=Vec3.copy(texture);
        plane.scale_x=Vec3.copy(scale_x);
        plane.scale_y=Vec3.copy(scale_y);

        return plane;
    }

    public Plane()
    {

    }
    public Plane(int w, int h)
    {
        this.width=  w;
        this.height=  h;

        position=new Vec3(0,0,0);
        cg=new Vec3((float)width/2f,(float)height/2f,0);

        pixel=new Vec3[width][height];
        normals1=new Vec3[width][height];
        normals2=new Vec3[width][height];


        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                    pixel[i][j]=new Vec3(i,j,0);
            }
        }
        calculateNormals();
    }
    public Plane(int w, int h, PlaneFunction function)
    {
        this.width=  (w);
        this.height= (h);

        position=new Vec3(0,0,0);
        cg=new Vec3((float)width/2f,(float)height/2f,0);

        pixel=new Vec3[width][height];
        normals1=new Vec3[width][height];
        normals2=new Vec3[width][height];

        Vec3 p;
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                p=function.pixel(i,j);
                if (p != null)
                    pixel[i][j]=p;
            }
        }
        calculateNormals();
    }
    public Plane(BufferedImage image, BufferedImage texture_map, float depth_scale)
    {
        width=image.getHeight();
        height=image.getWidth();
        position=new Vec3(0,0,0);
        cg=new Vec3((float)width/2f,(float)height/2f,0);

        //this.texture=texture_map;
        t_width=texture_map.getHeight();
        t_height=texture_map.getWidth();


        t_width_scale=((float)t_width/(float)width);
        t_height_scale=((float)t_height/(float)height);

        pixel=new Vec3[width][height];
        normals1=new Vec3[width][height];
        normals2=new Vec3[width][height];

        Color color;
        float d = 0;

        float d1 = 0, d2 = 0,d3=0,d4=0;

        loadTexture(texture_map);

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                color=new Color(image.getRGB(j,i));
                if (((image.getRGB(j,i)>>24)&0xff)!=0) {
                    d3=d2;
                    d2=d1;
                    d1=((color.getGreen()+color.getRed()+color.getBlue())*depth_scale)/3f;
                    d=(d1+d2+d3)/3;
                        pixel[i][j] = new Vec3(i,j,d);//((color.getGreen()+color.getRed()+color.getBlue())*depth_scale)/3f);
                        //if (color.getRed()>10)
                        //pixel[i][j].log("p");
                }
            }
        }
        d1=0;
        d2=0;
        d3=0;
        d4=0;

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (pixel[j][i]!=null)
                {
                    d3=d2;
                    d2=d1;
                    d1=pixel[j][i].z;
                    d=(d1+d2+d3)/3;
                    pixel[j][i].z = d;
                    //if (color.getRed()>10)
                    //pixel[i][j].log("p");
                }
            }
        }

        initScales();
//        for (int i = 0; i < width; i++)
//        {
//            pixel[i][0] = new Vec3(i,0,0);
//            pixel[i][height-1] = new Vec3(i,height-1,0);
//
//        }
//        for (int j = 0; j < height; j++)
//        {
//            pixel[0][j] = new Vec3(0,j,0);
//            pixel[width-1][j] = new Vec3(width-1,j,0);
//        }

        calculateNormals();
    }

    public void loadTexture(BufferedImage image)
    {
        int w=image.getHeight();
        int h=image.getWidth();

        texture=new int[w][h][4];
        int color;

        for (int i=0;i<w;i++)
        {
            for (int j=0;j<h;j++)
            {
                color=image.getRGB((int)(j),(int)(i));
                texture[i][j][0]=(color>>16)&0xff;
                texture[i][j][1]=(color>>8)&0xff;
                texture[i][j][2]=color&0xff;
                texture[i][j][3]=(color>>24)&0xff;
            }
        }


    }
    public void initScales()
    {
        scale_x=new float[width];
        scale_y=new float[height];

        float l;

        for (int i=0;i<width;i++)
        {
            l=0;
            for (int j=1;j<height;j++)
            {
                l+=pixel[i][j-1].sub(pixel[i][j]).magnitude();
            }
            scale_x[i]=t_width/l;
        }
        for (int i=0;i<height;i++)
        {
            l=0;
            for (int j=1;j<width;j++)
            {
                l+=pixel[j-1][i].sub(pixel[j][i]).magnitude();
            }
            scale_y[i]=t_height/l;
        }

    }
    public void calculateNormals()
    {
        int w=width-1;
        int h=height-1;
        for (int x = 2; x < w; x++)
        {
            for (int y = 2; y < h; y++)
            {
                if (pixel[x][y] != null)
                {
                    normals1[x][y]=getNormal(pixel[x][y],pixel[x-1][y+1],pixel[x+1][y+1],pixel[x+1][y-1],pixel[x-1][y-1]);
                    normals2[x][y]=getNormal(pixel[x][y],pixel[x-1][y-1],pixel[x+1][y-1],pixel[x+1][y+1],pixel[x-1][y+1]);

                }
            }
        }
    }

    @Override
    public void onDraw(Camera camera)
    {
        for (int i = 2; i < width-2; i++)
        {
            for (int j = 2; j < height-2; j++)
            {
                if (pixel[i][j] != null) {
                    //pixel[i][j].log(i+"");
                    //camera.drawPoint(pixel[i][j]);
                    raster(i,j,camera);
                }
            }
        }
//        a.onDraw(camera);
//        b.onDraw(camera);
//        c.onDraw(camera);
//        d.onDraw(camera);
    }
    @Override
    public void rotate(Vec3 angle)
    {
        Vec3 center=cg.add(position);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixel[i][j] != null)
                    pixel[i][j].rotateRespectWith(center,angle);
            }
        }
    }
    @Override
    public void rotate(Vec3 position, Vec3 angle)
    {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixel[i][j] != null)
                    pixel[i][j].rotateRespectWith(position,angle);
            }
        }
    }
    @Override
    public void move(Vec3 p)
    {
        position=position.add(p);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixel[i][j] != null)
                    pixel[i][j] = pixel[i][j].add(p);
            }
        }
    }




    int[] color11,color12,color21,color22;
    Vec3 x11;
    Vec3 x12;
    Vec3 x21;
    Vec3 x22;
    Vec3 p11,p12,p21,p22;
    Camera.Transformation Tx11;
    Camera.Transformation Tx12;
    Camera.Transformation Tx21;
    Camera.Transformation Tx22;
    float[] cx,cy;

    public void raster(int x,int y,Camera camera) {
        p11 = pixel[x][y];

        if (pixel[x][y] == null)
            return;

        p12 = pixel[x + 1][y];
        p21 = pixel[x][y + 1];
        p22 = pixel[x + 1][y + 1];

        if (p12 == null || p21 == null || p22 == null)
            return;


        Tx11 = camera.transform(p11);

        if (Tx11 == null)
            return;

        Tx12 = camera.transform(p12);
        if (Tx12 == null)
            return;

        Tx21 = camera.transform(p21);
        if (Tx21 == null)
            return;

        Tx22 = camera.transform(p22);

        if (Tx22 == null)
            return;

        if (checkBoundary(Tx11.p, camera) && checkBoundary(Tx12.p, camera) && checkBoundary(Tx21.p, camera) && checkBoundary(Tx22.p, camera))
            return;

        x11 = Tx11.p;//,camera);
        x12 = Tx12.p;//,camera);
        x21 = Tx21.p;//,camera);
        x22 = Tx22.p;//,camera);

        Vec3 a, b;


        float a1 = getAngle(p11,/*p12,p21,*/camera, Tx11.l, x, y);
        if (a1 == -1)
            return;

        float a2 = getAngle(p12,/*p11,p22,*/camera, Tx12.l, x + 1, y);
        if (a2 == -1)
            return;

        float a3 = getAngle(p21,/*p22,p11,*/camera, Tx21.l, x, y + 1);
        if (a3 == -1)
            return;

        float a4 = getAngle(p22,/*p12,p21,*/camera, Tx22.l, x + 1, y + 1);
        if (a4 == -1)
            return;


        /*Vec3 p=pixel[x][y];

        Vec3 n1=pixel[x+1][y].sub(p);
        Vec3 n2=pixel[x][y+1].sub(p);

//        Vec3 xp=new Vec3(x11.x,x11.y,x11.z);
//        Vec3 px=new Vec3(x12.x,x12.y,x12.z);
//        Vec3 py=new Vec3(x21.x,x21.y,x21.z);
//
//
//        Vec3 n1=px.sub(xp);
//        Vec3 n2=py.sub(xp);

        Vec3 normal=Vec3.cross(n2,n1);


        Vec3 position=camera.position.copy();
        //position=position.add(new Vec3(0,-200,-300));
        Vec3 ray=position.sub(p);//.sub(camera.position);

//        float ang=Vec3.getAngle(cr,ray);
//        if (ang>Math.PI/2)
//            ang= (float) (ang-Math.PI/2);
//        else if (ang<-Math.PI/2)
//            ang= (float) (ang-Math.PI/2);
        float angle= Vec3.getCosineSpecial(normal,ray,0.2f);

        float angle2=Vec3.getCosineSpecial(ray.getCosineRatio().mul(x11.l),camera.screen_normal,0.2f);

        if (angle<0)
        {
            normal=Vec3.cross(n1,n2);
            angle= Vec3.getCosineSpecial(normal,ray,0.2f);
            //angle=Vec3.getCosineSpecial(normal.getCosineRatio().mul(x11.l),camera.screen_orientation,0.2f);

        }
        angle=angle2*angle;*/
//        if (x==100&&y==100) {
//            normal.log("normal");
//            p.log(x + " , " + y);
//            System.out.println(angle+"  "+angle2+"  "+x11.l+"  ");
//            ray.getCosineRatio().mul(x11.l).log("");
//            camera.screen_orientation.log();
//        }

        //angle=angle*angle*angle*angle;

//        float k=x;
//        Vec3 p= new Vec3(0,pixel[x][y].y,-pixel[x][y].z);
//        p.log(x+" , "+y);
//        Vec3 nor=new Vec3(100,0,0);
//
//        normal= Vec3.getCosine(nor,camera.position.sub(p));


        //normal= Vec3.getCosine(new Vec3(100,x-100,y-100),camera.position);

        //float normal= Vec3.getCosine(cr,ray);

        //normal=(normal>=0&&normal<=1)?normal:1;

        float l1 = planeLength(x11, x12);
        float l2 = planeLength(x21, x22);

        float l = Math.max(l1, l2);
        float ax, ay;

        float[] c1, c2;

        if (wrap)
        {
            t_width_scale = scale_y[y];
            t_height_scale = scale_x[x];
        }

        /*if (texture!=null)
        {
            try {
                int xx = (int) ((float) x * t_width_scale);
                int yy = (int) ((float) y * t_height_scale);

                color11 = texture[xx][yy];//texture.getRGB((int) (y * t_height_scale), (int) (x * t_width_scale));
                color12 = texture[(int) (((float) (x + 1)) * t_width_scale)][yy];
                color21 = texture[xx][(int) (((float) (y + 1)) * t_height_scale)];
                color22 = texture[(int) (((float) (x + 1)) * t_width_scale)][(int) (((float) (y + 1)) * t_height_scale)];
            }
            catch (Exception e)
            {
                color11=new int[]{0xff,0xff,0xff,0xff};
                color12=new int[]{0xff,0xff,0xff,0xff};
                color21=new int[]{0xff,0xff,0xff,0xff};
                color22=new int[]{0xff,0xff,0xff,0xff};
            }
        }
        else
        {
            color11=new int[]{0xff,0xff,0xff,0xff};
            color12=new int[]{0xff,0xff,0xff,0xff};
            color21=new int[]{0xff,0xff,0xff,0xff};
            color22=new int[]{0xff,0xff,0xff,0xff};
        }*/

        float x_start=x*t_width_scale;
        float x_end=x_start+t_width_scale;
        float y_start=y*t_height_scale;
        float y_end=y_start+t_height_scale;

        float xpos,ypos;

        past_x= (int) x_start;
        past_y= (int) y_start;
        for (float i=0;i<=l;i+=0.5f)
        {
            a=Vec3.interpolate(x11,x12,i,l);
            b=Vec3.interpolate(x21,x22,i,l);

            ax=InterPolator.interpolate(a2,a1,i,l);
            ay=InterPolator.interpolate(a4,a3,i,l);

//            c1=interPolate1(color12,color11,i,l);
//            c2=interPolate1(color22,color21,i,l);

            l1=planeLength(a,b);
            xpos=interpolate(x_end,x_start,i,l);

            for (float j=0;j<=l1;j+=0.5f)
            {
                ypos=interpolate(y_end,y_start,j,l1);

                camera.canvas.point(Vec3.interpolate(a,b,j,l1),InterPolator.interpolate(ay,ax,j,l1),/*(color11[0]<<16)|(color11[1]<<8)|(color11[2]));*/colorAt(xpos,ypos)/*interPolate(c2,c1,j,l1)*/);
            }
        }
    }
    int past_x,past_y;

    int[] colorAt(float x, float y)
    {
        try {
            int x1 = (int) x;
            int x2 = x1 + 1;
            float mx = x - x1;

            int y1 = (int) y;
            int y2 = y1 + 1;
            float my = y - y1;
            cx = interPolate1(texture[x1][y1], texture[x2][y1], mx, x2 - x1);
            cy = interPolate1(texture[x1][y2], texture[x2][y2], mx, x2 - x1);

            return interPolate(cx, cy, my, y2 - y1);
        }catch (Exception e){}
        return new int[]{0xff,0xff,0xff,0xff};
    }
    float interpolate(float a, float b, float current, float total)
    {
        return a+((b-a)*(current/total));
    }
    boolean checkBoundary(Vec3 v,Camera camera)
    {
        return  (v.x>camera.width_margin||v.x<-camera.width_margin||v.y>camera.height_margin||v.y<-camera.height_margin);
    }
    public Vec3 adjustBoundary(Vec3 v,Camera camera)
    {
//        if (v.x>=camera.width_margin)
//        {
//            v=v.mul(camera.width_margin/v.x);
//            //v.y*=0.8f; //v.y*(camera.width_margin/v.x);
//            //v.x*=0.8f; //camera.width_margin;
//
//        }
//        else if (v.x<-camera.width_margin) {
//            v.y= v.y*(-camera.width_margin/v.x);
//            v.x = -camera.width_margin;
//        }
//
//        if (v.y>camera.height_margin) {
//            v.x=v.x*(camera.height_margin/v.y);
//            v.y = camera.height_margin;
//        }
//        else if(v.y<-camera.height_margin) {
//            v.x=v.x*(-camera.height_margin/v.y);
//            v.y = -camera.height_margin;
//        }

        return v;
    }
    int[] color(int x, int y)
    {
//        int c=texture.getRGB((int) (y * t_height_scale), (int) (x * t_width_scale));
//        return new int[]{(c>>16)&0xff,(c>>8)&0xff,c&0xff,(c>>24)&0xff};
        return texture[(int) (x*t_width_scale)][(int) (y*t_height_scale)];
    }
    int[] interPolate(float[] a, float[] b, float current, float total)
    {
//        int rc= (int) (a[0]+((b[0]-a[0])*current/total));
//        int gc= (int) (a[1]+((b[1]-a[1])*current/total));
//        int bc= (int) (a[2]+((b[2]-a[2])*current/total));
//        int ac= (int) (a[3]+((b[3]-a[3])*current/total));

        return new int[]{(int) (a[0]+((b[0]-a[0])*current/total)), (int) (a[1]+((b[1]-a[1])*current/total)), (int) (a[2]+((b[2]-a[2])*current/total)), (int) (a[3]+((b[3]-a[3])*current/total))};//(rc<<16)|(gc<<8)|bc|(ac<<24);
    }
    float[] interPolate1(int[] a, int[] b, float current, float total)
    {
        float rc= (a[0]+((float)(b[0]-a[0])*current/total));
        float gc= (a[1]+((float)(b[1]-a[1])*current/total));
        float bc= (a[2]+((float)(b[2]-a[2])*current/total));
        float ac= (a[3]+((float)(b[3]-a[3])*current/total));

        return new float[]{rc,gc,bc,ac};
    }
    float[] interPolate2(float[] a, float[] b, float current, float total)
    {
        return new float[]{(a[0]+((b[0]-a[0])*current/total)), (a[1]+((b[1]-a[1])*current/total)), (a[2]+((b[2]-a[2])*current/total)), (a[3]+((b[3]-a[3])*current/total))};//(rc<<16)|(gc<<8)|bc|(ac<<24);
    }
    Vec3 getNormal(Vec3 p, Vec3 a, Vec3 b, Vec3 c, Vec3 d)
    {
        if (p==null||a==null||b==null||c==null||d==null)
            return null;

        a=a.sub(p);
        b=b.sub(p);
        c=c.sub(p);
        d=d.sub(p);

        Vec3 n1=Vec3.cross(a,b);
        Vec3 n2=Vec3.cross(b,c);
        Vec3 n3=Vec3.cross(c,d);
        Vec3 n4=Vec3.cross(d,a);

        return n1.add(n2).add(n3).add(n4).div(4f);
    }
    float getAngle(Vec3 a,/* Vec3 b, Vec3 c,*/Camera camera,float l,int x, int y)
    {

//        Vec3 n1=b.sub(a);
//        Vec3 n2=c.sub(a);
//
//        Vec3 normal=Vec3.cross(n2,n1);

        Vec3 normal=normals1[x][y];//getNormal(pixel[x][y],pixel[x-1][y+1],pixel[x+1][y+1],pixel[x+1][y-1],pixel[x-1][y-1]);

        if (normal==null)
            return -1;

        Vec3 position=camera.position.copy();
        Vec3 ray=position.sub(a);

        float angle= Vec3.getCosineSpecial(normal,ray,0.4f);

        float angle2=Vec3.getCosineSpecial(ray,camera.screen_normal,0.2f);

//        if (angle2<0.9f)
//            System.out.println(angle2);

        if (angle<0)
        {
            //normal=Vec3.cross(n1,n2);
            normal=normals2[x][y];//getNormal(pixel[x][y],pixel[x-1][y-1],pixel[x+1][y-1],pixel[x+1][y+1],pixel[x-1][y+1]);

            angle= Vec3.getCosineSpecial(normal,ray,0.4f);
        }
        angle=angle2*angle;
        //angle*=angle;
        return angle;
    }
    float planeLength(Vec3 a, Vec3 b)
    {
        Vec3 v=a.sub(b);
        //v.z=0;
        return (float) (Math.sqrt(v.x*v.x+v.y*v.y)*Screen.anti_aliasing);//v.magnitude()*Screen.anti_aliasing;
    }
}
