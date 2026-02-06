package com.kinds.ankeroptimizer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

final class ModConfig {
    private static final String FILE_NAME = "kinds_anker_optimizer.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Config config = new Config();

    private ModConfig() {
    }

    static void init() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        config = load(path);
        save(path, config);
    }

    static Config get() {
        return config;
    }

    private static Config load(Path path) {
        if (!Files.exists(path)) {
            return new Config();
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            Config loaded = GSON.fromJson(reader, Config.class);
            if (loaded == null) {
                return new Config();
            }
            loaded.sanitize();
            return loaded;
        } catch (IOException | JsonSyntaxException e) {
            return new Config();
        }
    }

    private static void save(Path path, Config toSave) {
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path)) {
                GSON.toJson(toSave, writer);
            }
        } catch (IOException ignored) {
        }
    }

    static final class Config {
        boolean enabled = true;
        int maxExplosionsPerTick = 2;

        void sanitize() {
            if (!enabled) {
                return;
            }
            if (maxExplosionsPerTick < 1) {
                maxExplosionsPerTick = 1;
            }
            if (maxExplosionsPerTick > 20) {
                maxExplosionsPerTick = 20;
            }
        }
    }
}
