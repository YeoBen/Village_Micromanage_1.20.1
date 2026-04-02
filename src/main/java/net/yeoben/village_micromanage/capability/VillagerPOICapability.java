package net.yeoben.village_micromanage.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class VillagerPOICapability
{
    private BlockPos persistentBed = null;
    private BlockPos persistentJobSite = null;

    // get
    public BlockPos getPersistentBed() {
        return persistentBed;
    }

    public BlockPos getPersistentJobSite() {
        return persistentJobSite;
    }

    // set
    public void setPersistentBed(BlockPos pos) {
        this.persistentBed = pos;
    }

    public void setPersistentJobSite(BlockPos pos) {
        this.persistentJobSite = pos;
    }

    // save to NBT
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if(persistentBed != null) {
            tag.putLong("persistentBed", persistentBed.asLong());
        }
        if(persistentJobSite != null) {
            tag.putLong("persistentJobSite", persistentJobSite.asLong());
        }
        return tag;
    }

    // load from NBT
    public void deserializeNBT(CompoundTag tag) {
        if(tag.contains("persistentBed")) {
            persistentBed = BlockPos.of(tag.getLong("persistentBed"));
        }
        if(tag.contains("persistentJobSite")) {
            persistentJobSite = BlockPos.of(tag.getLong("persistentJobSite"));
        }
    }

}
