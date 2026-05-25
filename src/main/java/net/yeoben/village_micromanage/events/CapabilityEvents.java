package net.yeoben.village_micromanage.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.yeoben.village_micromanage.VillageMicromanage;
import net.yeoben.village_micromanage.capability.VillagerPOIProvider;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber
public class CapabilityEvents {
    private static final ResourceLocation VILLAGER_POI_CAP =
            ResourceLocation.fromNamespaceAndPath(VillageMicromanage.MOD_ID, "vilager_poi");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Villager) {
            if (!event.getCapabilities().containsKey(VILLAGER_POI_CAP)) {
                event.addCapability(VILLAGER_POI_CAP, new VillagerPOIProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLoaded(EntityEvent.EntityConstructing event) {
        if (!(event.getEntity() instanceof Villager villager)) {
            return;
        }

        try {
            villager.getBrain().setMemory(MemoryModuleType.HOME,
                    GlobalPos.of(villager.level().dimension(), new BlockPos(0, 0, 0)));
            System.out.println("Brain is ready!");
        } catch (Exception e) {
            System.out.println("Brain not ready yet: " + e.getMessage());
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if(event.getLevel().isClientSide) {
            return;
        }
        if(!(event.getEntity() instanceof Villager villager)) {
            return;
        }

        villager.getCapability(VillagerPOIProvider.VILLAGER_POI).ifPresent(cap -> {

            if(cap.getPersistentBed() != null) {
                villager.getBrain().setMemory(MemoryModuleType.HOME,
                        GlobalPos.of(villager.level().dimension(), cap.getPersistentBed()));
            }

            if(cap.getPersistentJobSite() != null) {
                villager.getBrain().setMemory(MemoryModuleType.JOB_SITE,
                        GlobalPos.of(villager.level().dimension(), cap.getPersistentJobSite()));
            }
        });
    }
}
