package org.multicoder.cft.common.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import org.multicoder.cft.Mod;
import org.multicoder.cft.common.block.BarrageBlock;
import org.multicoder.cft.common.item.BarrageBlockItem;

public class blockinit
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Mod.MOD_ID);

    public static final RegistryObject<Block> BARRAGE = BLOCKS.register("barrage", BarrageBlock::new);

    public static final RegistryObject<Item> BARRAGE_BI = Iteminit.ITEMS.register("barrage",() -> new BarrageBlockItem(BARRAGE.get()));
}
