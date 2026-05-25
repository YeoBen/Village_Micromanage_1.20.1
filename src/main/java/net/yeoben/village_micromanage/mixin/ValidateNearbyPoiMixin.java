package net.yeoben.village_micromanage.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.ValidateNearbyPoi;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.yeoben.village_micromanage.capability.VillagerPOIProvider;

@Mixin(ValidateNearbyPoi.class)
public class ValidateNearbyPoiMixin {

    @Redirect(
            method = "lambda$create$0",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryAccessor;erase()V"
            )
    )
    private static void onPoiErase(MemoryAccessor<?, GlobalPos> accessor,
                                   @Local LivingEntity entity,
                                   @Local MemoryModuleType<GlobalPos> poiMemoryType) {

        if(!(entity instanceof Villager villager)) {
            accessor.erase();
            return;
        }

        villager.getCapability(VillagerPOIProvider.VILLAGER_POI).ifPresent(cap -> {

            if(cap.getPersistentBed() != null) {
                accessor.set(GlobalPos.of(villager.level().dimension(), cap.getPersistentBed()));
            } else if (cap.getPersistentJobSite() != null) {
                accessor.set(GlobalPos.of(villager.level().dimension(), cap.getPersistentJobSite()));
            } else {
                accessor.erase();
            }
        });
    }
}
