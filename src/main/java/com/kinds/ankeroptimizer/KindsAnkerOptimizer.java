package com.kinds.ankeroptimizer;

import net.fabricmc.api.ModInitializer;

public final class KindsAnkerOptimizer implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig.init();
        AnchorExplosionQueue.init();
        OptOutNetworking.init();
    }
}
