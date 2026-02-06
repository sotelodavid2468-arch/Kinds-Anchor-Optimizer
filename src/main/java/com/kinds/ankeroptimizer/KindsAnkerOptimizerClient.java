package com.kinds.ankeroptimizer;

import com.kinds.ankeroptimizer.OptOutPayloads.OptOutAckPayload;
import java.util.HashSet;
import java.util.Set;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

public final class KindsAnkerOptimizerClient implements ClientModInitializer {
    private static final Set<String> OPTED_OUT_SERVERS = new HashSet<>();

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(OptOutPayloads.OPT_OUT_ID, (payload, context) -> {
            String key = currentServerKey();
            if (key != null) {
                OPTED_OUT_SERVERS.add(key);
            }
            context.responseSender().sendPacket(new OptOutAckPayload());
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> OPTED_OUT_SERVERS.clear());
    }

    static boolean isOptedOutForCurrentServer() {
        String key = currentServerKey();
        return key != null && OPTED_OUT_SERVERS.contains(key);
    }

    private static String currentServerKey() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return null;
        }
        ServerInfo info = client.getCurrentServerEntry();
        if (info == null || info.address == null) {
            return null;
        }
        return info.address;
    }
}
