package com.kinds.ankeroptimizer;

import com.kinds.ankeroptimizer.OptOutPayloads.OptOutAckPayload;
import com.kinds.ankeroptimizer.OptOutPayloads.OptOutPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

final class OptOutNetworking {
    private OptOutNetworking() {
    }

    static void init() {
        PayloadTypeRegistry.playS2C().register(OptOutPayloads.OPT_OUT_ID, OptOutPayloads.OPT_OUT_CODEC);
        PayloadTypeRegistry.playC2S().register(OptOutPayloads.OPT_OUT_ACK_ID, OptOutPayloads.OPT_OUT_ACK_CODEC);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (!ModConfig.get().requestClientOptOut) {
                return;
            }
            ServerPlayNetworking.send(handler.player, new OptOutPayload());
        });
    }
}
