package org.multicoder.cft.common.utility;

public enum StarShape
{
    small_ball,
    large_ball,
    creeper_face,
    burst,
    triangle,
    diamond,
    square,
    crown,
    pyramid;

    public String getRName(){
        switch (this){
            case small_ball ->{ return "SMALL_BALL";}
            case large_ball ->{ return "LARGE_BALL";}
            case burst -> {return "BURST";}
            case creeper_face -> {return "CREEPER";}
            case triangle -> {return "TRIANGLE";}
            case diamond -> {return "DIAMOND";}
            case square -> {return "SQUARE";}
            case crown ->{return "CROWN";}
            case pyramid ->{return "PYRAMID";}
        }
        return null;
    }
}
