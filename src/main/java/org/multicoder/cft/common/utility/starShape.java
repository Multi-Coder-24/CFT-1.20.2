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
    public static starShape getShape(int index){
        switch (index){
            case 0 ->{return small_ball;}
            case 1 ->{return large_ball;}
            case 2 ->{return burst;}
            case 3 ->{return creeper_face;}
            case 4 ->{return triangle;}
            case 5 ->{return diamond;}
            case 6 ->{return square;}
            case 7 ->{return crown;}
            case 8 ->{return pyramid;}
            case 9 ->{return star;}
        }
        return null;
    }
}
