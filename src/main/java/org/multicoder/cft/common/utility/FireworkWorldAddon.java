package org.multicoder.cft.common.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FireworkWorldAddon extends SavedData
{
    private List<CompoundTag> SavedFireworks;
    public static final String SaveName = "cft-presets";

    public static SavedData.Factory DataFactory = new Factory<>(FireworkWorldAddon::create,FireworkWorldAddon::load,null);

    private FireworkWorldAddon(List<CompoundTag> tags)
    {
        SavedFireworks = List.copyOf(tags);
    }

    private FireworkWorldAddon()
    {
        SavedFireworks = new ArrayList<>();
    }

    public void SavePreset(CompoundTag tag)
    {
        SavedFireworks.add(tag);
        this.setDirty();
    }

    public CompoundTag LoadPreset(String name)
    {
        AtomicReference<CompoundTag> Preset = new AtomicReference<>();
       SavedFireworks.forEach(tag ->{
            if(tag.getString("Name").equals(name))
            {
                Preset.set(tag.getCompound("Fireworks"));
            }
        });
        return Preset.get();
    }

    public static FireworkWorldAddon create()
    {
        return new FireworkWorldAddon();
    }

    public static FireworkWorldAddon load(CompoundTag tag)
    {
        List<CompoundTag> Tags = new ArrayList<>();
        tag.getList("Presets", Tag.TAG_COMPOUND).forEach(T ->
        {
            Tags.add((CompoundTag) T);
        });
        return new FireworkWorldAddon(Tags);
    }
    @Override
    public CompoundTag save(CompoundTag p_77763_)
    {
        ListTag LT = new ListTag();
        SavedFireworks.forEach(tag ->
        {
            LT.add(tag);
        });
        p_77763_.put("Presets",LT);
        return p_77763_;
    }
}
