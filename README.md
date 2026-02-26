# Kind's Anchor Optimizer

Client-side Fabric mod for faster respawn-anchor use.

This version lowers local right-click use cooldown when you're doing anchor
combos, so anchoring feels faster and less delayed.

## What it does
- Client-side only
- Speeds up anchor use by lowering `itemUseCooldown` during anchor context
- Keeps server-requested opt-out support
- Lets you tune cooldown in config

## Config
File: `config/kinds_anker_optimizer.json`

```json
{
  "enabled": true,
  "fastAnchorUse": true,
  "anchorUseCooldownTicks": 0,
  "onlyWhileUsingAnchorItems": true
}
```

Notes:
- `anchorUseCooldownTicks`: `0` is fastest, `4` matches normal behavior.
- `onlyWhileUsingAnchorItems`: if `true`, optimization applies only when using
  glowstone/respawn anchor or aiming at an anchor block.

## Server opt-out handshake
If a server sends `kinds_anchor_optimizer:opt_out`, the client disables the
optimization for that server address for the current session and replies with
`kinds_anchor_optimizer:opt_out_ack`.

## Compatibility
- Minecraft: 1.21.1–1.21.11
- Loader: Fabric

## License
All Rights Reserved. See `LICENSE`.
