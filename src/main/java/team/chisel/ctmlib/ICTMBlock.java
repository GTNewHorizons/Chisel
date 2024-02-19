package team.chisel.ctmlib;

import net.minecraft.world.IBlockAccess;

public interface ICTMBlock<T extends ISubmapManager> {

    /**
     * Gets the {@link ISubmapManager} for this block in the world.
     */
    T getManager(IBlockAccess world, int x, int y, int z, int meta);

    /**
     * Gets the {@link ISubmapManager} for this block in item form.
     * 
     * @param meta The meta (damage) of the stack.
     */
    T getManager(int meta);

}
