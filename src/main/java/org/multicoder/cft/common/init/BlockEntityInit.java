package org.multicoder.cft.common.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import org.multicoder.cft.Mod;
import org.multicoder.cft.common.blockentity.BarrageBlockEntity;

public class BlockEntityInit
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Mod.MOD_ID);

    public static final RegistryObject<BlockEntityType<BarrageBlockEntity>> BARRAGE = BLOCK_ENTITIES.register("barrage",() -> BlockEntityType.Builder.of(BarrageBlockEntity::new, blockinit.BARRAGE.get()).build(null));
}
