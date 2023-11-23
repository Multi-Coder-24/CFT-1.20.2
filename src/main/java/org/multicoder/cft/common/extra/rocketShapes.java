package org.multicoder.cft.common.extra;

import net.minecraft.client.particle.FireworkParticles;

public class rocketShapes
{

    /***
     * Creates the shape particles.
     */
    public static void CreateTriangle(FireworkParticles.Starter starter, boolean b, boolean b1, int[] ints, int[] ints1)
    {
        starter.createParticleShape(0.2d,new double[][]{{-2,-2},{0,2},{2,-2}},ints,ints1,b,b1,true);
    }

    /***
     * Creates the shape particles.
     */
    public static void CreatePyramid(FireworkParticles.Starter starter, boolean b, boolean b1, int[] ints, int[] ints1)
    {
        starter.createParticleShape(0.2d,new double[][]{{-2,-2},{0,2},{2,-2}},ints,ints1,b,b1,false);
    }

    /***
     * Creates the shape particles.
     */
    public static void CreateDiamond(FireworkParticles.Starter starter, boolean b, boolean b1, int[] ints, int[] ints1)
    {
        starter.createParticleShape(0.2d,new double[][]{{0,-2},{2,0},{0,2},{-2,0}},ints,ints1,b,b1,true);
    }

    /***
     * Creates the shape particles.
     */
    public static void CreateSquare(FireworkParticles.Starter starter, boolean b, boolean b1, int[] ints, int[] ints1)
    {
        starter.createParticleShape(0.2d,new double[][]{{-2,-2},{2,-2},{2,2},{-2,2}},ints,ints1,b,b1,true);
    }

    /***
     * Creates the shape particles.
     */
    public static void CreateCrown(FireworkParticles.Starter starter, boolean b, boolean b1, int[] ints, int[] ints1)
    {
        starter.createParticleShape(0.2d,new double[][]{{-2,2},{0,-1.5},{-2,-2},{0,0},{2,2},{2,0},{2,-2}},ints,ints1,b,b1,false);
    }
}
