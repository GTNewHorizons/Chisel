package com.cricketcraft.chisel.api;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * To be implemented on blocks that "hide" another block inside, so connected textuers can still be accomplished.
 */
public interface IFacade {

    /**
     * Gets the block this facade is acting as.
     * 
     * @param world {@link World}
     * @param x     X coord of your block
     * @param y     Y coord of your block
     * @param z     Z coord of your block
     * @param side  The side being rendered, NOT the side being connected from.
     *              <p>
     *              This value can be -1 if no side is specified. Please handle this appropriately.
     * @return The block inside of your facade block.
     */
    Block getFacade(IBlockAccess world, int x, int y, int z, int side);

    /**
     * Gets the metadata of the block that this facade is acting as.
     * 
     * @param world {@link World}
     * @param x     X coord of your block
     * @param y     Y coord of your block
     * @param z     Z coord of your block
     * @param side  The side being rendered, NOT the side being connected from.
     *              <p>
     *              This value can be -1 if no side is specified. Please handle this appropriately.
     * @return The metadata of your facade block.
     */
    int getFacadeMetadata(IBlockAccess world, int x, int y, int z, int side);

    /**
     * Gets the block this facade is acting as towards the block that is checking for a connection. Implement this if
     * the facade can show different blocks to different neighbours.
     * <p>
     * Defaults to {@link #getFacade(IBlockAccess, int, int, int, int)}.
     *
     * @param world     {@link World}
     * @param x         X coord of your block
     * @param y         Y coord of your block
     * @param z         Z coord of your block
     * @param side      The side being rendered, NOT the side being connected from.
     *                  <p>
     *                  This value can be -1 if no side is specified. Please handle this appropriately.
     * @param fromX     X coord of the block being rendered, which is checking for a connection. NOT the position of
     *                  your block. It is not necessarily adjacent, it can also be diagonal to your block.
     * @param fromY     Y coord of the block being rendered.
     * @param fromZ     Z coord of the block being rendered.
     * @param fromBlock The block being rendered.
     * @param fromMeta  The metadata of the block being rendered.
     * @return The block inside of your facade block, as seen from the block being rendered.
     */
    default Block getFacade(IBlockAccess world, int x, int y, int z, int side, int fromX, int fromY, int fromZ,
        Block fromBlock, int fromMeta) {
        return getFacade(world, x, y, z, side);
    }

    /**
     * Gets the metadata of the block that this facade is acting as towards the block that is checking for a
     * connection. Implement this together with
     * {@link #getFacade(IBlockAccess, int, int, int, int, int, int, int, Block, int)}.
     * <p>
     * Defaults to {@link #getFacadeMetadata(IBlockAccess, int, int, int, int)}.
     *
     * @param world     {@link World}
     * @param x         X coord of your block
     * @param y         Y coord of your block
     * @param z         Z coord of your block
     * @param side      The side being rendered, NOT the side being connected from.
     *                  <p>
     *                  This value can be -1 if no side is specified. Please handle this appropriately.
     * @param fromX     X coord of the block being rendered, which is checking for a connection. NOT the position of
     *                  your block. It is not necessarily adjacent, it can also be diagonal to your block.
     * @param fromY     Y coord of the block being rendered.
     * @param fromZ     Z coord of the block being rendered.
     * @param fromBlock The block being rendered.
     * @param fromMeta  The metadata of the block being rendered.
     * @return The metadata of your facade block, as seen from the block being rendered.
     */
    default int getFacadeMetadata(IBlockAccess world, int x, int y, int z, int side, int fromX, int fromY, int fromZ,
        Block fromBlock, int fromMeta) {
        return getFacadeMetadata(world, x, y, z, side);
    }
}
