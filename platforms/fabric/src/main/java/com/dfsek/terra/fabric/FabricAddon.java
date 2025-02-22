package com.dfsek.terra.fabric;

import com.dfsek.tectonic.exception.ConfigException;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.HashMap;
import java.util.Map;

import com.dfsek.terra.api.addon.TerraAddon;
import com.dfsek.terra.api.addon.annotations.Addon;
import com.dfsek.terra.api.addon.annotations.Author;
import com.dfsek.terra.api.addon.annotations.Version;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.event.events.config.pack.ConfigPackPostLoadEvent;
import com.dfsek.terra.api.event.events.config.pack.ConfigPackPreLoadEvent;
import com.dfsek.terra.api.event.functional.FunctionalEventHandler;
import com.dfsek.terra.api.registry.CheckedRegistry;
import com.dfsek.terra.api.registry.exception.DuplicateEntryException;
import com.dfsek.terra.api.util.generic.pair.Pair;
import com.dfsek.terra.api.world.Tree;
import com.dfsek.terra.api.world.biome.TerraBiome;
import com.dfsek.terra.fabric.config.PostLoadCompatibilityOptions;
import com.dfsek.terra.fabric.config.PreLoadCompatibilityOptions;
import com.dfsek.terra.fabric.event.BiomeRegistrationEvent;
import com.dfsek.terra.fabric.util.FabricUtil;


@Addon("terra-fabric")
@Author("Terra")
@Version("1.0.0")
public final class FabricAddon extends TerraAddon {
    private final PlatformImpl terraFabricPlugin;
    private final Map<ConfigPack, Pair<PreLoadCompatibilityOptions, PostLoadCompatibilityOptions>> templates = new HashMap<>();
    
    public FabricAddon(PlatformImpl terraFabricPlugin) {
        this.terraFabricPlugin = terraFabricPlugin;
    }
    
    @Override
    public void initialize() {
        terraFabricPlugin.getEventManager()
                         .getHandler(FunctionalEventHandler.class)
                         .register(this, ConfigPackPreLoadEvent.class)
                         .then(event -> {
                             PreLoadCompatibilityOptions template = new PreLoadCompatibilityOptions();
                             try {
                                 event.loadTemplate(template);
                             } catch(ConfigException e) {
                                 e.printStackTrace();
                             }
            
                             if(template.doRegistryInjection()) {
                                 BuiltinRegistries.CONFIGURED_FEATURE.getEntries().forEach(entry -> {
                                     if(!template.getExcludedRegistryFeatures().contains(entry.getKey().getValue())) {
                                         try {
                                             event.getPack().getCheckedRegistry(Tree.class).register(entry.getKey().getValue().toString(),
                                                                                                     (Tree) entry.getValue());
                                             terraFabricPlugin.getDebugLogger().info(
                                                     "Injected ConfiguredFeature " + entry.getKey().getValue() + " as Tree.");
                                         } catch(DuplicateEntryException ignored) {
                                         }
                                     }
                                 });
                             }
                             templates.put(event.getPack(), Pair.of(template, null));
                         })
                         .global();
        
        terraFabricPlugin.getEventManager()
                         .getHandler(FunctionalEventHandler.class)
                         .register(this, ConfigPackPostLoadEvent.class)
                         .then(event -> {
                             PostLoadCompatibilityOptions template = new PostLoadCompatibilityOptions();
            
                             try {
                                 event.loadTemplate(template);
                             } catch(ConfigException e) {
                                 e.printStackTrace();
                             }
            
                             templates.get(event.getPack()).setRight(template);
                         })
                         .priority(100)
                         .global();
        
        terraFabricPlugin.getEventManager()
                         .getHandler(FunctionalEventHandler.class)
                         .register(this, BiomeRegistrationEvent.class)
                         .then(event -> {
                             terraFabricPlugin.logger().info("Registering biomes...");
                             Registry<Biome> biomeRegistry = event.getRegistryManager().get(Registry.BIOME_KEY);
                             terraFabricPlugin.getConfigRegistry().forEach(pack -> pack.getCheckedRegistry(TerraBiome.class)
                                                                                       .forEach(
                                                                                               (id, biome) -> FabricUtil.registerOrOverwrite(
                                                                                                       biomeRegistry, Registry.BIOME_KEY,
                                                                                                       new Identifier("terra",
                                                                                                                      FabricUtil.createBiomeID(
                                                                                                                              pack, id)),
                                                                                                       FabricUtil.createBiome(biome, pack,
                                                                                                                              event.getRegistryManager())))); // Register all Terra biomes.
                             terraFabricPlugin.logger().info("Biomes registered.");
                         })
                         .global();
    }
    
    
    private void injectTree(CheckedRegistry<Tree> registry, String id, ConfiguredFeature<?, ?> tree) {
        try {
            registry.register(id, (Tree) tree);
        } catch(DuplicateEntryException ignore) {
        }
    }
    
    public Map<ConfigPack, Pair<PreLoadCompatibilityOptions, PostLoadCompatibilityOptions>> getTemplates() {
        return templates;
    }
}
