package org.multicoder.cft.common.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import org.multicoder.cft.Mod;
import org.multicoder.cft.common.block.barrageBlock;
import org.multicoder.cft.common.item.barrageBlockItem;

public class blockInit
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Mod.MOD_ID);

    public static final RegistryObject<Block> BARRAGE = BLOCKS.register("barrage", barrageBlock::new);

    public static final RegistryObject<Item> BARRAGE_BI = itemInit.ITEMS.register("barrage",() -> new barrageBlockItem(BARRAGE.get()));
}
