package org.multicoder.cft.common.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class fireworkWorldAddon extends SavedData
{
    private List<CompoundTag> savedFireworks;
    public static final String saveName = "cft-presets";

    public static SavedData.Factory addonFactory = new Factory<>(fireworkWorldAddon::create, fireworkWorldAddon::load,null);

    private fireworkWorldAddon(List<CompoundTag> tags)
    {
        savedFireworks = List.copyOf(tags);
    }

    private fireworkWorldAddon()
    {
        savedFireworks = new ArrayList<>();
    }

    public void savePreset(CompoundTag tag)
    {
        savedFireworks.add(tag);
        this.setDirty();
    }

    public CompoundTag loadPreset(String name)
    {
        AtomicReference<CompoundTag> Preset = new AtomicReference<>();
       savedFireworks.forEach(tag ->{
            if(tag.getString("Name").equals(name))
            {
                Preset.set(tag.getCompound("Fireworks"));
            }
        });
        return Preset.get();
    }

    public static fireworkWorldAddon create()
    {
        return new fireworkWorldAddon();
    }

    public static fireworkWorldAddon load(CompoundTag tag)
    {
        List<CompoundTag> Tags = new ArrayList<>();
        tag.getList("Presets", Tag.TAG_COMPOUND).forEach(T ->
        {
            Tags.add((CompoundTag) T);
        });
        return new fireworkWorldAddon(Tags);
    }
    @Override
    public CompoundTag save(CompoundTag p_77763_)
    {
        ListTag LT = new ListTag();
        savedFireworks.forEach(tag ->
        {
            LT.add(tag);
        });
        p_77763_.put("Presets",LT);
        return p_77763_;
    }
}
