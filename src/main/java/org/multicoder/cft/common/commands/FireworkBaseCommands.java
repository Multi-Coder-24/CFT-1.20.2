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
import net.neoforged.neoforge.server.command.EnumArgument;
import org.multicoder.cft.common.utility.CustomInitUtility;
import org.multicoder.cft.common.utility.StarShape;

public class FireworkBaseCommands
{
    public static void Register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal("cft").then(Commands.literal("init").then(Commands.argument("flight", IntegerArgumentType.integer(1,10)).executes(FireworkBaseCommands::Setup)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("add").then(Commands.argument("shape", EnumArgument.enumArgument(StarShape.class)).then(Commands.argument("color", IntegerArgumentType.integer()).then(Commands.argument("name", StringArgumentType.string()).executes(FireworkBaseCommands::AddStar))))))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("star").then(Commands.literal("add").then(Commands.argument("shape",EnumArgument.enumArgument(StarShape.class)).then(Commands.argument("red",IntegerArgumentType.integer(0,255)).then(Commands.argument("green",IntegerArgumentType.integer(0,255)).then(Commands.argument("blue",IntegerArgumentType.integer(0,255)).then(Commands.argument("name",StringArgumentType.string()).executes(FireworkBaseCommands::AddStarRGB))))))))).createBuilder().build();
    }

    private static int AddStar(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        int C = IntegerArgumentType.getInteger(context,"color");
        StarShape Shape = context.getArgument("shape", StarShape.class);
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AddStar(Held,Shape,C,Name);
        return 0;
    }

    private static int AddStarRGB(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        StarShape Shape = context.getArgument("shape", StarShape.class);
        int Red = IntegerArgumentType.getInteger(context,"red");
        int Green = IntegerArgumentType.getInteger(context,"green");
        int Blue = IntegerArgumentType.getInteger(context,"blue");
        String Name = StringArgumentType.getString(context,"name");
        CustomInitUtility.AddStarRGB(Held,Shape,Red,Green,Blue,Name);
        return 0;
    }

    private static int Setup(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {




        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Rocket = new ItemStack(Items.FIREWORK_ROCKET);
        int FD = IntegerArgumentType.getInteger(context,"flight");
        CustomInitUtility.Setup(Rocket,FD);
        context.getSource().getPlayerOrException().addItem(Rocket);
        return 0;
    }
}
