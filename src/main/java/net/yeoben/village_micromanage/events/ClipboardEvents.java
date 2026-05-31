package net.yeoben.village_micromanage.events;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yeoben.village_micromanage.capability.VillagerPOIProvider;
import net.yeoben.village_micromanage.item.ModItems;

@Mod.EventBusSubscriber
public class ClipboardEvents {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {

        if(event.getLevel().isClientSide()) {
            return;
        }

        if(event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        ItemStack heldItem = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);
        if(!heldItem.is(ModItems.CLIPBOARD.get())) {
            return;
        }

        if(!(event.getTarget() instanceof Villager villager)) {
            return;
        }

        // blocks opening trade menu
        event.setCanceled(true);

        CompoundTag tag = heldItem.getOrCreateTag();

        if(event.getEntity().isShiftKeyDown()) {

            villager.getCapability(VillagerPOIProvider.VILLAGER_POI).ifPresent(cap -> {
                cap.setPersistentBed(null);
                cap.setPersistentJobSite(null);
            });

            tag.remove("assignmentTarget");
            event.getEntity().sendSystemMessage(
                    Component.literal("Cleared all assigned POIs for this villager.")
            );
        } else {

            tag.putUUID("assignmentTarget", villager.getUUID());
            event.getEntity().sendSystemMessage(
                    Component.literal("Assignment mode: right click a block to assign. Sneak right click to cancel.")
            );
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

        if(event.getLevel().isClientSide()) {
            return;
        }

        if(event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        ItemStack heldItem = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);
        if(!heldItem.is(ModItems.CLIPBOARD.get())) {
            return;
        }

        CompoundTag tag = heldItem.getOrCreateTag();

        if(!tag.hasUUID("assignmentTarget")) {
            return;
        }

        // Block default right click behaviors of blocks
        event.setCanceled(true);

        if(event.getEntity().isShiftKeyDown()) {
            tag.remove("assignmentTarget");
            event.getEntity().sendSystemMessage(
                Component.literal("Assignment cancelled.")
            );
            return;
        }

        java.util.UUID villagerUUID = tag.getUUID("assignmentTarget");
        Villager villager = null;
        for (Villager v : event.getLevel().getEntitiesOfClass(Villager.class,
                new net.minecraft.world.phys.AABB(
                        event.getEntity().blockPosition()).inflate(100))) {
            if (v.getUUID().equals(villagerUUID)) {
                villager = v;
                break;
            }
        }

        if(villager == null) {
            tag.remove("assignmentTarget");
            event.getEntity().sendSystemMessage(
                Component.literal("ERROR: Villager could not be found, assignment cancelled.")
            );
            return;
        }

        BlockPos clickedPos = event.getPos();
        BlockState clickedBlock = event.getLevel().getBlockState(clickedPos);

        boolean isBed = clickedBlock.is(net.minecraft.tags.BlockTags.BEDS);

        villager.getCapability(VillagerPOIProvider.VILLAGER_POI).ifPresent(cap -> {
            if(isBed) {
                cap.setPersistentBed(clickedPos);
                event.getEntity().sendSystemMessage(
                    Component.literal("Villager is assigned to bed " + clickedPos.toShortString())
                );
            } else {
                cap.setPersistentJobSite(clickedPos);
                event.getEntity().sendSystemMessage(
                    Component.literal("Villager is assigned to job site " + clickedPos.toShortString())
                );
            }
        });

        tag.remove("assignmentTarget");
    }
}
