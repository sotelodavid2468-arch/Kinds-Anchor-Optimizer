# Kind's Anker Optimizer

Essential lag‑smoothing for respawn anchor explosions on Fabric servers.

Kind's Anker Optimizer is a server‑side Fabric mod that smooths respawn anchor
explosions by queuing them and processing a small number per tick. This reduces
TPS spikes during mass detonations while keeping vanilla behavior intact.

## Features
- Reduces lag spikes from mass respawn anchor explosions
- Server‑side only (no client required)
- Configurable per‑tick explosion budget
- Vanilla behavior preserved
 - Server-side opt‑out via config

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
  "maxExplosionsPerTick": 2
}
```

Recommended values:
- `2` for small servers
- `4` for medium servers
- `6` for larger servers

To disable the mod’s behavior (server-side opt‑out), set:
```json
{
  "enabled": false
}
```

## Compatibility
- Minecraft: 1.21.1–1.21.11
- Loader: Fabric

## License
All Rights Reserved. See `LICENSE`.
