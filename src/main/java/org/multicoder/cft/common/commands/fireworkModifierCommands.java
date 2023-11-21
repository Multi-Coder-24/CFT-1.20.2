package org.multicoder.cft.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.multicoder.cft.common.utility.CustomInitUtility;

public class fireworkModifierCommands
{

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addMainColor").then(Commands.argument("color", IntegerArgumentType.integer()).then(Commands.argument("name", StringArgumentType.string()).executes(fireworkModifierCommands::addMainColor)))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addMainColor").then(Commands.argument("red", IntegerArgumentType.integer(0,255)).then(Commands.argument("green",IntegerArgumentType.integer(0,255)).then(Commands.argument("blue",IntegerArgumentType.integer(0,255)).then(Commands.argument("name",StringArgumentType.string()).executes(fireworkModifierCommands::addMainColorRGB)))))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addFadeColor").then(Commands.argument("color", IntegerArgumentType.integer()).then(Commands.argument("name", StringArgumentType.string()).executes(fireworkModifierCommands::addFadeColor)))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addFadeColor").then(Commands.argument("red", IntegerArgumentType.integer(0,255)).then(Commands.argument("green",IntegerArgumentType.integer(0,255)).then(Commands.argument("blue",IntegerArgumentType.integer(0,255)).then(Commands.argument("name",StringArgumentType.string()).executes(fireworkModifierCommands::AddFadeColorRGB)))))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addTwinkle").then(Commands.argument("name", StringArgumentType.string()).executes(fireworkModifierCommands::addTwinkle))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addTrail").then(Commands.argument("name", StringArgumentType.string()).executes(fireworkModifierCommands::addTrail))))).createBuilder().build();
    }

    /***
     * Adds the trail effect to a star with the given name.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see CustomInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int addTrail(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String starName = StringArgumentType.getString(context,"name");
        if(player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AddTrail(mainHand,starName);
        }
        else
        {
            int diamondIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.DIAMOND));
            if(diamondIndex == -1){return 0;}
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            player.getInventory().getItem(diamondIndex).shrink(1);
            CustomInitUtility.AddTrail(mainHand,starName);
        }
        return 1;
    }

    /***
     * Adds the twinkle/sparkle effect to a star with the given name.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see CustomInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int addTwinkle(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String starName = StringArgumentType.getString(context,"name");
        if(player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AddTwinkle(mainHand,starName);
        }
        else
        {
            int glowstoneDustIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.GLOWSTONE_DUST));
            if(glowstoneDustIndex == -1){return 0;}
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            player.getInventory().getItem(glowstoneDustIndex).shrink(1);
            CustomInitUtility.AddTwinkle(mainHand,starName);
        }
        return 1;
    }

    /***
     * Adds a custom color to the main part of a star with the given name.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see CustomInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int addMainColorRGB(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        int red = IntegerArgumentType.getInteger(context,"red");
        int green = IntegerArgumentType.getInteger(context,"green");
        int blue = IntegerArgumentType.getInteger(context,"blue");
        String starName = StringArgumentType.getString(context,"name");
        if (player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendColorRGB(mainHand,red,green,blue,starName);
        }
        else
        {
            int whiteDyeIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.WHITE_DYE));
            if(whiteDyeIndex == -1){return 0;}
            player.getInventory().getItem(whiteDyeIndex).shrink(1);
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendColorRGB(mainHand,red,green,blue,starName);
        }
        return 1;
    }

    /***
     * Adds a custom color based upon the minecraft built-in color system to the main part of a star with the given name.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see CustomInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int addMainColor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        int intColor = IntegerArgumentType.getInteger(context,"color");
        String starName = StringArgumentType.getString(context,"name");
        if (player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendColor(mainHand,intColor,starName);
        }
        else
        {
            int whiteDyeIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.WHITE_DYE));
            if(whiteDyeIndex == -1){return 0;}
            player.getInventory().getItem(whiteDyeIndex).shrink(1);
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendColor(mainHand,intColor,starName);
        }
        return 1;
    }

    /***
     * Adds a custom color to the fade part of a star with the given name.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see CustomInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int AddFadeColorRGB(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        int red = IntegerArgumentType.getInteger(context,"red");
        int green = IntegerArgumentType.getInteger(context,"green");
        int blue = IntegerArgumentType.getInteger(context,"blue");
        String starName = StringArgumentType.getString(context,"name");
        if (player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendFadeColorRGB(mainHand,red,green,blue,starName);
        }
        else
        {
            int whiteDyeIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.WHITE_DYE));
            if(whiteDyeIndex == -1){return 0;}
            player.getInventory().getItem(whiteDyeIndex).shrink(1);
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendFadeColorRGB(mainHand,red,green,blue,starName);
        }
        return 1;
    }

    /***
     * Adds a custom color based upon the minecraft built-in color system to the fade part of a star with the given name.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see CustomInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int addFadeColor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        int intColor = IntegerArgumentType.getInteger(context,"color");
        String starName = StringArgumentType.getString(context,"name");
        if (player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendFadeColor(mainHand,intColor,starName);
        }
        else
        {
            int whiteDyeIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.WHITE_DYE));
            if(whiteDyeIndex == -1){return 0;}
            player.getInventory().getItem(whiteDyeIndex).shrink(1);
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            CustomInitUtility.AppendFadeColor(mainHand,intColor,starName);
        }
        return 1;
    }
}
