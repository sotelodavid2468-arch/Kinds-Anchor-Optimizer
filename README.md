# Kind's Anchor Optimizer

Essential lag‑smoothing for respawn anchor explosions on Fabric servers.

Kind's Anchor Optimizer is a server‑side Fabric mod that smooths respawn anchor
explosions by queuing them and processing a small number per tick. This reduces
TPS spikes during mass detonations while keeping vanilla behavior intact.

## Features
- Reduces lag spikes from mass respawn anchor explosions
- Server‑side focused; client is optional
- Adaptive per‑tick explosion budget based on player count
- Vanilla behavior preserved
- Server-side opt‑out via config
- Optional server‑requested client opt‑out (per server address)

## How It Works
- Intercepts respawn anchor explosions in the Overworld/End
- Removes the anchor block immediately
- Queues the explosion instead of detonating instantly
- Executes a limited number of queued explosions per server tick

## Usage (Servers)
1. Install Fabric Loader.
2. Download the jar from Modrinth or GitHub Releases.
3. Drop the jar into your `mods` folder and restart the server.

## Configuration
File: `config/kinds_anker_optimizer.json`

```json
{
  "enabled": true,
  "requestClientOptOut": false
}
```

To disable the mod’s behavior (server-side opt‑out), set:
```json
{
  "enabled": false
}
```

To request a client opt‑out on join (server‑controlled), set:
```json
{
  "requestClientOptOut": true
}
```

## Server‑Requested Client Opt‑Out (Implemented)
When `requestClientOptOut` is enabled, the server sends a custom payload to the
client on join. The client disables the mod for that server address and sends an
acknowledgment back. The opt‑out is cleared when the player disconnects.

Channels:
- `kinds_anchor_optimizer:opt_out`
- `kinds_anchor_optimizer:opt_out_ack`

### Plugin Messaging Protocol

Handshake (v1.0.5+)

Step | Direction | Channel | Payload
---|---|---|---
1 | Server → Client | `kinds_anchor_optimizer:opt_out` | Empty
2 | Client → Server | `kinds_anchor_optimizer:opt_out_ack` | Empty

### Example Implementation (Fabric API)

```java
// Server: send opt-out on join when enabled
ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
    if (!ModConfig.get().requestClientOptOut) {
        return;
    }
    ServerPlayNetworking.send(handler.player, new OptOutPayload());
});

// Client: mark server as opted out and send ack
ClientPlayNetworking.registerGlobalReceiver(OptOutPayloads.OPT_OUT_ID, (payload, context) -> {
    String key = currentServerKey();
    if (key != null) {
        OPTED_OUT_SERVERS.add(key);
    }
    context.responseSender().sendPacket(new OptOutAckPayload());
});
```

### Files Added (Opt‑Out Support)
- `src/main/java/com/kinds/ankeroptimizer/KindsAnkerOptimizerClient.java`
- `src/main/java/com/kinds/ankeroptimizer/OptOutNetworking.java`
- `src/main/java/com/kinds/ankeroptimizer/OptOutPayloads.java`


## Compatibility
- Minecraft: 1.21.1–1.21.11
- Loader: Fabric

## License
All Rights Reserved. See `LICENSE`.
