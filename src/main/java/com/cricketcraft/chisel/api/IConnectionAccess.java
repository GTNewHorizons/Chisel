package com.cricketcraft.chisel.api;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

/**
 * To be implemented on an {@link IBlockAccess} that renders blocks which are only part of a block space, so it can
 * decide connected textures itself instead of reporting the blocks around it.
 * <p>
 * Connected textures ask this for every location they check, while rendering in this world. Render bounds smaller than
 * the block are then a part of the block: they show the matching part of the block's connected texture, instead of the
 * whole texture squeezed into the bounds.
 */
public interface IConnectionAccess {

    /**
     * Whether the given location shows the block being rendered, as seen from the block being rendered.
     *
     * @param x     X coord of the block to check against. NOT the position of the block being rendered.
     * @param y     Y coord of the block to check against.
     * @param z     Z coord of the block to check against.
     * @param side  The side being rendered, NOT the side being connected from.
     *              <p>
     *              This value can be -1 if no side is specified. Please handle this appropriately.
     * @param fromX X coord of the block being rendered. It is not necessarily adjacent, it can also be diagonal to the
     *              location.
     * @param fromY Y coord of the block being rendered.
     * @param fromZ Z coord of the block being rendered.
     * @param block The block being rendered.
     * @param meta  The metadata of the block being rendered.
     * @return True if the location shows the given block and metadata.
     */
    boolean matches(int x, int y, int z, int side, int fromX, int fromY, int fromZ, Block block, int meta);
}
