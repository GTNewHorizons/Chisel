package team.chisel.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import team.chisel.block.tileentity.TileEntityAutoChisel;
import team.chisel.block.tileentity.TileEntityCarvableBeacon;
import team.chisel.block.tileentity.TileEntityPresent;

public class CommonProxy {

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityAutoChisel.class, "autoChisel");
        GameRegistry.registerTileEntity(TileEntityPresent.class, "tile.chisel.present");
        GameRegistry.registerTileEntity(TileEntityCarvableBeacon.class, "tile.chisel.beacon");
    }

    public void preInit() {}

    public void init() {}

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public World getClientWorld() {
        return null;
    }
}
