package team.chisel.compat.fmp;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.cricketcraft.chisel.api.carving.IVariationInfo;
import com.google.common.collect.Lists;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.vec.BlockCoord;
import codechicken.microblock.MicroMaterialRegistry;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory2;
import codechicken.multipart.TMultiPart;
import team.chisel.client.render.FullBrightMicroMaterial;
import team.chisel.init.ChiselBlocks;

public class FMPCompat implements IPartFactory2, IPartConverter {

    public void init() {
        MultiPartRegistry.registerConverter(this);
        MultiPartRegistry.registerPartFactory(this, "chisel_torch");
    }

    @Override
    public Iterable<Block> blockTypes() {
        return Lists.newArrayList(ChiselBlocks.torches);
    }

    @Override
    public TMultiPart convert(World world, BlockCoord bc) {
        Block block = world.getBlock(bc.x, bc.y, bc.z);
        for (int i = 0; i < ChiselBlocks.torches.length; i++) {
            if (block == ChiselBlocks.torches[i]) {
                return new PartChiselTorch(i, world.getBlockMetadata(bc.x, bc.y, bc.z));
            }
        }
        return null;
    }

    @Override
    public TMultiPart createPart(String type, NBTTagCompound nbt) {
        return newPart(type);
    }

    @Override
    public TMultiPart createPart(String type, MCDataInput packet) {
        return newPart(type);
    }

    @Override
    public TMultiPart createPartForClientPreview(String type, NBTTagCompound nbt) {
        return newPart(type);
    }

    private static TMultiPart newPart(String type) {
        if (type.equals("chisel_torch")) {
            return new PartChiselTorch();
        }
        return null;
    }

    public static void registerGlowieVariation(IVariationInfo info, Block block) {
        int meta = info.getVariation()
            .getBlockMeta();

        MicroMaterialRegistry.registerMaterial(
            new FullBrightMicroMaterial(block, meta),
            block.getUnlocalizedName() + ((meta > 0) ? ("_" + meta) : ""));
    }
}
