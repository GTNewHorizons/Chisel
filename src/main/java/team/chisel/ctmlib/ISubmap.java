package team.chisel.ctmlib;

import net.minecraft.util.IIcon;

public interface ISubmap {

    /**
     * The width, in icons, of the submap.
     */
    int getWidth();

    /**
     * The height, in icons, of the submap.
     */
    int getHeight();

    IIcon getSubIcon(int x, int y);

}
