package net.yeoben.village_micromanage.mixin;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.common.capabilities.Capability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.yeoben.village_micromanage.capability.VillagerPOICapability;
import net.yeoben.village_micromanage.capability.VillagerPOIProvider;

@Mixin(Brain.class)
public class BrainMixin {

    @Inject(method = "eraseMemory", at = @At("RETURN"))
    private <U> void onEraseMemory(MemoryModuleType<U> memoryType, CallbackInfo ci) {

        Brain<?> brain = (Brain<?>) (Object) this;

        if(memoryType != MemoryModuleType.HOME && memoryType != MemoryModuleType.JOB_SITE) {
            return;
        }
    }
}
