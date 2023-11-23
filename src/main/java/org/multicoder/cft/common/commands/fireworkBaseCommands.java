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
import net.neoforged.neoforge.server.command.EnumArgument;
import org.multicoder.cft.common.utility.customInitUtility;
import org.multicoder.cft.common.utility.randomFireworkMaker;
import org.multicoder.cft.common.utility.starShape;

public class fireworkBaseCommands
{
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal("cft").then(Commands.literal("init").then(Commands.argument("flight", IntegerArgumentType.integer(1,10)).executes(fireworkBaseCommands::setup)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("add").then(Commands.argument("shape", EnumArgument.enumArgument(starShape.class)).then(Commands.argument("color", IntegerArgumentType.integer()).then(Commands.argument("name", StringArgumentType.string()).executes(fireworkBaseCommands::addStar))))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("add").then(Commands.argument("shape",EnumArgument.enumArgument(starShape.class)).then(Commands.argument("red",IntegerArgumentType.integer(0,255)).then(Commands.argument("green",IntegerArgumentType.integer(0,255)).then(Commands.argument("blue",IntegerArgumentType.integer(0,255)).then(Commands.argument("name",StringArgumentType.string()).executes(fireworkBaseCommands::addStarRGB))))))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("random").executes(fireworkBaseCommands::getRandom))).createBuilder().build();
    }

    private static int getRandom(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        if(player.isCreative()){
            ItemStack rocket = randomFireworkMaker.createRandomFirework();
            rocket.setCount(16);
            player.addItem(rocket);
        }
        else
        {
            int paperIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.PAPER));
            int whiteDyeIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.WHITE_DYE));
            int gunpowderIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.GUNPOWDER));
            if(paperIndex == -1 || whiteDyeIndex == -1 || gunpowderIndex == -1){return 0;}
            player.getInventory().getItem(paperIndex).shrink(1);
            player.getInventory().getItem(whiteDyeIndex).shrink(2);
            player.getInventory().getItem(gunpowderIndex).shrink(1);
            ItemStack rocket = randomFireworkMaker.createRandomFirework();
            rocket.setCount(16);
            player.addItem(rocket);
        }
        return 1;
    }


    /***
     * Adds a star with a custom color based upon the minecraft built-in color system.
     * The name is used to add more colors or effects later.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see customInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int addStar(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        starShape starShape = context.getArgument("shape", org.multicoder.cft.common.utility.starShape.class);
        String starName = StringArgumentType.getString(context,"name");
        int intColor = IntegerArgumentType.getInteger(context,"color");
        if(player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            customInitUtility.addStar(mainHand,starShape,intColor,starName);
        }
        else
        {
            int gunpowderIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.GUNPOWDER));
            int whiteDyeIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.WHITE_DYE));
            if(gunpowderIndex == -1 || whiteDyeIndex == -1){return 0;}
            player.getInventory().getItem(gunpowderIndex).shrink(1);
            player.getInventory().getItem(whiteDyeIndex).shrink(1);
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            customInitUtility.addStar(mainHand,starShape,intColor,starName);
        }
        return 1;
    }


    /***
     * Adds a star with a custom color.
     * The name is used to add more colors or effects later.
     * Deducts resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see customInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int addStarRGB(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        starShape starShape = context.getArgument("shape", org.multicoder.cft.common.utility.starShape.class);
        int red = IntegerArgumentType.getInteger(context,"red");
        int green = IntegerArgumentType.getInteger(context,"green");
        int blue = IntegerArgumentType.getInteger(context,"blue");
        String starName = StringArgumentType.getString(context,"name");
        if(player.isCreative())
        {
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            customInitUtility.addStarRGB(mainHand,starShape,red,green,blue,starName);
        }
        else
        {
            int gunpowderIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.GUNPOWDER));
            int whiteDyeIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.WHITE_DYE));
            if(gunpowderIndex == -1 || whiteDyeIndex == -1){return 0;}
            player.getInventory().getItem(gunpowderIndex).shrink(1);
            player.getInventory().getItem(whiteDyeIndex).shrink(1);
            ItemStack mainHand = player.getMainHandItem();
            if(!mainHand.is(Items.FIREWORK_ROCKET)){return 0;}
            customInitUtility.addStarRGB(mainHand,starShape,red,green,blue,starName);
        }
        return 1;
    }

    /***
     * Creates a basic firework rocket.
     * Deducting resources if the player is not in creative mode.
     * The command uses CustomInitUtility to handle the NBT data
     * @see customInitUtility
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int setup(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        int flightDuration = IntegerArgumentType.getInteger(context,"flight");
        ServerPlayer player = context.getSource().getPlayerOrException();
        if(player.isCreative())
        {
            ItemStack fireworkRocket = new ItemStack(Items.FIREWORK_ROCKET);
            customInitUtility.setup(fireworkRocket,flightDuration);
            fireworkRocket.setCount(16);
            player.addItem(fireworkRocket);
        }
        else
        {
            int gunpowderIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.GUNPOWDER));
            int paperIndex = player.getInventory().findSlotMatchingItem(new ItemStack(Items.PAPER));
            if(gunpowderIndex == -1 || paperIndex == -1){return 0;}
            ItemStack gunpowder = player.getInventory().getItem(gunpowderIndex);
            ItemStack paper = player.getInventory().getItem(paperIndex);
            gunpowder.shrink(1);
            paper.shrink(1);
            player.getInventory().setItem(gunpowderIndex,gunpowder);
            player.getInventory().setItem(paperIndex,paper);
            ItemStack fireworkRocket = new ItemStack(Items.FIREWORK_ROCKET);
            customInitUtility.setup(fireworkRocket,flightDuration);
            fireworkRocket.setCount(16);
            player.addItem(fireworkRocket);
        }
        return 1;
    }
}
