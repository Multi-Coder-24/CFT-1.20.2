package org.multicoder.cft.common.utility;

public class optionsUtils
{
    public static String optionToString(byte B){
        if(B == (byte) 0)
        {
            return "Pulse";
        } else if (B == (byte) 1)
        {
            return "Timed";
        } else if (B == (byte) 2)
        {
            return "Continuous Randomized";
        }
        return null;
    }
    public static String optionToString(boolean B)
    {
        if(B)
        {
            return "Randomized";
        }
        else{
            return "Sequential";
        }
    }
}
