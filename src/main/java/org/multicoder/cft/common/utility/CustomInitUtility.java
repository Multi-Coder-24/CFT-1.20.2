package org.multicoder.cft.common.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CustomInitUtility
{
    public static void Setup(ItemStack In, int FD)
    {
        CompoundTag Tag = In.getOrCreateTag();
        CompoundTag Firework = new CompoundTag();
        Firework.putByte("Flight", ((byte) FD));
        Tag.put("Fireworks",Firework);
        In.setTag(Tag);
    }
    public static void AddStar(ItemStack In, StarShape shape,int color,String name)
    {
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosion;
        CompoundTag Star = new CompoundTag();
        int[] Cols = new int[] {color};
        Star.putIntArray("Colors",Cols);
        Star.putString("neoforge:shape_type",shape.getRName());
        Star.putByte("Type",(byte) 1);
        Star.putString("Name",name);
        if(Firework.contains("Explosions"))
        {
            Explosion = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        }
        else
        {
            Explosion = new ListTag();
        }
        Explosion.add(Star);
        Firework.put("Explosions",Explosion);
        Tag.put("Fireworks",Firework);
    }
    public static void AddStarRGB(ItemStack In, StarShape shape,int R,int G, int B,String name)
    {
        Color C = new Color(R,G,B);
        int color = C.getRGB();
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosion;
        CompoundTag Star = new CompoundTag();
        int[] Cols = new int[] {color};
        Star.putIntArray("Colors",Cols);
        Star.putString("neoforge:shape_type",shape.getRName());
        Star.putByte("Type",(byte) 1);
        Star.putString("Name",name);
        if(Firework.contains("Explosions"))
        {
            Explosion = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        }
        else
        {
            Explosion = new ListTag();
        }
        Explosion.add(Star);
        Firework.put("Explosions",Explosion);
        Tag.put("Fireworks",Firework);
    }
    public static void AppendColor(ItemStack In, int color,String name)
    {
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosions = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> Selected = new AtomicReference<>();
        AtomicInteger SelectedIndex = new AtomicInteger();
        Explosions.forEach(Star ->
        {
            CompoundTag StarCompound = (CompoundTag) Star;
            if(StarCompound.getString("Name").equals(name))
            {
                SelectedIndex.set(Explosions.indexOf(Star));
                Selected.set(StarCompound);
            }
        });
        try{
            CompoundTag Star = Selected.get();
            int[] Cols = Star.getIntArray("Colors");
            Cols = IntArrHandler.Append(Cols,color);
            Star.putIntArray("Colors",Cols);
            Explosions.set(SelectedIndex.get(),Star);
            Firework.put("Explosions",Explosions);
            Tag.put("Fireworks",Firework);
            In.setTag(Tag);
        }
        catch(NullPointerException e)
        {
            return;
        }
    }
    public static void AppendColorRGB(ItemStack In, int r,int g, int b,String name)
    {
        Color C = new Color(r,g,b);
        int color = C.getRGB();
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosions = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> Selected = new AtomicReference<>();
        AtomicInteger SelectedIndex = new AtomicInteger();
        Explosions.forEach(Star ->
        {
            CompoundTag StarCompound = (CompoundTag) Star;
            if(StarCompound.getString("Name").equals(name))
            {
                SelectedIndex.set(Explosions.indexOf(Star));
                Selected.set(StarCompound);
            }
        });
        try{
            CompoundTag Star = Selected.get();
            int[] Cols = Star.getIntArray("Colors");
            Cols = IntArrHandler.Append(Cols,color);
            Star.putIntArray("Colors",Cols);
            Explosions.set(SelectedIndex.get(),Star);
            Firework.put("Explosions",Explosions);
            Tag.put("Fireworks",Firework);
            In.setTag(Tag);
        }
        catch (NullPointerException ex){
            return;
        }
    }
    public static void AppendFadeColor(ItemStack In, int color,String name)
    {
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosions = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> Selected = new AtomicReference<>();
        AtomicInteger SelectedIndex = new AtomicInteger();
        Explosions.forEach(Star ->
        {
            CompoundTag StarCompound = (CompoundTag) Star;
            if(StarCompound.getString("Name").equals(name))
            {
                SelectedIndex.set(Explosions.indexOf(Star));
                Selected.set(StarCompound);
            }
        });
        try{
            CompoundTag Star = Selected.get();
            if(!Star.contains("FadeColors"))
            {
                int[] Cols = new int[] {color};
                Star.putIntArray("FadeColors",Cols);
            }
            else
            {
                int[] Cols = Star.getIntArray("FadeColors");
                Cols = IntArrHandler.Append(Cols,color);
                Star.putIntArray("FadeColors",Cols);
            }
            Explosions.set(SelectedIndex.get(),Star);
            Firework.put("Explosions",Explosions);
            Tag.put("Fireworks",Firework);
            In.setTag(Tag);
        }
        catch(NullPointerException e)
        {
            return;
        }
    }
    public static void AppendFadeColorRGB(ItemStack In, int r,int g, int b,String name)
    {
        Color C = new Color(r,g,b);
        int color = C.getRGB();
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosions = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> Selected = new AtomicReference<>();
        AtomicInteger SelectedIndex = new AtomicInteger();
        Explosions.forEach(Star ->
        {
            CompoundTag StarCompound = (CompoundTag) Star;
            if(StarCompound.getString("Name").equals(name))
            {
                SelectedIndex.set(Explosions.indexOf(Star));
                Selected.set(StarCompound);
            }
        });
        try{
            CompoundTag Star = Selected.get();
            if(!Star.contains("FadeColors"))
            {
                int[] Cols = new int[] {color};
                Star.putIntArray("FadeColors",Cols);
            }
            else
            {
                int[] Cols = Star.getIntArray("FadeColors");
                Cols = IntArrHandler.Append(Cols,color);
                Star.putIntArray("FadeColors",Cols);
            }
            Explosions.set(SelectedIndex.get(),Star);
            Firework.put("Explosions",Explosions);
            Tag.put("Fireworks",Firework);
            In.setTag(Tag);
        }
        catch (NullPointerException ex){
            return;
        }
    }
    public static void AddTwinkle(ItemStack In, String name)
    {
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosions = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> Selected = new AtomicReference<>();
        AtomicInteger SelectedIndex = new AtomicInteger();
        Explosions.forEach(Star ->
        {
            CompoundTag StarCompound = (CompoundTag) Star;
            if(StarCompound.getString("Name").equals(name))
            {
                SelectedIndex.set(Explosions.indexOf(Star));
                Selected.set(StarCompound);
            }
        });
        CompoundTag Star = Selected.get();
        Star.putByte("Flicker",(byte) 1);
        Explosions.set(SelectedIndex.get(),Star);
        Firework.put("Explosions",Explosions);
        Tag.put("Fireworks",Firework);
        In.setTag(Tag);
    }
    public static void AddTrail(ItemStack In, String name)
    {
        CompoundTag Tag = In.getTag();
        CompoundTag Firework = Tag.getCompound("Fireworks");
        ListTag Explosions = Firework.getList("Explosions", net.minecraft.nbt.Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> Selected = new AtomicReference<>();
        AtomicInteger SelectedIndex = new AtomicInteger();
        Explosions.forEach(Star ->
        {
            CompoundTag StarCompound = (CompoundTag) Star;
            if(StarCompound.getString("Name").equals(name))
            {
                SelectedIndex.set(Explosions.indexOf(Star));
                Selected.set(StarCompound);
            }
        });
        CompoundTag Star = Selected.get();
        Star.putByte("Trail",(byte) 1);
        Explosions.set(SelectedIndex.get(),Star);
        Firework.put("Explosions",Explosions);
        Tag.put("Fireworks",Firework);
        In.setTag(Tag);
    }
}
