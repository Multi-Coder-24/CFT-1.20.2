package org.multicoder.cft.common.utility;

public class OptionsUtils
{
    public static String OptionToString(byte B){
        if(B == (byte) 0)
        {
            return "Pulse";
        } else if (B == (byte) 1)
        {
            return "Timed";
        }
        return null;
    }
    public static String OptionToString(boolean B)
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
