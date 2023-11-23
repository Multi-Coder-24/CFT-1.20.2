package org.multicoder.cft;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.multicoder.cft.common.extra.rocketShape;
import org.multicoder.cft.common.init.blockEntityInit;
import org.multicoder.cft.common.init.blockInit;
import org.multicoder.cft.common.init.itemInit;

@net.neoforged.fml.common.Mod(Mod.MOD_ID)
public class Mod
{
    public static final String MOD_ID = "cft";

    public Mod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::ClientSetup);
        eventBus.addListener(this::AddCreative);
        itemInit.ITEMS.register(eventBus);
        blockInit.BLOCKS.register(eventBus);
        blockEntityInit.BLOCK_ENTITIES.register(eventBus);
    }

    public void ClientSetup(FMLClientSetupEvent event)
    {
        rocketShape.register();
    }

    public void AddCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
        {
            event.accept(blockInit.BARRAGE_BI.get());
        }
    }
}
