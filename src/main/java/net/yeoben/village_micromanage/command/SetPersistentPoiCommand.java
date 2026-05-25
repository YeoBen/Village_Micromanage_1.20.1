package net.yeoben.village_micromanage.command;

import java.util.List;

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

@Mod.EventBusSubscriber
public class SetPersistentPoiCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("villagemicro")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("persistentpoi")
                    .then(Commands.literal("bed")
                        .then(Commands.literal("set")
                            .then(Commands.argument("x", IntegerArgumentType.integer())
                            .then(Commands.argument("y", IntegerArgumentType.integer())
                            .then(Commands.argument("z", IntegerArgumentType.integer())
                            .executes(ctx -> {
                                int x = IntegerArgumentType.getInteger(ctx, "x");
                                int y = IntegerArgumentType.getInteger(ctx, "y");
                                int z = IntegerArgumentType.getInteger(ctx, "z");
                                BlockPos pos = new BlockPos(x, y, z);

                                List<Villager> villagers = ctx.getSource().getLevel()
                                        .getEntitiesOfClass(Villager.class,
                                                ctx.getSource().getEntity().getBoundingBox().inflate(10));

                                if (villagers.isEmpty()) {
                                    ctx.getSource().sendFailure(Component.literal("No villagers found within 10 blocks!"));
                                    return 0;
                                }

                                villagers.get(0).getCapability(VillagerPOIProvider.VILLAGER_POI).ifPresent(cap -> {
                                    cap.setPersistentBed(pos);
                                });

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "Set persistent bed to " + pos.toShortString()), true);
                                return 1;
                            }))))))
                    .then(Commands.literal("jobsite")
                        .then(Commands.literal("set")
                            .then(Commands.argument("x", IntegerArgumentType.integer())
                            .then(Commands.argument("y", IntegerArgumentType.integer())
                            .then(Commands.argument("z", IntegerArgumentType.integer())
                            .executes(ctx -> {
                                int x = IntegerArgumentType.getInteger(ctx, "x");
                                int y = IntegerArgumentType.getInteger(ctx, "y");
                                int z = IntegerArgumentType.getInteger(ctx, "z");
                                BlockPos pos = new BlockPos(x, y, z);

                                List<Villager> villagers = ctx.getSource().getLevel()
                                        .getEntitiesOfClass(Villager.class,
                                                ctx.getSource().getEntity().getBoundingBox().inflate(10));

                                if (villagers.isEmpty()) {
                                    ctx.getSource().sendFailure(Component.literal("No villagers found within 10 blocks!"));
                                    return 0;
                                }

                                villagers.get(0).getCapability(VillagerPOIProvider.VILLAGER_POI).ifPresent(cap -> {
                                    cap.setPersistentBed(pos);
                                });

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "Set persistent bed to " + pos.toShortString()), true);
                                return 1;
                            })))))))
        );
    }
}
