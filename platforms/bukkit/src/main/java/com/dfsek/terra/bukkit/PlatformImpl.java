package com.dfsek.terra.bukkit;

import com.dfsek.tectonic.exception.LoadException;
import com.dfsek.tectonic.loading.TypeRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.Locale;
import java.util.Optional;

import com.dfsek.terra.AbstractPlatform;
import com.dfsek.terra.api.util.Logger;
import com.dfsek.terra.api.addon.TerraAddon;
import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.handle.ItemHandle;
import com.dfsek.terra.api.handle.WorldHandle;
import com.dfsek.terra.api.world.biome.Biome;
import com.dfsek.terra.bukkit.handles.BukkitItemHandle;
import com.dfsek.terra.bukkit.handles.BukkitWorldHandle;
import com.dfsek.terra.bukkit.world.BukkitBiome;
import com.dfsek.terra.util.logging.JavaLogger;


public class PlatformImpl extends AbstractPlatform {
    private final ItemHandle itemHandle = new BukkitItemHandle();
    
    private final WorldHandle handle = new BukkitWorldHandle();
    
    private final TerraBukkitPlugin plugin;
    
    public PlatformImpl(TerraBukkitPlugin plugin) {
        this.plugin = plugin;
        load();
    }
    
    public TerraBukkitPlugin getPlugin() {
        return plugin;
    }
    
    @Override
    public boolean reload() {
        return false;
    }
    
    @Override
    public String platformName() {
        return "Bukkit";
    }
    
    @Override
    public void runPossiblyUnsafeTask(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }
    
    @Override
    public WorldHandle getWorldHandle() {
        return handle;
    }
    
    @Override
    public File getDataFolder() {
        return plugin.getDataFolder();
    }
    
    @Override
    public ItemHandle getItemHandle() {
        return itemHandle;
    }
    
    @Override
    public void register(TypeRegistry registry) {
        super.register(registry);
        registry
                .registerLoader(BlockState.class, (t, o, l) -> handle.createBlockData((String) o))
                .registerLoader(Biome.class, (t, o, l) -> parseBiome((String) o))
                .registerLoader(EntityType.class, (t, o, l) -> EntityType.valueOf((String) o));
        
    }
    
    @Override
    protected Logger createLogger() {
        return new JavaLogger(plugin.getLogger());
    }
    
    @Override
    protected Optional<TerraAddon> getPlatformAddon() {
        return Optional.of(new BukkitAddon(this));
    }
    
    private BukkitBiome parseBiome(String id) throws LoadException {
        if(!id.startsWith("minecraft:")) throw new LoadException("Invalid biome identifier " + id);
        return new BukkitBiome(org.bukkit.block.Biome.valueOf(id.toUpperCase(Locale.ROOT).substring(10)));
    }
}
