package com.kinds.ankeroptimizer.mixin;

import com.kinds.ankeroptimizer.AnchorExplosionQueue;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RespawnAnchorBlock.class)
public final class RespawnAnchorBlockMixin {
    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private static void kinds$queueExplosion(BlockState state, World world, BlockPos explodedPos, CallbackInfo ci) {
        if (world.isClient) {
            return;
        }

        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }

        world.removeBlock(explodedPos, false);
        AnchorExplosionQueue.enqueue(serverWorld, explodedPos);
        ci.cancel();
    }
}
