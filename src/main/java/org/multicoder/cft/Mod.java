package org.multicoder.cft;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.multicoder.cft.common.extra.RocketShape;
import org.multicoder.cft.common.init.BlockEntityInit;
import org.multicoder.cft.common.init.blockinit;
import org.multicoder.cft.common.init.Iteminit;

@net.neoforged.fml.common.Mod(Mod.MOD_ID)
public class Mod
{
    public static final String MOD_ID = "cft";

    public Mod()
    {
        IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();
        Bus.addListener(this::ClientSetup);
        Bus.addListener(this::AddCreative);
        Iteminit.ITEMS.register(Bus);
        blockinit.BLOCKS.register(Bus);
        BlockEntityInit.BLOCK_ENTITIES.register(Bus);
    }

    public void ClientSetup(FMLClientSetupEvent event)
    {
        RocketShape.Register();
    }

    public void AddCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
        {
            event.accept(blockinit.BARRAGE_BI.get());
        }
    }
}
