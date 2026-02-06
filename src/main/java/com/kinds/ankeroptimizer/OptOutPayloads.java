package com.kinds.ankeroptimizer;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public final class OptOutPayloads {
    public static final CustomPayload.Id<OptOutPayload> OPT_OUT_ID =
            new CustomPayload.Id<>(Identifier.of("kinds_anchor_optimizer", "opt_out"));
    public static final CustomPayload.Id<OptOutAckPayload> OPT_OUT_ACK_ID =
            new CustomPayload.Id<>(Identifier.of("kinds_anchor_optimizer", "opt_out_ack"));

    public static final PacketCodec<RegistryByteBuf, OptOutPayload> OPT_OUT_CODEC =
            PacketCodec.of((value, buf) -> {}, buf -> new OptOutPayload());
    public static final PacketCodec<RegistryByteBuf, OptOutAckPayload> OPT_OUT_ACK_CODEC =
            PacketCodec.of((value, buf) -> {}, buf -> new OptOutAckPayload());

    private OptOutPayloads() {
    }

    public record OptOutPayload() implements CustomPayload {
        @Override
        public Id<? extends CustomPayload> getId() {
            return OPT_OUT_ID;
        }
    }

    public record OptOutAckPayload() implements CustomPayload {
        @Override
        public Id<? extends CustomPayload> getId() {
            return OPT_OUT_ACK_ID;
        }
    }
}
