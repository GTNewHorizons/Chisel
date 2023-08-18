package com.cricketcraft.chisel.api.carving;

import com.cricketcraft.chisel.api.rendering.TextureType;

import team.chisel.ctmlib.ISubmapManager;

public interface IVariationInfo extends ISubmapManager {

    ICarvingVariation getVariation();

    String getDescription();

    TextureType getType();
}
