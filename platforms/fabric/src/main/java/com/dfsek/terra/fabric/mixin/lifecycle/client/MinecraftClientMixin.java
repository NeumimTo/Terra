package com.dfsek.terra.fabric.mixin.lifecycle.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.dfsek.terra.api.event.events.platform.PlatformInitializationEvent;
import com.dfsek.terra.fabric.FabricEntryPoint;
import com.dfsek.terra.fabric.generation.TerraGeneratorType;
import com.dfsek.terra.fabric.mixin.access.GeneratorTypeAccessor;


@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/client/util/WindowProvider;createWindow" +
                                                 "(Lnet/minecraft/client/WindowSettings;Ljava/lang/String;Ljava/lang/String;)" +
                                                 "Lnet/minecraft/client/util/Window;",
                                        // sorta arbitrary position, after mod init, before window opens
                                        shift = At.Shift.BEFORE))
    public void injectConstructor(RunArgs args, CallbackInfo callbackInfo) {
        FabricEntryPoint.getPlatform().getEventManager().callEvent(new PlatformInitializationEvent());
        FabricEntryPoint.getPlatform().getConfigRegistry().forEach(pack -> {
            final GeneratorType generatorType = new TerraGeneratorType(pack);
            //noinspection ConstantConditions
            ((GeneratorTypeAccessor) generatorType).setTranslationKey(new LiteralText("Terra:" + pack.getID()));
            GeneratorTypeAccessor.getValues().add(1, generatorType);
        });
    }
}
