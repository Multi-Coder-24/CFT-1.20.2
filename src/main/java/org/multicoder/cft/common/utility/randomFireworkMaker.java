package org.multicoder.cft.common.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;
import java.util.Random;

public class randomFireworkMaker
{
    public static ItemStack createRandomFirework()
    {
        ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        Random random = new Random();
        CompoundTag stackTag = firework.getOrCreateTag();
        CompoundTag fireworkTag = new CompoundTag();
        fireworkTag.putByte("Flight",(byte) 1);
        int red = random.nextInt(90,255), green = random.nextInt(90,255), blue = random.nextInt(90,255);
        int redFade = random.nextInt(90,255),greenFade = random.nextInt(90,255),blueFade = random.nextInt(90,255);
        int shapeIndex = random.nextInt(0,10);
        starShape shape = starShape.getShape(shapeIndex);
        Color nativeColor = new Color(red,green,blue);
        Color nativeFadeColor = new Color(redFade,greenFade,blueFade);
        ListTag stars = new ListTag();
        CompoundTag starTag = new CompoundTag();
        int[] colors = new int[]{nativeColor.getRGB()};
        int[] fadeColors = new int[] {nativeFadeColor.getRGB()};
        starTag.putIntArray("Colors",colors);
        starTag.putIntArray("FadeColors",fadeColors);
        starTag.putByte("Type",(byte) 1);
        starTag.putString("neoforge:shape_type",shape.getName());
        stars.add(starTag);
        fireworkTag.put("Explosions",stars);
        stackTag.put("Fireworks",fireworkTag);
        firework.setTag(stackTag);
        return firework;
    }
}
