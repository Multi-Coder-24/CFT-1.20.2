package org.multicoder.cft.common.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.common.util.JsonUtils;
import org.multicoder.cft.common.utility.FireworkWorldAddon;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class UtilityCommands
{
    public static void Register(CommandDispatcher<CommandSourceStack> dispatcher) {
        //dispatcher.register(Commands.literal("cft").then(Commands.literal("copyTag").executes(UtilityCommands::CopyTag))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("cloneToStack").executes(UtilityCommands::CloneStack))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("SaveToPlayer").then(Commands.argument("name", StringArgumentType.string()).executes(UtilityCommands::SaveRocket)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("LoadFromPlayer").then(Commands.argument("name",StringArgumentType.string()).executes(UtilityCommands::LoadRocket)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("SaveToLevel").then(Commands.argument("name", StringArgumentType.string()).executes(UtilityCommands::SaveRocketLevel)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("LoadFromLevel").then(Commands.argument("name",StringArgumentType.string()).executes(UtilityCommands::LoadRocketLevel)))).createBuilder().build();
    }

    private static int LoadRocket(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative())
        {
            player.sendSystemMessage(Component.literal("You do not have permission or are not in creative"));
            return -1;
        }
        String saveName = StringArgumentType.getString(context,"name");
        CompoundTag Tag = player.getPersistentData();
        ListTag LT;
        if(!Tag.contains("SavedRockets"))
        {
            return -3;
        }
        else
        {
            LT = Tag.getList("SavedRockets", net.minecraft.nbt.Tag.TAG_COMPOUND);
        }
        AtomicReference<CompoundTag> Rocket = new AtomicReference<>();
        LT.forEach(saved ->
        {
            CompoundTag Data = (CompoundTag) saved;
            if(Data.getString("Name").equals(saveName))
            {
                Rocket.set(Data);
            }
        });
        if(Objects.nonNull(Rocket.get()))
        {
            ItemStack FRocket = new ItemStack(Items.FIREWORK_ROCKET,64);
            CompoundTag ItemStackTag = FRocket.getOrCreateTag();
            CompoundTag Firework = Rocket.get().getCompound("Rocket");
            Firework = Firework.getCompound("Fireworks");
            ItemStackTag.put("Fireworks",Firework);
            FRocket.setTag(ItemStackTag);
            player.addItem(FRocket);
            player.sendSystemMessage(Component.literal("Rocket loaded from player data"));
            return 0;
        }
        player.sendSystemMessage(Component.literal("You do not have a rocket by that name stored"));
        return -4;
    }
    private static int LoadRocketLevel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative())
        {
            player.sendSystemMessage(Component.literal("You do not have permission or are not in creative"));
            return -1;
        }
        String saveName = StringArgumentType.getString(context,"name");
        ServerLevel level = context.getSource().getLevel();
        FireworkWorldAddon FDA = level.getDataStorage().get(FireworkWorldAddon.DataFactory,FireworkWorldAddon.SaveName);
        CompoundTag Rocket = FDA.LoadPreset(saveName);
        if(Objects.nonNull(Rocket))
        {
            ItemStack FRocket = new ItemStack(Items.FIREWORK_ROCKET,64);
            CompoundTag ItemStackTag = FRocket.getOrCreateTag();
            ItemStackTag.put("Fireworks",Rocket);
            FRocket.setTag(ItemStackTag);
            player.addItem(FRocket);
            player.sendSystemMessage(Component.literal("Rocket loaded from level data"));
            return 0;
        }
        player.sendSystemMessage(Component.literal("The level does not have a rocket by that name stored"));
        return -4;
    }
    private static int SaveRocketLevel(CommandContext<CommandSourceStack> context)  throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative())
        {
            player.sendSystemMessage(Component.literal("You do not have permission or are not in creative"));
            return -1;
        }
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        String saveName = StringArgumentType.getString(context,"name");
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET))
        {
            player.sendSystemMessage(Component.literal("You are not holding a firework"));
            return -2;
        }
        try
        {
            CompoundTag Saved = new CompoundTag();
            Saved.putString("Name",saveName);
            Saved.put("Fireworks",Held.getTag().getCompound("Fireworks"));
            ServerLevel level = context.getSource().getLevel();
            FireworkWorldAddon FDA = level.getDataStorage().get(FireworkWorldAddon.DataFactory,FireworkWorldAddon.SaveName);
            FDA.SavePreset(Saved);
            FDA.setDirty();
            player.sendSystemMessage(Component.literal("Firework saved to level data, Use /cft LoadFromLevel to retrieve"));
            return 0;
        }
        catch(Exception ex)
        {
            player.sendSystemMessage(Component.literal("Error: " + ex.getMessage() + " Has Occurred"));
            throw ex;
        }
    }

    private static int SaveRocket(CommandContext<CommandSourceStack> context)  throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative())
        {
            player.sendSystemMessage(Component.literal("You do not have permission or are not in creative"));
            return -1;
        }
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        String saveName = StringArgumentType.getString(context,"name");
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET))
        {
            player.sendSystemMessage(Component.literal("You are not holding a firework"));
            return -2;
        }
        CompoundTag Tag = player.getPersistentData();
        ListTag LT;
        if(!Tag.contains("SavedRockets"))
        {
            LT = new ListTag();
            CompoundTag Saved = new CompoundTag();
            Saved.putString("Name",saveName);
            Saved.put("Rocket",Held.getTag());
            LT.add(Saved);
        }
        else
        {
            LT = Tag.getList("SavedRockets", net.minecraft.nbt.Tag.TAG_COMPOUND);
            CompoundTag Saved = new CompoundTag();
            Saved.putString("Name",saveName);
            Saved.put("Rocket",Held.getTag());
            LT.add(Saved);
        }
        Tag.put("SavedRockets",LT);
        player.sendSystemMessage(Component.literal("Firework saved to player data, Use /cft LoadFromPlayer to retrieve"));
        return 0;
    }

    private static int CopyTag(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(Held.hasTag())
        {
            System.out.println(Held.getTag());
        }
        return 0;
    }
    private static int CloneStack(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if(!context.getSource().hasPermission(2) && !context.getSource().getPlayerOrException().isCreative()){return -1;}
        ItemStack Held = context.getSource().getPlayerOrException().getMainHandItem();
        if(!Held.getItem().equals(Items.FIREWORK_ROCKET)){return -2;}
        ItemStack Cloned = Held.copy();
        Cloned.setCount(64);
        context.getSource().getPlayerOrException().addItem(Cloned);
        return 0;
    }
}
