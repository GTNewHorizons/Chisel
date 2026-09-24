package com.cricketcraft.chisel.mixins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import team.chisel.ctmlib.TextureSubmapResource;

/**
 * Provides generated resources for independently stitched CTM submap sprites.
 * <p>
 * For example, a request for chisel:textures/blocks/__chisel_submap/4x4/2_1/foo.png is resolved from
 * chisel:textures/blocks/foo.png and cropped to the requested cell. This lets Minecraft and other mods treat each
 * submap sprite like an ordinary texture resource.
 */
@Mixin(FallbackResourceManager.class)
public abstract class MixinFallbackResourceManager {

    @Shadow
    public abstract IResource getResource(ResourceLocation location) throws IOException;

    @Shadow
    public abstract List<IResource> getAllResources(ResourceLocation location) throws IOException;

    @Inject(method = "getResource", at = @At("HEAD"), cancellable = true)
    private void chisel$getSubmapResource(ResourceLocation location, CallbackInfoReturnable<IResource> cir)
        throws IOException {

        TextureSubmapResource.SubmapLocation submap = TextureSubmapResource.parse(location);
        if (submap == null) return;

        IResource source = getResource(submap.source());
        cir.setReturnValue(TextureSubmapResource.create(location, source, submap));
    }

    @Inject(method = "getAllResources", at = @At("HEAD"), cancellable = true)
    private void chisel$getAllSubmapResources(ResourceLocation location, CallbackInfoReturnable<List<IResource>> cir)
        throws IOException {

        TextureSubmapResource.SubmapLocation submap = TextureSubmapResource.parse(location);
        if (submap == null) return;

        List<IResource> sources = getAllResources(submap.source());
        List<IResource> resources = new ArrayList<>(sources.size());

        for (IResource source : sources) {
            resources.add(TextureSubmapResource.create(location, source, submap));
        }

        cir.setReturnValue(resources);
    }
}
