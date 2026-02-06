package com.kinds.ankeroptimizer;

import java.util.ArrayDeque;
import java.util.Deque;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class AnchorExplosionQueue {
    private static final Deque<QueuedExplosion> QUEUE = new ArrayDeque<>();

    private AnchorExplosionQueue() {
    }

    static void init() {
        ServerTickEvents.END_SERVER_TICK.register(AnchorExplosionQueue::onEndServerTick);
    }

    public static void enqueue(ServerWorld world, BlockPos pos) {
        QUEUE.addLast(new QueuedExplosion(world, pos.toImmutable()));
    }

    private static void onEndServerTick(MinecraftServer server) {
        if (!ModConfig.get().enabled) {
            return;
        }

        int budget = ModConfig.get().maxExplosionsPerTick;
        while (budget > 0 && !QUEUE.isEmpty()) {
            QueuedExplosion next = QUEUE.pollFirst();
            if (next == null) {
                continue;
            }

            ServerWorld world = next.world;
            BlockPos pos = next.pos;
            Vec3d center = Vec3d.ofCenter(pos);

            world.createExplosion(
                    null,
                    world.getDamageSources().badRespawnPoint(center),
                    null,
                    center,
                    5.0f,
                    true,
                    World.ExplosionSourceType.BLOCK
            );

            budget--;
        }
    }

    private record QueuedExplosion(ServerWorld world, BlockPos pos) {
    }
}
