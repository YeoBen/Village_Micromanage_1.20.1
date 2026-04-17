package net.yeoben.village_micromanage.events;

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
}
