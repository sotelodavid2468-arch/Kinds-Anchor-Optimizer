package com.kinds.ankeroptimizer.mixin;

import com.kinds.ankeroptimizer.KindsAnkerOptimizerClient;
import com.kinds.ankeroptimizer.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private int itemUseCooldown;

    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    public ClientWorld world;

    @Shadow
    public HitResult crosshairTarget;

    @Inject(method = "doItemUse", at = @At("TAIL"))
    private void kinds$reduceAnchorUseCooldown(CallbackInfo ci) {
        ModConfig.Config config = ModConfig.get();
        if (!config.enabled || !config.fastAnchorUse) {
            return;
        }
        if (KindsAnkerOptimizerClient.isOptedOutForCurrentServer()) {
            return;
        }
        if (player == null || world == null) {
            return;
        }
        if (!isAnchorContext(config)) {
            return;
        }

        int targetCooldown = config.anchorUseCooldownTicks;
        if (itemUseCooldown > targetCooldown) {
            itemUseCooldown = targetCooldown;
        }
    }

    private boolean isAnchorContext(ModConfig.Config config) {
        if (crosshairTarget instanceof BlockHitResult blockHitResult) {
            BlockState state = world.getBlockState(blockHitResult.getBlockPos());
            if (state.isOf(Blocks.RESPAWN_ANCHOR)) {
                return true;
            }
        }

        if (!config.onlyWhileUsingAnchorItems) {
            return true;
        }

        return hasAnchorItem(player.getMainHandStack()) || hasAnchorItem(player.getOffHandStack());
    }

    private static boolean hasAnchorItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.RESPAWN_ANCHOR || item == Items.GLOWSTONE;
    }
}
