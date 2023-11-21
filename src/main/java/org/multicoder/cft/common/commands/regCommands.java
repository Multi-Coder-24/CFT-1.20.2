package org.multicoder.cft.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.multicoder.cft.common.utility.FireworkWorldAddon;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = org.multicoder.cft.Mod.MOD_ID)
public class regCommands
{
    /***
     * Registers all the commands that CFT relies on.
     */
    @SubscribeEvent
    private static void onCommandRegister(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        UtilityCommands.Register(dispatcher);
        fireworkBaseCommands.registerCommands(dispatcher);
        fireworkModifierCommands.registerCommands(dispatcher);
    }

    /***
     * Registers the firework preset storage onto the level
     */
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void onServerStart(ServerStartedEvent event)
    {
        ServerLevel level = event.getServer().getLevel(Level.OVERWORLD);
        if(Objects.nonNull(FireworkWorldAddon.DataFactory) && Objects.nonNull(level))
        {
            FireworkWorldAddon FDA = level.getDataStorage().<FireworkWorldAddon>computeIfAbsent(FireworkWorldAddon.DataFactory,FireworkWorldAddon.SaveName);
            FDA.setDirty();
        }
    }
}
