package engine.util;

public class InterPolator
{
    public static float interpolate(float start, float end, float current, float total)
    {
        return start+((end-start)*(current/total));
    }
}
