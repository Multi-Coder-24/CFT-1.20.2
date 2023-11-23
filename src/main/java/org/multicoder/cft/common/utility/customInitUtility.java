package org.multicoder.cft.common.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class customInitUtility
{
    /***
     * Creates the basic firework with the correct NBT format
     * @param fireworkRocket The item stack
     * @param flightDuration The flight duration as an integer
     */
    public static void setup(ItemStack fireworkRocket, int flightDuration)
    {
        CompoundTag stackTag = fireworkRocket.getOrCreateTag();
        CompoundTag fireworkTag = new CompoundTag();
        fireworkTag.putByte("Flight", ((byte) flightDuration));
        stackTag.put("Fireworks",fireworkTag);
        fireworkRocket.setTag(stackTag);
    }

    /***
     * Adds a star with the given name onto the firework stack
     * @param fireworkRocket The item stack
     * @param shape The enum representing the shape
     * @param color The native color type
     * @param name The name of the star
     */
    public static void addStar(ItemStack fireworkRocket, starShape shape, int color, String name)
    {
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag fireworkTag = stackTag.getCompound("Fireworks");
        ListTag stars;
        CompoundTag starTag = new CompoundTag();
        int[] Cols = new int[] {color};
        starTag.putIntArray("Colors",Cols);
        starTag.putString("neoforge:shape_type",shape.getName());
        starTag.putByte("Type",(byte) 1);
        starTag.putString("Name",name);
        if(fireworkTag.contains("Explosions"))
        {
            stars = fireworkTag.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        }
        else
        {
            stars = new ListTag();
        }
        stars.add(starTag);
        fireworkTag.put("Explosions",stars);
        stackTag.put("Fireworks",fireworkTag);
    }

    /***
     * Adds a star with the given name onto the firework stack, converting the rgb values to the native type
     * @param fireworkRocket The item stack
     * @param shape The enum representing the shape
     * @param red The red channel as an integer between 0-255
     * @param green The green channel as an integer between 0-255
     * @param blue The blue channel as an integer between 0-255
     * @param name The name of the star
     */
    public static void addStarRGB(ItemStack fireworkRocket, starShape shape, int red, int green, int blue, String name)
    {
        Color nativeColor = new Color(red,green,blue);
        int color = nativeColor.getRGB();
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag fireworkTag = stackTag.getCompound("Fireworks");
        ListTag stars;
        CompoundTag starTag = new CompoundTag();
        int[] Cols = new int[] {color};
        starTag.putIntArray("Colors",Cols);
        starTag.putString("neoforge:shape_type",shape.getName());
        starTag.putByte("Type",(byte) 1);
        starTag.putString("Name",name);
        if(fireworkTag.contains("Explosions"))
        {
            stars = fireworkTag.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        }
        else
        {
            stars = new ListTag();
        }
        stars.add(starTag);
        fireworkTag.put("Explosions",stars);
        stackTag.put("Fireworks",fireworkTag);
    }

    /***
     * Adds a main color to the star with the given name.
     * @param fireworkRocket The item stack
     * @param color The color in the native format
     * @param name The star's name
     */
    public static void appendColor(ItemStack fireworkRocket, int color, String name)
    {
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag fireworkTag = stackTag.getCompound("Fireworks");
        ListTag stars = fireworkTag.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> selectedReference = new AtomicReference<>();
        AtomicInteger indexReference = new AtomicInteger();
        stars.forEach(Star ->
        {
            CompoundTag starTag = (CompoundTag) Star;
            if(starTag.getString("Name").equals(name))
            {
                indexReference.set(stars.indexOf(Star));
                selectedReference.set(starTag);
            }
        });
        try{
            CompoundTag starTag = selectedReference.get();
            int[] colors = starTag.getIntArray("Colors");
            colors = intArrHandler.addIntToArray(colors,color);
            starTag.putIntArray("Colors",colors);
            stars.set(indexReference.get(),starTag);
            fireworkTag.put("Explosions",stars);
            stackTag.put("Fireworks",fireworkTag);
            fireworkRocket.setTag(stackTag);
        }
        catch(NullPointerException e)
        {
            return;
        }
    }

    /***
     * Adds a main color to the star with the given name, converts rgb to the native type
     * @param fireworkRocket The item stack
     * @param red The red channel as an integer between 0-255
     * @param green The green channel as an integer between 0-255
     * @param blue The blue channel as an integer between 0-255
     * @param name The star's name
     */
    public static void appendColorRGB(ItemStack fireworkRocket, int red, int green, int blue, String name)
    {
        Color nativeColor = new Color(red,green,blue);
        int color = nativeColor.getRGB();
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag fireworkTag = stackTag.getCompound("Fireworks");
        ListTag stars = fireworkTag.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> selectedReference = new AtomicReference<>();
        AtomicInteger indexReference = new AtomicInteger();
        stars.forEach(Star ->
        {
            CompoundTag starTag = (CompoundTag) Star;
            if(starTag.getString("Name").equals(name))
            {
                indexReference.set(stars.indexOf(Star));
                selectedReference.set(starTag);
            }
        });
        try{
            CompoundTag starTag = selectedReference.get();
            int[] colors = starTag.getIntArray("Colors");
            colors = intArrHandler.addIntToArray(colors,color);
            starTag.putIntArray("Colors",colors);
            stars.set(indexReference.get(),starTag);
            fireworkTag.put("Explosions",stars);
            stackTag.put("Fireworks",fireworkTag);
            fireworkRocket.setTag(stackTag);
        }
        catch (NullPointerException ex){
            return;
        }
    }

    /***
     * Adds a fade color to the star with the given name.
     * @param fireworkRocket The item stack
     * @param color The color in the native format
     * @param name The star's name
     */
    public static void appendFadeColor(ItemStack fireworkRocket, int color, String name)
    {
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag Firework = stackTag.getCompound("Fireworks");
        ListTag stars = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> selectedReference = new AtomicReference<>();
        AtomicInteger indexReference = new AtomicInteger();
        stars.forEach(Star ->
        {
            CompoundTag starTag = (CompoundTag) Star;
            if(starTag.getString("Name").equals(name))
            {
                indexReference.set(stars.indexOf(Star));
                selectedReference.set(starTag);
            }
        });
        try{
            CompoundTag starTag = selectedReference.get();
            if(!starTag.contains("FadeColors"))
            {
                int[] colors = new int[] {color};
                starTag.putIntArray("FadeColors",colors);
            }
            else
            {
                int[] colors = starTag.getIntArray("FadeColors");
                colors = intArrHandler.addIntToArray(colors,color);
                starTag.putIntArray("FadeColors",colors);
            }
            stars.set(indexReference.get(),starTag);
            Firework.put("Explosions",stars);
            stackTag.put("Fireworks",Firework);
            fireworkRocket.setTag(stackTag);
        }
        catch(NullPointerException e)
        {
            return;
        }
    }

    /***
     * Adds a main color to the star with the given name, converts rgb to the native type
     * @param fireworkRocket The item stack
     * @param red The red channel as an integer between 0-255
     * @param green The green channel as an integer between 0-255
     * @param blue The blue channel as an integer between 0-255
     * @param name The star's name
     */
    public static void appendFadeColorRGB(ItemStack fireworkRocket,int red,int green,int blue,String name)
    {
        Color nativeColor = new Color(red,green,blue);
        int color = nativeColor.getRGB();
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag fireworkTag = stackTag.getCompound("Fireworks");
        ListTag stars = fireworkTag.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> selectedReference = new AtomicReference<>();
        AtomicInteger indexReference = new AtomicInteger();
        stars.forEach(Star ->
        {
            CompoundTag starTag = (CompoundTag) Star;
            if(starTag.getString("Name").equals(name))
            {
                indexReference.set(stars.indexOf(Star));
                selectedReference.set(starTag);
            }
        });
        try{
            CompoundTag starTag = selectedReference.get();
            if(!starTag.contains("FadeColors"))
            {
                int[] colors = new int[] {color};
                starTag.putIntArray("FadeColors",colors);
            }
            else
            {
                int[] colors = starTag.getIntArray("FadeColors");
                colors = intArrHandler.addIntToArray(colors,color);
                starTag.putIntArray("FadeColors",colors);
            }
            stars.set(indexReference.get(),starTag);
            fireworkTag.put("Explosions",stars);
            stackTag.put("Fireworks",fireworkTag);
            fireworkRocket.setTag(stackTag);
        }
        catch (NullPointerException ex){
            return;
        }
    }

    /***
     * Adds the twinkle effect to the star with the given name.
     * @param fireworkRocket The item stack
     * @param name The star's name
     */
    public static void addTwinkle(ItemStack fireworkRocket, String name)
    {
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag fireworkTag = stackTag.getCompound("Fireworks");
        ListTag stars = fireworkTag.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> selectedReference = new AtomicReference<>();
        AtomicInteger indexReference = new AtomicInteger();
        stars.forEach(Star ->
        {
            CompoundTag starTag = (CompoundTag) Star;
            if(starTag.getString("Name").equals(name))
            {
                indexReference.set(stars.indexOf(Star));
                selectedReference.set(starTag);
            }
        });
        CompoundTag starTag = selectedReference.get();
        starTag.putByte("Flicker",(byte) 1);
        stars.set(indexReference.get(),starTag);
        fireworkTag.put("Explosions",stars);
        stackTag.put("Fireworks",fireworkTag);
        fireworkRocket.setTag(stackTag);
    }

    /***
     * Adds the trail effect to the star with the given name.
     * @param fireworkRocket The item stack
     * @param name The star's name
     */
    public static void addTrail(ItemStack fireworkRocket, String name)
    {
        CompoundTag stackTag = fireworkRocket.getTag();
        CompoundTag fireworkTag = stackTag.getCompound("Fireworks");
        ListTag stars = fireworkTag.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> selectedReference = new AtomicReference<>();
        AtomicInteger indexReference = new AtomicInteger();
        stars.forEach(Star ->
        {
            CompoundTag starTag = (CompoundTag) Star;
            if(starTag.getString("Name").equals(name))
            {
                indexReference.set(stars.indexOf(Star));
                selectedReference.set(starTag);
            }
        });
        CompoundTag starTag = selectedReference.get();
        starTag.putByte("Trail",(byte) 1);
        stars.set(indexReference.get(),starTag);
        fireworkTag.put("Explosions",stars);
        stackTag.put("Fireworks",fireworkTag);
        fireworkRocket.setTag(stackTag);
    }
}
