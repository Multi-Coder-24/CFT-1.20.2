package org.multicoder.cft.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.multicoder.cft.common.utility.CustomInitUtility;

public class FireworkModifierCommands
{

    //  Registers
    public static void Register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addMainColor").then(Commands.argument("color", IntegerArgumentType.integer()).then(Commands.argument("name", StringArgumentType.string()).executes(FireworkModifierCommands::AddMainColor)))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addMainColor").then(Commands.argument("red", IntegerArgumentType.integer(0,255)).then(Commands.argument("green",IntegerArgumentType.integer(0,255)).then(Commands.argument("blue",IntegerArgumentType.integer(0,255)).then(Commands.argument("name",StringArgumentType.string()).executes(FireworkModifierCommands::AddMainColorRGB)))))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addFadeColor").then(Commands.argument("color", IntegerArgumentType.integer()).then(Commands.argument("name", StringArgumentType.string()).executes(FireworkModifierCommands::AddFadeColor)))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addFadeColor").then(Commands.argument("red", IntegerArgumentType.integer(0,255)).then(Commands.argument("green",IntegerArgumentType.integer(0,255)).then(Commands.argument("blue",IntegerArgumentType.integer(0,255)).then(Commands.argument("name",StringArgumentType.string()).executes(FireworkModifierCommands::AddFadeColorRGB)))))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addTwinkle").then(Commands.argument("name", StringArgumentType.string()).executes(FireworkModifierCommands::AddTwinkle))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("addTrail").then(Commands.argument("name", StringArgumentType.string()).executes(FireworkModifierCommands::AddTrail))))).createBuilder().build();
    }

    private static int AddTrail(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AddTrail(Held,Name);
        return 0;
    }

    private static int AddTwinkle(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AddTwinkle(Held,Name);
        return 0;
    }

    //  Methods
    private static int AddMainColorRGB(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        int Red = IntegerArgumentType.getInteger(context,"red");
        int Green = IntegerArgumentType.getInteger(context,"green");
        int Blue = IntegerArgumentType.getInteger(context,"blue");
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AppendColorRGB(Held,Red,Green,Blue,Name);
        return 0;
    }

    private static int AddMainColor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        int Color = IntegerArgumentType.getInteger(context,"color");
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AppendColor(Held,Color,Name);
        return 0;
    }

    private static int AddFadeColorRGB(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        int Red = IntegerArgumentType.getInteger(context,"red");
        int Green = IntegerArgumentType.getInteger(context,"green");
        int Blue = IntegerArgumentType.getInteger(context,"blue");
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AppendFadeColorRGB(Held,Red,Green,Blue,Name);
        return 0;
    }

    private static int AddFadeColor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        int Color = IntegerArgumentType.getInteger(context,"color");
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AppendFadeColor(Held,Color,Name);
        return 0;
    }
}
