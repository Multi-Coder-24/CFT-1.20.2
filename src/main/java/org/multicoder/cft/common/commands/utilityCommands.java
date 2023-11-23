package org.multicoder.cft.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.multicoder.cft.common.utility.fireworkWorldAddon;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class utilityCommands
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("cft").then(Commands.literal("SaveToPlayer").then(Commands.argument("name", StringArgumentType.string()).executes(utilityCommands::saveRocket)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("LoadFromPlayer").then(Commands.argument("name",StringArgumentType.string()).executes(utilityCommands::loadRocket)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("SaveToLevel").then(Commands.argument("name", StringArgumentType.string()).executes(utilityCommands::saveRocketLevel)))).createBuilder().build();
        dispatcher.register(Commands.literal("cft").then(Commands.literal("LoadFromLevel").then(Commands.argument("name",StringArgumentType.string()).executes(utilityCommands::loadRocketLevel)))).createBuilder().build();
    }

    /***
     * Reads the player's persistent data to search for a firework with the given name.
     * If the firework is found, gives the player 16 of the rockets.
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int loadRocket(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String saveName = StringArgumentType.getString(context,"name");
        CompoundTag persistentData = player.getPersistentData();
        ListTag savedRocketsList;
        if(!persistentData.contains("SavedRockets")){return 0;}
        savedRocketsList = persistentData.getList("SavedRockets", Tag.TAG_COMPOUND);
        AtomicReference<CompoundTag> atomicRocket = new AtomicReference<>();
        savedRocketsList.forEach(compound ->
        {
            CompoundTag rocketTag = (CompoundTag) compound;
            if(rocketTag.getString("name").equals(saveName))
            {
                atomicRocket.set(rocketTag);
            }
        });
        if(Objects.nonNull(atomicRocket.get()))
        {
            ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
            firework.setCount(16);
            CompoundTag stackCompound = firework.getOrCreateTag();
            CompoundTag fireworkCompound = atomicRocket.get();
            fireworkCompound = fireworkCompound.getCompound("Fireworks");
            stackCompound.put("Fireworks",fireworkCompound);
            firework.setTag(stackCompound);
            player.addItem(firework);
            return 1;
        }
        return 0;
    }

    /***
     * Reads the level data to search for a firework with the given name.
     * If the firework is found, gives the player 16 of the rockets.
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int loadRocketLevel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String saveName = StringArgumentType.getString(context,"name");
        ServerLevel level = context.getSource().getLevel();
        fireworkWorldAddon worldData = level.getDataStorage().get(fireworkWorldAddon.addonFactory, fireworkWorldAddon.saveName);
        CompoundTag rocketCompound = worldData.loadPreset(saveName);
        if(Objects.nonNull(rocketCompound))
        {
            ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET,16);
            CompoundTag stackCompound = firework.getOrCreateTag();
            stackCompound.put("Fireworks",rocketCompound);
            firework.setTag(stackCompound);
            player.addItem(firework);
            return 1;
        }
        return 0;
    }

    /***
     * Saves the held rocket to the level data with the given name.
     * @return 1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int saveRocketLevel(CommandContext<CommandSourceStack> context)  throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ItemStack mainHand = player.getMainHandItem();
        String saveName = StringArgumentType.getString(context,"name");
        if(!mainHand.getItem().equals(Items.FIREWORK_ROCKET))
        {
            return 0;
        }
        CompoundTag fireworkTag = new CompoundTag();
        fireworkTag.putString("Name",saveName);
        fireworkTag.put("Fireworks",mainHand.getTag().getCompound("Fireworks"));
        ServerLevel level = context.getSource().getLevel();
        fireworkWorldAddon worldData = level.getDataStorage().get(fireworkWorldAddon.addonFactory, fireworkWorldAddon.saveName);
        worldData.savePreset(fireworkTag);
        worldData.setDirty();
        return 1;
    }

    /***
     * Gets the player's persistent data, creates the save list if the player does not have it.
     * Saves the held rocket to the list with the given name.
     * @return  1 if successful and 0 otherwise
     * @throws CommandSyntaxException if the CommandSourceStack fails to get player.
     */
    private static int saveRocket(CommandContext<CommandSourceStack> context)  throws CommandSyntaxException
    {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ItemStack mainHand = context.getSource().getPlayerOrException().getMainHandItem();
        String saveName = StringArgumentType.getString(context,"name");
        if(!mainHand.getItem().equals(Items.FIREWORK_ROCKET))
        {
            return 0;
        }
        CompoundTag playerPersistentData = player.getPersistentData();
        ListTag savedFireworksList;
        if(!playerPersistentData.contains("SavedRockets"))
        {
            savedFireworksList = new ListTag();
            CompoundTag fireworkTag = new CompoundTag();
            fireworkTag.putString("Name",saveName);
            fireworkTag.put("Rocket",mainHand.getTag());
            savedFireworksList.add(fireworkTag);
        }
        else
        {
            savedFireworksList = playerPersistentData.getList("SavedRockets", net.minecraft.nbt.Tag.TAG_COMPOUND);
            CompoundTag fireworkTag = new CompoundTag();
            fireworkTag.putString("Name",saveName);
            fireworkTag.put("Rocket",mainHand.getTag());
            savedFireworksList.add(fireworkTag);
        }
        playerPersistentData.put("SavedRockets",savedFireworksList);
        player.sendSystemMessage(Component.literal("Firework saved to player data, Use /cft LoadFromPlayer to retrieve"));
        return 0;
    }
}
