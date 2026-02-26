# Kind's Anchor Optimizer

Client-side Fabric mod for cleaner respawn-anchor timing.

This build keeps anchor use responsive but adds a vanilla-safe multiplayer
limit so it does not look like a hard cheat boost.

## What it does
- Client-side only
- Faster anchor timing by lowering local `itemUseCooldown` in anchor context
- Server opt-out handshake is still supported
- Vanilla-safe multiplayer limiter enabled by default

## Setup
No manual setup required. Drop the jar in your mods folder and launch.

## Server opt-out handshake
If a server sends `kinds_anchor_optimizer:opt_out`, this mod disables its speed
logic for that server address during the current session and replies with
`kinds_anchor_optimizer:opt_out_ack`.

## Compatibility
- Minecraft: 1.21.1-1.21.11
- Loader: Fabric

## License
All Rights Reserved. See `LICENSE`.
