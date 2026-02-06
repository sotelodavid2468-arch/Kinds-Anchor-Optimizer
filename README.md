# Kind's Anker Optimizer

Server-side Fabric mod that smooths Respawn Anchor explosions by queuing them and
processing a small number per tick to reduce lag spikes.

## Features
- Reduces lag spikes from mass respawn anchor explosions.
- Server-side only.
- Configurable per-tick explosion budget.

## Configuration
File: `config/kinds_anker_optimizer.json`

```json
{
  "maxExplosionsPerTick": 2
}
```

## Compatibility
- Minecraft: 1.21.1–1.21.11
- Loader: Fabric

## License
All Rights Reserved. See `LICENSE`.
