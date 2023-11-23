package org.multicoder.cft.common.utility;

public enum starShape
{
    small_ball,
    large_ball,
    creeper_face,
    burst,
    triangle,
    diamond,
    square,
    crown,
    pyramid,
    star;

    public String getName(){
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
            case star -> {return "STAR";}
        }
        return null;
    }
}
