package org.multicoder.cft.common.extra;

import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.client.FireworkShapeFactoryRegistry;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;

import java.util.HashMap;

public class rocketShape
{
    private static final FireworkRocketItem.Shape TRIANGLE = FireworkRocketItem.Shape.create("TRIANGLE",FireworkRocketItem.Shape.values().length,"triangle");
    private static final FireworkRocketItem.Shape DIAMOND = FireworkRocketItem.Shape.create("DIAMOND",FireworkRocketItem.Shape.values().length,"diamond");
    private static final FireworkRocketItem.Shape SQUARE = FireworkRocketItem.Shape.create("SQUARE",FireworkRocketItem.Shape.values().length,"square");
    private static final FireworkRocketItem.Shape CROWN = FireworkRocketItem.Shape.create("CROWN",FireworkRocketItem.Shape.values().length,"crown");
    private static final FireworkRocketItem.Shape PYRAMID = FireworkRocketItem.Shape.create("PYRAMID",FireworkRocketItem.Shape.values().length,"pyramid");

    /***
     * Registers shapes and recipes.
     */
    public static void register()
    {
        FireworkShapeFactoryRegistry.register(TRIANGLE, rocketShapes::CreateTriangle);
        FireworkShapeFactoryRegistry.register(PYRAMID, rocketShapes::CreatePyramid);
        FireworkShapeFactoryRegistry.register(DIAMOND, rocketShapes::CreateDiamond);
        FireworkShapeFactoryRegistry.register(SQUARE, rocketShapes::CreateSquare);
        FireworkShapeFactoryRegistry.register(CROWN, rocketShapes::CreateCrown);
        FireworkStarRecipe.SHAPE_BY_ITEM = new HashMap<>(FireworkStarRecipe.SHAPE_BY_ITEM);
        FireworkStarRecipe.SHAPE_BY_ITEM.put(Items.AMETHYST_SHARD,TRIANGLE);
        FireworkStarRecipe.SHAPE_BY_ITEM.put(Items.EMERALD,CROWN);
        FireworkStarRecipe.SHAPE_BY_ITEM.put(Items.BLAZE_ROD,PYRAMID);
        FireworkStarRecipe.SHAPE_BY_ITEM.put(Items.DIRT,SQUARE);
        FireworkStarRecipe.SHAPE_BY_ITEM.put(Items.COPPER_INGOT,DIAMOND);
        FireworkStarRecipe.SHAPE_INGREDIENT = CompoundIngredient.of(FireworkStarRecipe.SHAPE_INGREDIENT, Ingredient.of(Items.BLAZE_ROD), Ingredient.of(Items.EMERALD), Ingredient.of(Items.AMETHYST_SHARD), Ingredient.of(Items.DIRT), Ingredient.of(Items.COPPER_INGOT));
    }

}
