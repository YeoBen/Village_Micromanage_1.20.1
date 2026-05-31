package net.yeoben.village_micromanage.command;

import com.google.common.eventbus.Subscribe;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yeoben.village_micromanage.capability.VillagerPOIProvider;

import java.util.List;

@Mod.EventBusSubscriber
public class RefreshVillagerBrainCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("villagemicro")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("refreshBrain")
                    .then(Commands.literal("closestVillager")
                        .executes(ctx -> {

                            List<Villager> villagers = ctx.getSource().getLevel()
                                    .getEntitiesOfClass(Villager.class,
                                            ctx.getSource().getEntity().getBoundingBox().inflate(10));

                            if (villagers.isEmpty()) {
                                ctx.getSource().sendFailure(Component.literal("No villagers found within 10 blocks!"));
                                return 0;
                            }

                            Villager villager = villagers.get(0);

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

                            ctx.getSource().sendSystemMessage(
                                    Component.literal("Refreshed the POIs of the closest villager")
                            );

                            return 1;
                        })))
        );
    }
}
