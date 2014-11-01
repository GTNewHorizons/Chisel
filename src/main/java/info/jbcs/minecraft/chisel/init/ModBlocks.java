package info.jbcs.minecraft.chisel.init;

import cpw.mods.fml.common.registry.GameRegistry;
import info.jbcs.minecraft.chisel.Chisel;
import info.jbcs.minecraft.chisel.Configurations;
import info.jbcs.minecraft.chisel.block.*;
import info.jbcs.minecraft.chisel.carving.CarvableHelper;
import info.jbcs.minecraft.chisel.carving.CarvableVariation;
import info.jbcs.minecraft.chisel.carving.Carving;
import info.jbcs.minecraft.chisel.item.ItemCarvable;
import info.jbcs.minecraft.chisel.item.ItemMarbleSlab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {
    public static BlockCarvable marble;
    public static BlockCarvable marblePillar;
    public static BlockCarvable limestone;
    public static BlockMarbleSlab marbleSlab;
    public static BlockMarbleSlab limestoneSlab;
    public static BlockMarbleSlab marblePillarSlab;
    public static BlockCarvable cobblestone;
    public static BlockMarbleWall cobblestoneWall;
    public static BlockCarvableGlass glass;
    public static BlockCarvablePane paneGlass;
    public static BlockCarvable sandstone;
    public static BlockCarvable sandstoneScribbles;
    public static BlockConcrete concrete;
    public static BlockRoadLine roadLine;
    public static BlockCarvable iron;
    public static BlockCarvable gold;
    public static BlockCarvable diamond;
    public static BlockLightstoneCarvable lightstone;
    public static BlockCarvable lapis;
    public static BlockCarvable emerald;
    public static BlockCarvable netherBrick;
    public static BlockCarvable netherrack;
    public static BlockCarvable cobblestoneMossy;
    public static BlockCarvable stoneBrick;
    public static BlockMarbleStairs marbleStairs;
    public static BlockMarbleStairs limestoneStairs;
    public static BlockCarvablePane paneIron;
    public static BlockMarbleIce ice;
    public static BlockMarbleIce icePillar;
    public static BlockMarbleIceStairs iceStairs;
    public static BlockCarvable[] planks = new BlockCarvable[6];
    public static BlockCarvable obsidian;
    public static BlockCarvablePowered redstone;
    public static BlockHolystone holystone;
    public static BlockLavastone lavastone;
    public static BlockCarvable fantasy;
    public static BlockCarvable carpet;
    public static BlockMarbleCarpet carpetFloor;
    public static BlockCarvable bookshelf;
    public static BlockCarvable tyrian;
    public static BlockCarvable dirt;
    public static BlockCloud cloud;
    public static BlockCarvable temple;
    public static BlockCarvable templeMossy;
    public static BlockCarvable factory;
    public static BlockCarvablePane paperWall;
    public static BlockCarvable woolenClay;
    public static BlockCarvable laboratory;
    public static BlockCarvable pumpkin;
    public static BlockCarvable jackOLantern;
    public static Block autoChisel;
    public static BlockSnakestone snakestone;
    public static BlockSnakestone sandSnakestone;
    public static BlockSnakestoneObsidian obsidianSnakestone;

    public static BlockSpikes spikeTrap;

    // 1.7
    public static BlockCarvableGlass[] stainedGlass = new BlockCarvableGlass[4];
    public static BlockCarvablePane[] stainedGlassPane = new BlockCarvablePane[8];

    public static void load()
    {
        if(Configurations.featureEnabled("autoChisel")){
            autoChisel = new BlockAutoChisel().setBlockTextureName("Chisel:autoChisel").setCreativeTab(ModTabs.tabChisel);
            GameRegistry.registerBlock(autoChisel, autoChisel.getUnlocalizedName());
            Chisel.proxy.registerTileEntities();
        }

        if(Configurations.featureEnabled("marble"))
        {
            marble = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeStone);
            marble.carverHelper.setChiselBlockName("Marble");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.0.desc"), 0, "marble");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.1.desc"), 1, "marble/a1-stoneornamental-marblebrick");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.2.desc"), 2, "marble/a1-stoneornamental-marbleclassicpanel");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.3.desc"), 3, "marble/a1-stoneornamental-marbleornate");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.4.desc"), 4, "marble/panel");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.5.desc"), 5, "marble/block");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.6.desc"), 6, "marble/terrain-pistonback-marblecreeperdark");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.7.desc"), 7, "marble/terrain-pistonback-marblecreeperlight");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.8.desc"), 8, "marble/a1-stoneornamental-marblecarved");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.9.desc"), 9, "marble/a1-stoneornamental-marblecarvedradial");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.10.desc"), 10, "marble/terrain-pistonback-marbledent");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.11.desc"), 11, "marble/terrain-pistonback-marbledent-small");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.12.desc"), 12, "marble/marble-bricks");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.13.desc"), 13, "marble/marble-arranged-bricks");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.14.desc"), 14, "marble/marble-fancy-bricks");
            marble.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marble.15.desc"), 15, "marble/marble-blocks");
            marble.carverHelper.register(marble, "marble");
            OreDictionary.registerOre("marble", marble);
            Carving.chisel.registerOre("marble", "marble");

            marbleSlab = (BlockMarbleSlab) new BlockMarbleSlab(marble).setHardness(2.0F).setResistance(10F);
            marbleSlab.carverHelper.setChiselBlockName("Marble Slab");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.0.desc"), 0, "marble");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.1.desc"), 1, "marbleslab/a1-stoneornamental-marblebrick");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.2.desc"), 2, "marbleslab/a1-stoneornamental-marbleclassicpanel");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.3.desc"), 3, "marbleslab/a1-stoneornamental-marbleornate");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.4.desc"), 4, "marbleslab/a1-stoneornamental-marblepanel");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.5.desc"), 5, "marbleslab/terrain-pistonback-marble");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.6.desc"), 6, "marbleslab/terrain-pistonback-marblecreeperdark");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.7.desc"), 7, "marbleslab/terrain-pistonback-marblecreeperlight");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.8.desc"), 8, "marbleslab/a1-stoneornamental-marblecarved");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.9.desc"), 9, "marbleslab/a1-stoneornamental-marblecarvedradial");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.10.desc"), 10, "marbleslab/terrain-pistonback-marbledent");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.11.desc"), 11, "marbleslab/terrain-pistonback-marbledent-small");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.12.desc"), 12, "marbleslab/marble-bricks");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.13.desc"), 13, "marbleslab/marble-arranged-bricks");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.14.desc"), 14, "marbleslab/marble-fancy-bricks");
            marbleSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleSlab.15.desc"), 15, "marbleslab/marble-blocks");
            marbleSlab.carverHelper.register(marbleSlab, "marble_slab", ItemMarbleSlab.class);

            if(Configurations.featureEnabled("marblePillar"))
            {
                if(Configurations.oldPillars)
                {
                    marblePillar = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeStone);
                    marblePillar.carverHelper.setChiselBlockName("Marble Pillar");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.0.desc"), 0, "marblepillarold/column");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.1.desc"), 1, "marblepillarold/capstone");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.2.desc"), 2, "marblepillarold/base");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.3.desc"), 3, "marblepillarold/small");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.4.desc"), 4, "marblepillarold/pillar-carved");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.5.desc"), 5, "marblepillarold/a1-stoneornamental-marblegreek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.6.desc"), 6, "marblepillarold/a1-stonepillar-greek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.7.desc"), 7, "marblepillarold/a1-stonepillar-plain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.8.desc"), 8, "marblepillarold/a1-stonepillar-greektopplain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.9.desc"), 9, "marblepillarold/a1-stonepillar-plaintopplain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.10.desc"), 10, "marblepillarold/a1-stonepillar-greekbottomplain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.11.desc"), 11, "marblepillarold/a1-stonepillar-plainbottomplain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.12.desc"), 12, "marblepillarold/a1-stonepillar-greektopgreek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.13.desc"), 13, "marblepillarold/a1-stonepillar-plaintopgreek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.14.desc"), 14, "marblepillarold/a1-stonepillar-greekbottomgreek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarOld.15.desc"), 15, "marblepillarold/a1-stonepillar-plainbottomgreek");
                } else
                {
                    marblePillar = (BlockCarvable) new BlockMarblePillar(Material.rock).setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeStone);
                    marblePillar.carverHelper.setChiselBlockName("Marble Pillar");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.0.desc"), 0, "marblepillar/pillar");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.1.desc"), 1, "marblepillar/default");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.2.desc"), 2, "marblepillar/simple");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.3.desc"), 3, "marblepillar/convex");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.4.desc"), 4, "marblepillar/rough");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.5.desc"), 5, "marblepillar/greekdecor");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.6.desc"), 6, "marblepillar/greekgreek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.7.desc"), 7, "marblepillar/greekplain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.8.desc"), 8, "marblepillar/plaindecor");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.9.desc"), 9, "marblepillar/plaingreek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.10.desc"), 10, "marblepillar/plainplain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.11.desc"), 11, "marblepillar/widedecor");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.12.desc"), 12, "marblepillar/widegreek");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.13.desc"), 13, "marblepillar/wideplain");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.14.desc"), 14, "marblepillar/carved");
                    marblePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillar.15.desc"), 15, "marblepillar/ornamental");
                }
                marblePillar.carverHelper.register(marblePillar, "marble_pillar");
                Carving.chisel.setGroupClass("marble_pillar", "marble");

                marblePillarSlab = (BlockMarbleSlab) new BlockMarbleSlab(marblePillar).setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeStone);
                marblePillarSlab.carverHelper.setChiselBlockName("Marble Pillar Slab");
                if(Configurations.oldPillars)
                {
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.0.desc"), 0, "marblepillarslabold/column");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.1.desc"), 1, "marblepillarslabold/capstone");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.2.desc"), 2, "marblepillarslabold/base");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.3.desc"), 3, "marblepillarslabold/small");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.4.desc"), 4, "marblepillarslabold/pillar-carved");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.5.desc"), 5, "marblepillarslabold/a1-stoneornamental-marblegreek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.6.desc"), 6, "marblepillarslabold/a1-stonepillar-greek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.7.desc"), 7, "marblepillarslabold/a1-stonepillar-plain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.8.desc"), 8, "marblepillarslabold/a1-stonepillar-greektopplain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.9.desc"), 9, "marblepillarslabold/a1-stonepillar-plaintopplain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.10.desc"), 10, "marblepillarslabold/a1-stonepillar-greekbottomplain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.11.desc"), 11, "marblepillarslabold/a1-stonepillar-plainbottomplain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.12.desc"), 12, "marblepillarslabold/a1-stonepillar-greektopgreek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.13.desc"), 13, "marblepillarslabold/a1-stonepillar-plaintopgreek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.14.desc"), 14, "marblepillarslabold/a1-stonepillar-greekbottomgreek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlabOld.15.desc"), 15, "marblepillarslabold/a1-stonepillar-plainbottomgreek");
                } else
                {
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.0.desc"), 0, "marblepillarslab/pillar");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.1.desc"), 1, "marblepillarslab/default");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.2.desc"), 2, "marblepillarslab/simple");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.3.desc"), 3, "marblepillarslab/convex");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.4.desc"), 4, "marblepillarslab/rough");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.5.desc"), 5, "marblepillarslab/greekdecor");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.6.desc"), 6, "marblepillarslab/greekgreek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.7.desc"), 7, "marblepillarslab/greekplain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.8.desc"), 8, "marblepillarslab/plaindecor");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.9.desc"), 9, "marblepillarslab/plaingreek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.10.desc"), 10, "marblepillarslab/plainplain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.11.desc"), 11, "marblepillarslab/widedecor");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.12.desc"), 12, "marblepillarslab/widegreek");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.13.desc"), 13, "marblepillarslab/wideplain");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.14.desc"), 14, "marblepillarslab/carved");
                    marblePillarSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marblePillarSlab.15.desc"), 15, "marblepillarslab/ornamental");
                }
                marblePillarSlab.carverHelper.register(marblePillarSlab, "marble_pillar_slab", ItemMarbleSlab.class);
            }

            BlockMarbleStairsMaker makerMarbleStairs = new BlockMarbleStairsMaker(marble);
            makerMarbleStairs.carverHelper.setChiselBlockName("Marble Stairs");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.0.desc"), 0, "marble");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.6.desc"), 1, "marbleslab/a1-stoneornamental-marblebrick");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.2.desc"), 2, "marbleslab/a1-stoneornamental-marbleclassicpanel");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.3.desc"), 3, "marbleslab/a1-stoneornamental-marbleornate");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.4.desc"), 4, "marbleslab/a1-stoneornamental-marblepanel");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.5.desc"), 5, "marbleslab/terrain-pistonback-marble");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.6.desc"), 6, "marbleslab/terrain-pistonback-marblecreeperdark");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.7.desc"), 7, "marbleslab/terrain-pistonback-marblecreeperlight");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.8.desc"), 8, "marbleslab/a1-stoneornamental-marblecarved");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.9.desc"), 9, "marbleslab/a1-stoneornamental-marblecarvedradial");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.10.desc"), 10, "marbleslab/terrain-pistonback-marbledent");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.11.desc"), 11, "marbleslab/terrain-pistonback-marbledent-small");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.12.desc"), 12, "marbleslab/marble-bricks");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.13.desc"), 13, "marbleslab/marble-arranged-bricks");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.14.desc"), 14, "marbleslab/marble-fancy-bricks");
            makerMarbleStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.marbleStairs.15.desc"), 15, "marbleslab/marble-blocks");
            makerMarbleStairs.create("marble_stairs");
        }

        if(Configurations.featureEnabled("limestone"))
        {
            limestone = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeStone);
            limestone.carverHelper.setChiselBlockName("Limestone");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.0.desc"), 0, "limestone");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.1.desc"), 1, "limestone/terrain-cobbsmalltilelight");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.2.desc"), 2, "limestone/terrain-cob-frenchlight");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.3.desc"), 3, "limestone/terrain-cob-french2light");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.4.desc"), 4, "limestone/terrain-cobmoss-creepdungeonlight");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.5.desc"), 5, "limestone/terrain-cob-smallbricklight");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.6.desc"), 6, "limestone/terrain-mossysmalltilelight");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.7.desc"), 7, "limestone/terrain-pistonback-dungeon");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.8.desc"), 8, "limestone/terrain-pistonback-dungeonornate");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.9.desc"), 9, "limestone/terrain-pistonback-dungeonvent");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.10.desc"), 10, "limestone/terrain-pistonback-lightcreeper");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.11.desc"), 11, "limestone/terrain-pistonback-lightdent");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.12.desc"), 12, "limestone/terrain-pistonback-lightemboss");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.13.desc"), 13, "limestone/terrain-pistonback-lightfour");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.14.desc"), 14, "limestone/terrain-pistonback-lightmarker");
            limestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestone.15.desc"), 15, "limestone/terrain-pistonback-lightpanel");
            limestone.carverHelper.register(limestone, "limestone");
            OreDictionary.registerOre("limestone", limestone);
            Carving.chisel.registerOre("limestone", "limestone");

            limestoneSlab = (BlockMarbleSlab) new BlockMarbleSlab(limestone).setHardness(2.0F).setResistance(10F);
            limestoneSlab.carverHelper.setChiselBlockName("Limestone Slab");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.0.desc"), 0, "limestone");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.1.desc"), 1, "limestone/terrain-cobbsmalltilelight");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.2.desc"), 2, "limestone/terrain-cob-frenchlight");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.3.desc"), 3, "limestone/terrain-cob-french2light");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.4.desc"), 4, "limestone/terrain-cobmoss-creepdungeonlight");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.5.desc"), 5, "limestone/terrain-cob-smallbricklight");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.6.desc"), 6, "limestone/terrain-mossysmalltilelight");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.7.desc"), 7, "limestone/terrain-pistonback-dungeon");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.8.desc"), 8, "limestone/terrain-pistonback-dungeonornate");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.9.desc"), 9, "limestone/terrain-pistonback-dungeonvent");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.10.desc"), 10, "limestone/terrain-pistonback-lightcreeper");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.11.desc"), 11, "limestone/terrain-pistonback-lightdent");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.12.desc"), 12, "limestone/terrain-pistonback-lightemboss");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.13.desc"), 13, "limestone/terrain-pistonback-lightfour");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.14.desc"), 14, "limestone/terrain-pistonback-lightmarker");
            limestoneSlab.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneSlab.15.desc"), 15, "limestone/terrain-pistonback-lightpanel");
            limestoneSlab.carverHelper.register(limestoneSlab, "limestone_slab", ItemMarbleSlab.class);

            BlockMarbleStairsMaker makerLimestoneStairs = new BlockMarbleStairsMaker(limestone);
            makerLimestoneStairs.carverHelper.setChiselBlockName("Limestone Stairs");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.0.desc"), 0, "limestone");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.1.desc"), 1, "limestone/terrain-cobbsmalltilelight");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.2.desc"), 2, "limestone/terrain-cob-frenchlight");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.3.desc"), 3, "limestone/terrain-cob-french2light");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.4.desc"), 4, "limestone/terrain-cobmoss-creepdungeonlight");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.5.desc"), 5, "limestone/terrain-cob-smallbricklight");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.6.desc"), 6, "limestone/terrain-mossysmalltilelight");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.7.desc"), 7, "limestone/terrain-pistonback-dungeon");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.8.desc"), 8, "limestone/terrain-pistonback-dungeonornate");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.9.desc"), 9, "limestone/terrain-pistonback-dungeonvent");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.10.desc"), 10, "limestone/terrain-pistonback-lightcreeper");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.11.desc"), 11, "limestone/terrain-pistonback-lightdent");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.12.desc"), 12, "limestone/terrain-pistonback-lightemboss");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.13.desc"), 13, "limestone/terrain-pistonback-lightfour");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.14.desc"), 14, "limestone/terrain-pistonback-lightmarker");
            makerLimestoneStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.limestoneStairs.15.desc"), 15, "limestone/terrain-pistonback-lightpanel");
            makerLimestoneStairs.create("limestone_stairs");
        }

        if(Configurations.featureEnabled("cobblestone"))
        {
            cobblestone = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeStone);
            Carving.chisel.addVariation("cobblestone", Blocks.cobblestone, 0, 0);
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.0.desc"), 1, "cobblestone/terrain-cobb-brickaligned");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.1.desc"), 2, "cobblestone/terrain-cob-detailedbrick");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.2.desc"), 3, "cobblestone/terrain-cob-smallbrick");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.3.desc"), 4, "cobblestone/terrain-cobblargetiledark");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.4.desc"), 5, "cobblestone/terrain-cobbsmalltile");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.5.desc"), 6, "cobblestone/terrain-cob-french");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.6.desc"), 7, "cobblestone/terrain-cob-french2");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.7.desc"), 8, "cobblestone/terrain-cobmoss-creepdungeon");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.8.desc"), 9, "cobblestone/terrain-mossysmalltiledark");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.9.desc"), 10, "cobblestone/terrain-pistonback-dungeontile");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.10.desc"), 11, "cobblestone/terrain-pistonback-darkcreeper");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.11.desc"), 12, "cobblestone/terrain-pistonback-darkdent");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.12.desc"), 13, "cobblestone/terrain-pistonback-darkemboss");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.13.desc"), 14, "cobblestone/terrain-pistonback-darkmarker");
            cobblestone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.cobblestone.14.desc"), 15, "cobblestone/terrain-pistonback-darkpanel");
            cobblestone.carverHelper.register(cobblestone, "cobblestone");
            Carving.chisel.registerOre("cobblestone", "cobblestone");
        }

        if(Configurations.featureEnabled("glass"))
        {
            glass = (BlockCarvableGlass) new BlockCarvableGlass().setHardness(0.3F).setStepSound(Block.soundTypeGlass);
            Carving.chisel.addVariation("glass", Blocks.glass, 0, 0);
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.1.desc"), 1, "glass/terrain-glassbubble");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.2.desc"), 2, "glass/terrain-glass-chinese");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.3.desc"), 3, "glass/japanese");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.4.desc"), 4, "glass/terrain-glassdungeon");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.5.desc"), 5, "glass/terrain-glasslight");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.6.desc"), 6, "glass/terrain-glassnoborder");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.7.desc"), 7, "glass/terrain-glass-ornatesteel");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.8.desc"), 8, "glass/terrain-glass-screen");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.9.desc"), 9, "glass/terrain-glassshale");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.10.desc"), 10, "glass/terrain-glass-steelframe");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.11.desc"), 11, "glass/terrain-glassstone");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.12.desc"), 12, "glass/terrain-glassstreak");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.13.desc"), 13, "glass/terrain-glass-thickgrid");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.14.desc"), 14, "glass/terrain-glass-thingrid");
            glass.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.glass.15.desc"), 15, "glass/a1-glasswindow-ironfencemodern");
            glass.carverHelper.register(glass, "glass");
            Carving.chisel.registerOre("glass", "glass");
        }

        if(Configurations.featureEnabled("sandstone"))
        {
            sandstone = (BlockCarvable) new BlockCarvable().setStepSound(Block.soundTypeStone).setHardness(0.8F);
            Carving.chisel.addVariation("sandstone", Blocks.sandstone, 0, 0);
            Carving.chisel.addVariation("sandstone", Blocks.sandstone, 1, 1);
            Carving.chisel.addVariation("sandstone", Blocks.sandstone, 2, 2);
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.3.desc"), 3, "sandstone/faded");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.4.desc"), 4, "sandstone/column");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.5.desc"), 5, "sandstone/capstone");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.6.desc"), 6, "sandstone/small");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.7.desc"), 7, "sandstone/base");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.8.desc"), 8, "sandstone/smooth");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.9.desc"), 9, "sandstone/smooth-cap");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.10.desc"), 10, "sandstone/smooth-small");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.11.desc"), 11, "sandstone/smooth-base");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.12.desc"), 12, "sandstone/block");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.13.desc"), 13, "sandstone/blocks");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.14.desc"), 14, "sandstone/mosaic");
            sandstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstone.15.desc"), 15, "sandstone/horizontal-tiles");
            sandstone.carverHelper.register(sandstone, "sandstone");
            Carving.chisel.registerOre("sandstone", "sandstone");

            if(Configurations.featureEnabled("snakeSandstone"))
            {
                sandSnakestone = (BlockSnakestone) new BlockSnakestone("Chisel:snakestone/sandsnake/").setBlockName("snakestoneSand");
                GameRegistry.registerBlock(sandSnakestone, ItemCarvable.class, "sand_snakestone");
                //TODO- eat me!
                //LanguageRegistry.addName(new ItemStack(sandSnakestone, 1, 1), "Sandstone snake block head");
                //LanguageRegistry.addName(new ItemStack(sandSnakestone, 1, 13), "Sandstone snake block body");
                Carving.chisel.addVariation("sandstone", sandSnakestone, 1, 16);
                Carving.chisel.addVariation("sandstone", sandSnakestone, 13, 17);
            }
        }

        if(Configurations.featureEnabled("sandstoneScribbles"))
        {
            sandstoneScribbles = (BlockCarvable) new BlockCarvable().setStepSound(Block.soundTypeStone).setHardness(0.8F);
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 0, "sandstone-scribbles/scribbles-0");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 1, "sandstone-scribbles/scribbles-1");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 2, "sandstone-scribbles/scribbles-2");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 3, "sandstone-scribbles/scribbles-3");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 4, "sandstone-scribbles/scribbles-4");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 5, "sandstone-scribbles/scribbles-5");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 6, "sandstone-scribbles/scribbles-6");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 7, "sandstone-scribbles/scribbles-7");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 8, "sandstone-scribbles/scribbles-8");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 9, "sandstone-scribbles/scribbles-9");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 10, "sandstone-scribbles/scribbles-10");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 11, "sandstone-scribbles/scribbles-11");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 12, "sandstone-scribbles/scribbles-12");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 13, "sandstone-scribbles/scribbles-13");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 14, "sandstone-scribbles/scribbles-14");
            sandstoneScribbles.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.sandstoneScribbles.desc"), 15, "sandstone-scribbles/scribbles-15");
            sandstoneScribbles.carverHelper.register(sandstoneScribbles, "sandstone_scribbles");
        }

        if(Configurations.featureEnabled("concrete"))
        {
            concrete = (BlockConcrete) new BlockConcrete().setStepSound(Block.soundTypeStone).setHardness(0.5F);
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.0.desc"), 0, "concrete/default");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.1.desc"), 1, "concrete/block");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.2.desc"), 2, "concrete/doubleslab");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.3.desc"), 3, "concrete/blocks");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.4.desc"), 4, "concrete/weathered");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.5.desc"), 5, "concrete/weathered-block");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.6.desc"), 6, "concrete/weathered-doubleslab");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.7.desc"), 7, "concrete/weathered-blocks");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.8.desc"), 8, "concrete/weathered-half");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.9.desc"), 9, "concrete/weathered-block-half");
            concrete.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.concrete.10.desc"), 10, "concrete/asphalt");
            concrete.carverHelper.register(concrete, "concrete");
            OreDictionary.registerOre("concrete", concrete);
            Carving.chisel.registerOre("concrete", "concrete");
        }

        if(Configurations.featureEnabled("roadLine"))
        {
            roadLine = (BlockRoadLine) new BlockRoadLine().setStepSound(Block.soundTypeStone).setHardness(0.01F).setBlockName("roadLine");
            GameRegistry.registerBlock(roadLine, ItemCarvable.class, "road_line");
            //TODO- flag
            //LanguageRegistry.addName(new ItemStack(roadLine, 1, 0), "Road lines");
        }

        if(Configurations.featureEnabled("ironBlock"))
        {
            iron = (BlockBeaconBase) new BlockBeaconBase().setHardness(5F).setResistance(10F).setStepSound(Block.soundTypeMetal);
            Carving.chisel.addVariation("iron_block", Blocks.iron_block, 0, 0);
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.1.desc"), 1, "iron/terrain-iron-largeingot");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.2.desc"), 2, "iron/terrain-iron-smallingot");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.3.desc"), 3, "iron/terrain-iron-gears");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.4.desc"), 4, "iron/terrain-iron-brick");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.5.desc"), 5, "iron/terrain-iron-plates");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.6.desc"), 6, "iron/terrain-iron-rivets");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.7.desc"), 7, "iron/terrain-iron-coin-heads");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.8.desc"), 8, "iron/terrain-iron-coin-tails");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.9.desc"), 9, "iron/terrain-iron-crate-dark");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.10.desc"), 10, "iron/terrain-iron-crate-light");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.11.desc"), 11, "iron/terrain-iron-moon");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.12.desc"), 12, "iron/terrain-iron-space");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.13.desc"), 13, "iron/terrain-iron-spaceblack");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.14.desc"), 14, "iron/terrain-iron-vents");
            iron.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iron.15.desc"), 15, "iron/terrain-iron-simple");
            iron.carverHelper.register(iron, "iron_block");
            Carving.chisel.registerOre("iron_block", "iron");
        }

        if(Configurations.featureEnabled("goldBlock"))
        {
            gold = (BlockBeaconBase) new BlockBeaconBase().setHardness(3F).setResistance(10F).setStepSound(Block.soundTypeMetal);
            Carving.chisel.addVariation("gold_block", Blocks.gold_block, 0, 0);
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.1.desc"), 1, "gold/terrain-gold-largeingot");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.2.desc"), 2, "gold/terrain-gold-smallingot");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.3.desc"), 3, "gold/terrain-gold-brick");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.4.desc"), 4, "gold/terrain-gold-cart");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.5.desc"), 5, "gold/terrain-gold-coin-heads");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.6.desc"), 6, "gold/terrain-gold-coin-tails");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.7.desc"), 7, "gold/terrain-gold-crate-dark");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.8.desc"), 8, "gold/terrain-gold-crate-light");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.9.desc"), 9, "gold/terrain-gold-plates");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.10.desc"), 10, "gold/terrain-gold-rivets");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.11.desc"), 11, "gold/terrain-gold-star");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.12.desc"), 12, "gold/terrain-gold-space");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.13.desc"), 13, "gold/terrain-gold-spaceblack");
            gold.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.gold.14.desc"), 14, "gold/terrain-gold-simple");
            gold.carverHelper.register(gold, "gold_block");
            Carving.chisel.registerOre("gold_block", "gold");
        }

        if(Configurations.featureEnabled("diamondBlock"))
        {
            diamond = (BlockBeaconBase) new BlockBeaconBase().setHardness(5F).setResistance(10F).setStepSound(Block.soundTypeMetal);
            Carving.chisel.addVariation("diamond_block", Blocks.diamond_block, 0, 0);
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.1.desc"), 1, "diamond/terrain-diamond-embossed");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.2.desc"), 2, "diamond/terrain-diamond-gem");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.3.desc"), 3, "diamond/terrain-diamond-cells");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.4.desc"), 4, "diamond/terrain-diamond-space");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.5.desc"), 5, "diamond/terrain-diamond-spaceblack");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.6.desc"), 6, "diamond/terrain-diamond-simple");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.7.desc"), 7, "diamond/a1-blockdiamond-bismuth");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.8.desc"), 8, "diamond/a1-blockdiamond-crushed");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.9.desc"), 9, "diamond/a1-blockdiamond-four");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.10.desc"), 10, "diamond/a1-blockdiamond-fourornate");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.11.desc"), 11, "diamond/a1-blockdiamond-zelda");
            diamond.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.diamond.12.desc"), 12, "diamond/a1-blockdiamond-ornatelayer");
            diamond.carverHelper.register(diamond, "diamond_block");
            Carving.chisel.registerOre("diamond_block", "diamond");
        }

        if(Configurations.featureEnabled("glowstone"))
        {
            lightstone = (BlockLightstoneCarvable) new BlockLightstoneCarvable().setHardness(0.3F).setLightLevel(1.0F).setStepSound(Block.soundTypeGlass);
            Carving.chisel.addVariation("glowstone", Blocks.glowstone, 0, 0);
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.1.desc"), 1, "lightstone/terrain-sulphur-cobble");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.2.desc"), 2, "lightstone/terrain-sulphur-corroded");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.3.desc"), 3, "lightstone/terrain-sulphur-glass");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.4.desc"), 4, "lightstone/terrain-sulphur-neon");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.5.desc"), 5, "lightstone/terrain-sulphur-ornate");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.6.desc"), 6, "lightstone/terrain-sulphur-rocky");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.7.desc"), 7, "lightstone/terrain-sulphur-shale");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.8.desc"), 8, "lightstone/terrain-sulphur-tile");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.9.desc"), 9, "lightstone/terrain-sulphur-weavelanternlight");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.10.desc"), 10, "lightstone/a1-glowstone-cobble");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.11.desc"), 11, "lightstone/a1-glowstone-growth");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.12.desc"), 12, "lightstone/a1-glowstone-layers");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.13.desc"), 13, "lightstone/a1-glowstone-tilecorroded");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.14.desc"), 14, "lightstone/glowstone-bismuth");
            lightstone.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lightstone.15.desc"), 15, "lightstone/glowstone-bismuth-panel");
            lightstone.carverHelper.register(lightstone, "glowstone");
            Carving.chisel.registerOre("glowstone", "glowstone");
        }

        if(Configurations.featureEnabled("lapisBlock"))
        {
            lapis = (BlockCarvable) new BlockCarvable().setHardness(3F).setResistance(5F).setStepSound(Block.soundTypeStone);
            Carving.chisel.addVariation("lapis_block", Blocks.lapis_block, 0, 0);
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.1.desc"), 1, "lapis/terrain-lapisblock-chunky");
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.2.desc"), 2, "lapis/terrain-lapisblock-panel");
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.3.desc"), 3, "lapis/terrain-lapisblock-zelda");
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.4.desc"), 4, "lapis/terrain-lapisornate");
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.5.desc"), 5, "lapis/terrain-lapistile");
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.6.desc"), 6, "lapis/a1-blocklapis-panel");
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.7.desc"), 7, "lapis/a1-blocklapis-smooth");
            lapis.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.lapis.8.desc"), 8, "lapis/a1-blocklapis-ornatelayer");
            lapis.carverHelper.register(lapis, "lapis_block");
            Carving.chisel.registerOre("lapis_block", "lapis");
        }

        if(Configurations.featureEnabled("emeraldBlock"))
        {
            emerald = (BlockBeaconBase) new BlockBeaconBase().setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
            Carving.chisel.addVariation("emerald_block", Blocks.emerald_block, 0, 0);
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.1.desc"), 1, "emerald/a1-blockemerald-emeraldpanel");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.2.desc"), 2, "emerald/a1-blockemerald-emeraldpanelclassic");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.3.desc"), 3, "emerald/a1-blockemerald-emeraldsmooth");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.4.desc"), 4, "emerald/a1-blockemerald-emeraldchunk");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.5.desc"), 5, "emerald/a1-blockemerald-emeraldornatelayer");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.6.desc"), 6, "emerald/a1-blockemerald-emeraldzelda");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.7.desc"), 7, "emerald/a1-blockquartz-cell");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.8.desc"), 8, "emerald/a1-blockquartz-cellbismuth");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.9.desc"), 9, "emerald/a1-blockquartz-four");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.10.desc"), 10, "emerald/a1-blockquartz-fourornate");
            emerald.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.emerald.11.desc"), 11, "emerald/a1-blockquartz-ornate");
            emerald.carverHelper.register(emerald, "emerald_block");
            Carving.chisel.registerOre("emerald_block", "emerald");
        }

        if(Configurations.featureEnabled("netherBrick"))
        {
            netherBrick = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeStone);
            Carving.chisel.addVariation("nether_brick", Blocks.nether_brick, 0, 0);
            //netherBrick.carverHelper.addVariation("Nether brick", 0, Blocks.nether_brick);
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.1.desc"), 1, "netherbrick/a1-netherbrick-brinstar");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.2.desc"), 2, "netherbrick/a1-netherbrick-classicspatter");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.3.desc"), 3, "netherbrick/a1-netherbrick-guts");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.4.desc"), 4, "netherbrick/a1-netherbrick-gutsdark");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.5.desc"), 5, "netherbrick/a1-netherbrick-gutssmall");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.6.desc"), 6, "netherbrick/a1-netherbrick-lavabrinstar");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.7.desc"), 7, "netherbrick/a1-netherbrick-lavabrown");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.8.desc"), 8, "netherbrick/a1-netherbrick-lavaobsidian");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.9.desc"), 9, "netherbrick/a1-netherbrick-lavastonedark");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.10.desc"), 10, "netherbrick/a1-netherbrick-meat");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.11.desc"), 11, "netherbrick/a1-netherbrick-meatred");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.12.desc"), 12, "netherbrick/a1-netherbrick-meatredsmall");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.13.desc"), 13, "netherbrick/a1-netherbrick-meatsmall");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.14.desc"), 14, "netherbrick/a1-netherbrick-red");
            netherBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.netherBrick.15.desc"), 15, "netherbrick/a1-netherbrick-redsmall");
            netherBrick.carverHelper.register(netherBrick, "nether_brick");
            Carving.chisel.registerOre("nether_brick", "nether_brick");
        }

        if(Configurations.featureEnabled("netherRack"))
        {
            netherrack = (BlockCarvable) new BlockCarvable().setHardness(0.4F).setStepSound(Block.soundTypeStone);
            Carving.chisel.addVariation("netherrack", Blocks.netherrack, 0, 0);
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.1.desc"), 1, "netherrack/a1-netherrack-bloodgravel");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.2.desc"), 2, "netherrack/a1-netherrack-bloodrock");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.3.desc"), 3, "netherrack/a1-netherrack-bloodrockgrey");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.4.desc"), 4, "netherrack/a1-netherrack-brinstar");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.5.desc"), 5, "netherrack/a1-netherrack-brinstarshale");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.6.desc"), 6, "netherrack/a1-netherrack-classic");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.7.desc"), 7, "netherrack/a1-netherrack-classicspatter");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.8.desc"), 8, "netherrack/a1-netherrack-guts");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.9.desc"), 9, "netherrack/a1-netherrack-gutsdark");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.10.desc"), 10, "netherrack/a1-netherrack-meat");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.11.desc"), 11, "netherrack/a1-netherrack-meatred");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.12.desc"), 12, "netherrack/a1-netherrack-meatrock");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.13.desc"), 13, "netherrack/a1-netherrack-red");
            netherrack.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.hellrock.14.desc"), 14, "netherrack/a1-netherrack-wells");
            netherrack.carverHelper.register(netherrack, "netherrack");
            Carving.chisel.registerOre("netherrack", "netherrack");
        }

        if(Configurations.featureEnabled("cobblestoneMossy"))
        {
            cobblestoneMossy = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeStone);
            Carving.chisel.addVariation("mossy_cobblestone", Blocks.mossy_cobblestone, 0, 0);
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.1.desc"), 1, "cobblestonemossy/terrain-cobb-brickaligned");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.2.desc"), 2, "cobblestonemossy/terrain-cob-detailedbrick");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.3.desc"), 3, "cobblestonemossy/terrain-cob-smallbrick");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.4.desc"), 4, "cobblestonemossy/terrain-cobblargetiledark");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.5.desc"), 5, "cobblestonemossy/terrain-cobbsmalltile");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.6.desc"), 6, "cobblestonemossy/terrain-cob-french");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.7.desc"), 7, "cobblestonemossy/terrain-cob-french2");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.8.desc"), 8, "cobblestonemossy/terrain-cobmoss-creepdungeon");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.9.desc"), 9, "cobblestonemossy/terrain-mossysmalltiledark");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.10.desc"), 10, "cobblestonemossy/terrain-pistonback-dungeontile");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.11.desc"), 11, "cobblestonemossy/terrain-pistonback-darkcreeper");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.12.desc"), 12, "cobblestonemossy/terrain-pistonback-darkdent");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.13.desc"), 13, "cobblestonemossy/terrain-pistonback-darkemboss");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.14.desc"), 14, "cobblestonemossy/terrain-pistonback-darkmarker");
            cobblestoneMossy.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneMoss.15.desc"), 15, "cobblestonemossy/terrain-pistonback-darkpanel");
            cobblestoneMossy.carverHelper.register(cobblestoneMossy, "mossy_cobblestone");
            Carving.chisel.registerOre("mossy_cobblestone", "mossy_cobblestone");
        }

        if(Configurations.featureEnabled("stoneBrick"))
        {
            stoneBrick = (BlockCarvable) new BlockCarvable().setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone);
            for(int i = 0; i < 4; i++)
            {
                if(i == 1)
                {
                    if(Configurations.allowMossy)
                        Carving.chisel.addVariation("stonebrick", Blocks.stonebrick, i, i);
                } else
                    Carving.chisel.addVariation("stonebrick", Blocks.stonebrick, i, i);
            }
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.4.desc"), 4, "stonebrick/smallbricks");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.5.desc"), 5, "stonebrick/largebricks");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.6.desc"), 6, "stonebrick/smallchaotic");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.7.desc"), 7, "stonebrick/chaoticbricks");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.8.desc"), 8, "stonebrick/chaotic");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.9.desc"), 9, "stonebrick/fancy");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.10.desc"), 10, "stonebrick/ornate");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.11.desc"), 11, "stonebrick/largeornate");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.12.desc"), 12, "stonebrick/panel-hard");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.13.desc"), 13, "stonebrick/sunken");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.14.desc"), 14, "stonebrick/ornatepanel");
            stoneBrick.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.stoneBrick.15.desc"), 15, "stonebrick/poison");
            stoneBrick.carverHelper.register(stoneBrick, "stonebrick");
            Carving.chisel.registerOre("stonebrick", "stonebrick");
        }

        if(Configurations.featureEnabled("snakestone"))
        {
            snakestone = (BlockSnakestone) new BlockSnakestone("Chisel:snakestone/snake/").setBlockName("snakestoneStone");
            GameRegistry.registerBlock(snakestone, ItemCarvable.class, "stone_snakestone");
            //LanguageRegistry.addName(new ItemStack(snakestone, 1, 1), "Stone snake block head");
            //LanguageRegistry.addName(new ItemStack(snakestone, 1, 13), "Stone snake block body");
            Carving.chisel.addVariation("stonebrick", snakestone, 1, 16);
            Carving.chisel.addVariation("stonebrick", snakestone, 13, 17);
        }

        if(Configurations.featureEnabled("dirt"))
        {
            dirt = (BlockCarvable) new BlockCarvable(Material.ground).setHardness(0.5F).setStepSound(Block.soundTypeGravel).setBlockName("dirt.default");
            Carving.chisel.addVariation("dirt", Blocks.dirt, 0, 0);
            dirt.carverHelper.setChiselBlockName("Dirt");
            //dirt.carverHelper.addVariation("Dirt", 0, Blocks.dirt);
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.0.desc"), 0, "dirt/bricks");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.1.desc"), 1, "dirt/netherbricks");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.2.desc"), 2, "dirt/bricks3");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.3.desc"), 3, "dirt/cobble");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.4.desc"), 4, "dirt/reinforced");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.5.desc"), 5, "dirt/dirt-reinforced");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.6.desc"), 6, "dirt/happy");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.7.desc"), 7, "dirt/bricks2");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.8.desc"), 8, "dirt/bricks+dirt2");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.9.desc"), 9, "dirt/hor");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.10.desc"), 10, "dirt/vert");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.11.desc"), 11, "dirt/layers");
            dirt.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.dirt.12.desc"), 12, "dirt/vertical");
            dirt.carverHelper.register(dirt, "dirt");
            dirt.setHarvestLevel("shovel", 0);
            OreDictionary.registerOre("dirt", dirt);
            Carving.chisel.registerOre("dirt", "dirt");
        }

        if(Configurations.featureEnabled("ice"))
        {
            ice = (BlockMarbleIce) new BlockMarbleIce().setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass);
            Carving.chisel.addVariation("ice", Blocks.ice, 0, 0);
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.1.desc"), 1, "ice/a1-ice-light");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.2.desc"), 2, "ice/a1-stonecobble-icecobble");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.3.desc"), 3, "ice/a1-netherbrick-ice");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.4.desc"), 4, "ice/a1-stonecobble-icebrick");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.5.desc"), 5, "ice/a1-stonecobble-icebricksmall");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.6.desc"), 6, "ice/a1-stonecobble-icedungeon");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.7.desc"), 7, "ice/a1-stonecobble-icefour");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.8.desc"), 8, "ice/a1-stonecobble-icefrench");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.9.desc"), 9, "ice/sunkentiles");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.10.desc"), 10, "ice/tiles");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.11.desc"), 11, "ice/a1-stonecobble-icepanel");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.12.desc"), 12, "ice/a1-stoneslab-ice");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.13.desc"), 13, "ice/zelda");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.14.desc"), 14, "ice/bismuth");
            ice.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.ice.15.desc"), 15, "ice/poison");
            ice.carverHelper.register(ice, "ice");
            Carving.chisel.registerOre("ice", "ice");

            if(Configurations.featureEnabled("icePillar"))
            {
                icePillar = (BlockMarbleIce) new BlockMarbleIce().setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass);
                icePillar.carverHelper.setChiselBlockName("Ice Pillar");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.0.desc"), 0, "icepillar/column");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.1.desc"), 1, "icepillar/capstone");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.2.desc"), 2, "icepillar/base");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.3.desc"), 3, "icepillar/small");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.4.desc"), 4, "icepillar/pillar-carved");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.5.desc"), 5, "icepillar/a1-stoneornamental-marblegreek");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.6.desc"), 6, "icepillar/a1-stonepillar-greek");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.7.desc"), 7, "icepillar/a1-stonepillar-plain");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.8.desc"), 8, "icepillar/a1-stonepillar-greektopplain");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.9.desc"), 9, "icepillar/a1-stonepillar-plaintopplain");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.10.desc"), 10, "icepillar/a1-stonepillar-greekbottomplain");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.11.desc"), 11, "icepillar/a1-stonepillar-plainbottomplain");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.12.desc"), 12, "icepillar/a1-stonepillar-greektopgreek");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.13.desc"), 13, "icepillar/a1-stonepillar-plaintopgreek");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.14.desc"), 14, "icepillar/a1-stonepillar-greekbottomgreek");
                icePillar.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.icePillar.15.desc"), 15, "icepillar/a1-stonepillar-plainbottomgreek");
                icePillar.carverHelper.register(icePillar, "ice_pillar");
                Carving.chisel.setGroupClass("ice_pillar", "ice");
            }

            if(Configurations.featureEnabled("iceStairs"))
            {
                BlockMarbleStairsMaker makerIceStairs = new BlockMarbleStairsMaker(Blocks.ice);
                makerIceStairs.carverHelper.setChiselBlockName("Ice Stairs");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.0.desc"), 0, Blocks.ice);
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.1.desc"), 1, "ice/a1-ice-light");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.2.desc"), 2, "ice/a1-stonecobble-icecobble");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.3.desc"), 3, "ice/a1-netherbrick-ice");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.4.desc"), 4, "ice/a1-stonecobble-icebrick");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.5.desc"), 5, "ice/a1-stonecobble-icebricksmall");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.6.desc"), 6, "ice/a1-stonecobble-icedungeon");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.7.desc"), 7, "ice/a1-stonecobble-icefour");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.8.desc"), 8, "ice/a1-stonecobble-icefrench");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.9.desc"), 9, "ice/sunkentiles");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.10.desc"), 10, "ice/tiles");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.11.desc"), 11, "ice/a1-stonecobble-icepanel");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.12.desc"), 12, "ice/a1-stoneslab-ice");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.13.desc"), 13, "ice/zelda");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.14.desc"), 14, "ice/bismuth");
                makerIceStairs.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.iceStairs.15.desc"), 15, "ice/poison");
                makerIceStairs.create(new BlockMarbleStairsMakerCreator()
                {
                    @Override
                    public BlockMarbleStairs create(Block block, int meta, CarvableHelper helper)
                    {
                        return new BlockMarbleIceStairs(block, meta, helper);
                    }
                }, "ice_stairs");
            }
        }

        if(Configurations.featureEnabled("wood"))
        {
            String[] plank_names = { "oak", "spruce", "birch", "jungle", "acacia", "dark-oak" };
            String[] plank_ucnames = {"Oak", "Spruce", "Birch", "Jungle", "Acacia", "Dark Oak"};
            for(int i = 0; i < 6; i++)
            {
                String n = plank_names[i];
                String u = plank_ucnames[i];
                final String orename = n.replace('-', '_') + "_planks";

                planks[i] = (BlockCarvable) (new BlockCarvable()).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood);
                planks[i].carverHelper.setChiselBlockName(u + " Wood Planks");
                planks[i].carverHelper.addVariation("Smooth " + n + " wood planks", 1, "planks-" + n + "/clean");
                planks[i].carverHelper.addVariation("Short " + n + " wood planks", 2, "planks-" + n + "/short");
                planks[i].carverHelper.addVariation("Fancy " + n + " wood plank arrangement", 6, "planks-" + n + "/fancy");
                planks[i].carverHelper.addVariation(u + " wood panel", 8, "planks-" + n + "/panel-nails");
                planks[i].carverHelper.addVariation(u + " wood double slab", 9, "planks-" + n + "/double");
                planks[i].carverHelper.addVariation(u + " wood crate", 10, "planks-" + n + "/crate");
                planks[i].carverHelper.addVariation("Fancy " + n + " wood crate", 11, "planks-" + n + "/crate-fancy");
                planks[i].carverHelper.addVariation("Large long " + n + " wood planks", 13, "planks-" + n + "/large");
                if(i < 4)
                { // TODO: We lack textures for these!
                    planks[i].carverHelper.addVariation("Vertical " + n + " wood planks", 3, "planks-" + n + "/vertical");
                    planks[i].carverHelper.addVariation("Vertical uneven " + n + " wood planks", 4, "planks-" + n + "/vertical-uneven");
                    planks[i].carverHelper.addVariation(u + " wood parquet", 5, "planks-" + n + "/parquet");
                    planks[i].carverHelper.addVariation(u + " wood plank blinds", 7, "planks-" + n + "/blinds");
                    planks[i].carverHelper.addVariation(u + " wood scaffold", 12, "planks-" + n + "/crateex");
                    planks[i].carverHelper.addVariation(u + " wood planks in disarray", 14, "planks-" + n + "/chaotic-hor");
                    planks[i].carverHelper.addVariation("Vertical " + n + " wood planks in disarray", 15, "planks-" + n + "/chaotic");
                }
                planks[i].carverHelper.register(planks[i], orename);
                Carving.chisel.addVariation(orename, Blocks.planks, i, 0);
                Blocks.planks.setHarvestLevel("chisel", 0, i);
                planks[i].setHarvestLevel("axe", 0);

                Carving.chisel.setVariationSound(orename, Chisel.MOD_ID+":chisel.wood");
            }
        }

        if(Configurations.featureEnabled("obsidian"))
        {
            obsidian = (BlockCarvable) new BlockCarvable().setHardness(50.0F).setResistance(2000.0F).setStepSound(Block.soundTypeStone);
            Carving.chisel.addVariation("obsidian", Blocks.obsidian, 0, 0);
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.1.desc"), 1, "obsidian/pillar");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.2.desc"), 2, "obsidian/pillar-quartz");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.3.desc"), 3, "obsidian/chiseled");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.4.desc"), 4, "obsidian/panel-shiny");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.5.desc"), 5, "obsidian/panel");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.6.desc"), 6, "obsidian/chunks");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.7.desc"), 7, "obsidian/growth");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.8.desc"), 8, "obsidian/crystal");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.9.desc"), 9, "obsidian/map-a");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.10.desc"), 10, "obsidian/map-b");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.11.desc"), 11, "obsidian/panel-light");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.12.desc"), 12, "obsidian/blocks");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.13.desc"), 13, "obsidian/tiles");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.14.desc"), 14, "obsidian/greek");
            obsidian.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.obsidian.15.desc"), 15, "obsidian/crate");
            obsidian.carverHelper.register(obsidian, "obsidian");
            Carving.chisel.registerOre("obsidian", "obsidian");
        }

        if(Configurations.featureEnabled("snakestoneObsidian"))
        {
            obsidianSnakestone = (BlockSnakestoneObsidian) new BlockSnakestoneObsidian("Chisel:snakestone/obsidian/").setBlockName("obsidianSnakestone").setHardness(50.0F).setResistance(2000.0F);
            GameRegistry.registerBlock(obsidianSnakestone, ItemCarvable.class, "obsidian_snakestone");
            //LanguageRegistry.addName(new ItemStack(obsidianSnakestone, 1, 1), "Obsidian snakestone head");
            //LanguageRegistry.addName(new ItemStack(obsidianSnakestone, 1, 13), "Obsidian snakestone body");
            Carving.chisel.addVariation("obsidian", obsidianSnakestone, 1, 16);
            Carving.chisel.addVariation("obsidian", obsidianSnakestone, 13, 17);
        }

        if(Configurations.featureEnabled("ironBars"))
        {
            paneIron = (BlockCarvablePane) new BlockCarvablePane(Material.iron, true).setHardness(0.3F).setStepSound(Block.soundTypeMetal);
            Carving.chisel.addVariation("iron_bars", Blocks.iron_bars, 0, 0);
            paneIron.carverHelper.addVariation("Iron bars without frame", 1, "ironpane/fenceIron");
            paneIron.carverHelper.addVariation("Menacing iron bars", 2, "ironpane/barbedwire");
            paneIron.carverHelper.addVariation("Iron cage bars", 3, "ironpane/cage");
            paneIron.carverHelper.addVariation("Menacing iron spikes", 4, "ironpane/fenceIronTop");
            paneIron.carverHelper.addVariation("Thick iron grid", 5, "ironpane/terrain-glass-thickgrid");
            paneIron.carverHelper.addVariation("Thin iron grid", 6, "ironpane/terrain-glass-thingrid");
            paneIron.carverHelper.addVariation("Ornate iron pane fence", 7, "ironpane/terrain-glass-ornatesteel");
            paneIron.carverHelper.addVariation("Vertical iron bars", 8, "ironpane/bars");
            paneIron.carverHelper.addVariation("Iron spikes", 9, "ironpane/spikes");
            paneIron.carverHelper.register(paneIron, "iron_bars");
        }

        if(Configurations.featureEnabled("glassPane"))
        {
            paneGlass = (BlockCarvablePane) new BlockCarvablePane(Material.glass, false).setHardness(0.3F).setStepSound(Block.soundTypeGlass);
            Carving.chisel.addVariation("glass_pane", Blocks.glass_pane, 0, 0);
            paneGlass.carverHelper.addVariation("Bubble glass pane", 1, "glasspane/terrain-glassbubble");
            paneGlass.carverHelper.addVariation("Borderless glass pane", 2, "glasspane/terrain-glassnoborder");
            paneGlass.carverHelper.addVariation("Screen pane", 3, "glasspane/terrain-glass-screen");
            paneGlass.carverHelper.addVariation("Streak glass pane", 4, "glasspane/terrain-glassstreak");
            paneGlass.carverHelper.addVariation("Chinese glass pane", 12, "glasspane/chinese");
            paneGlass.carverHelper.addVariation("Chinese glass pane with golden frame", 13, "glasspane/chinese2");
            paneGlass.carverHelper.addVariation("Japanese glass pane", 14, "glasspane/japanese");
            paneGlass.carverHelper.addVariation("Ornate japanese glass pane", 15, "glasspane/japanese2");
            paneGlass.carverHelper.register(paneGlass, "glass_pane");
        }

        if(Configurations.featureEnabled("redstoneBlock"))
        {
            redstone = (BlockCarvablePowered) (new BlockCarvablePowered(Material.iron)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
            Carving.chisel.addVariation("redstone_block", Blocks.redstone_block, 0, 0);
            redstone.carverHelper.addVariation("Smooth redstone", 1, "redstone/smooth");
            redstone.carverHelper.addVariation("Large redstone block", 2, "redstone/block");
            redstone.carverHelper.addVariation("Small redstone blocks", 3, "redstone/blocks");
            redstone.carverHelper.addVariation("Redstone bricks", 4, "redstone/bricks");
            redstone.carverHelper.addVariation("Small redstone bricks", 5, "redstone/smallbricks");
            redstone.carverHelper.addVariation("Chaotic redstone bricks", 6, "redstone/smallchaotic");
            redstone.carverHelper.addVariation("Chiseled redstone", 7, "redstone/chiseled");
            redstone.carverHelper.addVariation("Redstone Greek decoration", 8, "redstone/ere");
            redstone.carverHelper.addVariation("Ornate redstone tiles", 9, "redstone/ornate-tiles");
            redstone.carverHelper.addVariation("Redstone pillar", 10, "redstone/pillar");
            redstone.carverHelper.addVariation("Redstone tiles", 11, "redstone/tiles");
            redstone.carverHelper.addVariation("Redstone circuit", 12, "redstone/circuit");
            redstone.carverHelper.addVariation("Redstone supaplex circuit", 13, "redstone/supaplex");
            redstone.carverHelper.addVariation("Redstone skulls", 14, "redstone/a1-blockredstone-skullred");
            redstone.carverHelper.addVariation("Redstone Zelda block", 15, "redstone/a1-blockredstone-redstonezelda");
            redstone.carverHelper.register(redstone, "redstone_block");
            Carving.chisel.registerOre("redstone_block", "redstone");
        }

        if(Configurations.featureEnabled("holystone"))
        {
            holystone = (BlockHolystone) new BlockHolystone(Material.rock).setHardness(2.0F).setResistance(10F).setStepSound(Chisel.soundHolystoneFootstep);
            holystone.carverHelper.addVariation("Holystone", 0, "holystone/holystone");
            holystone.carverHelper.addVariation("Smooth holystone", 1, "holystone/smooth");
            holystone.carverHelper.addVariation("Mysterious holystone symbol", 2, "holystone/love");
            holystone.carverHelper.addVariation("Chiseled holystone", 3, "holystone/chiseled");
            holystone.carverHelper.addVariation("Holystone blocks", 4, "holystone/blocks");
            holystone.carverHelper.addVariation("Rough holystone blocks", 5, "holystone/blocks-rough");
            holystone.carverHelper.addVariation("Holystone bricks", 6, "holystone/brick");
            holystone.carverHelper.addVariation("Large holystone bricks", 7, "holystone/largebricks");
            holystone.carverHelper.addVariation("Holystone platform", 8, "holystone/platform");
            holystone.carverHelper.addVariation("Holystone platform tiles", 9, "holystone/platform-tiles");
            holystone.carverHelper.addVariation("Fancy holystone construction", 10, "holystone/construction");
            holystone.carverHelper.addVariation("Fancy holystone tiles", 11, "holystone/fancy-tiles");
            holystone.carverHelper.addVariation("Smooth holystone plate", 12, "holystone/plate");
            holystone.carverHelper.addVariation("Holystone plate", 13, "holystone/plate-rough");
            holystone.carverHelper.register(holystone, "holystone");
            OreDictionary.registerOre("holystone", holystone);
            Carving.chisel.registerOre("holystone", "holystone");
        }

        if(Configurations.featureEnabled("lavastone"))
        {
            lavastone = (BlockLavastone) new BlockLavastone(Material.rock, "lava_flow").setHardness(2.0F).setResistance(10F);
            lavastone.carverHelper.addVariation("Lavastone", 0, "lavastone/cobble");
            lavastone.carverHelper.addVariation("Black lavastone", 1, "lavastone/black");
            lavastone.carverHelper.addVariation("Lavastone tiles", 2, "lavastone/tiles");
            lavastone.carverHelper.addVariation("Chaotic lavastone bricks", 3, "lavastone/chaotic");
            lavastone.carverHelper.addVariation("Lava creeper in tiles", 4, "lavastone/creeper");
            lavastone.carverHelper.addVariation("Lava panel", 5, "lavastone/panel");
            lavastone.carverHelper.addVariation("Ornate lava panel", 6, "lavastone/panel-ornate");
            lavastone.carverHelper.register(lavastone, "lavastone");
            OreDictionary.registerOre("lavastone", lavastone);
            Carving.chisel.registerOre("lavastone", "lavastone");
        }

        if(Configurations.featureEnabled("fantasy"))
        {
            fantasy = (BlockCarvable) new BlockCarvable(Material.rock).setHardness(2.0F).setResistance(10F);
            fantasy.carverHelper.setChiselBlockName("Fantasy Block");
            fantasy.carverHelper.addVariation("Fantasy brick", 0, "fantasy/brick");
            fantasy.carverHelper.addVariation("Faded fantasy brick", 1, "fantasy/brick-faded");
            fantasy.carverHelper.addVariation("Weared fantasy brick", 2, "fantasy/brick-wear");
            fantasy.carverHelper.addVariation("Damaged fantasy bricks", 3, "fantasy/bricks");
            fantasy.carverHelper.addVariation("Fantasy decoration", 4, "fantasy/decor");
            fantasy.carverHelper.addVariation("Fantasy decoration block", 5, "fantasy/decor-block");
            fantasy.carverHelper.addVariation("Fantasy pillar", 6, "fantasy/pillar");
            fantasy.carverHelper.addVariation("Fantasy pillar decoration", 7, "fantasy/pillar-decorated");
            fantasy.carverHelper.addVariation("Fantasy gold snake decoration", 8, "fantasy/gold-decor-1");
            fantasy.carverHelper.addVariation("Fantasy gold noise decoration", 9, "fantasy/gold-decor-2");
            fantasy.carverHelper.addVariation("Fantasy gold engravings decoration", 10, "fantasy/gold-decor-3");
            fantasy.carverHelper.addVariation("Fantasy gold chains decoration", 11, "fantasy/gold-decor-4");
            fantasy.carverHelper.addVariation("Fantasy plate decoration", 12, "fantasy/plate");
            fantasy.carverHelper.addVariation("Fantasy block", 13, "fantasy/block");
            fantasy.carverHelper.addVariation("Fantasy bricks in disarray", 14, "fantasy/bricks-chaotic");
            fantasy.carverHelper.addVariation("Weared fantasy bricks", 15, "fantasy/bricks-wear");
            fantasy.carverHelper.register(fantasy, "fantasyblock");
            OreDictionary.registerOre("fantasy", fantasy);
            Carving.chisel.registerOre("fantasyblock", "fantasy");
        }

        if(Configurations.featureEnabled("carpet"))
        {
            carpet = (BlockCarvable) new BlockCarvable(Material.cloth).setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeCloth);
            carpet.carverHelper.setChiselBlockName("Carpet Block");
            carpet.carverHelper.addVariation("White carpet block", 0, "carpet/white");
            carpet.carverHelper.addVariation("Orange carpet block", 1, "carpet/orange");
            carpet.carverHelper.addVariation("Magenta carpet block", 2, "carpet/lily");
            carpet.carverHelper.addVariation("Light blue carpet block", 3, "carpet/lightblue");
            carpet.carverHelper.addVariation("Yellow carpet block", 4, "carpet/yellow");
            carpet.carverHelper.addVariation("Light green carpet block", 5, "carpet/lightgreen");
            carpet.carverHelper.addVariation("Pink carpet block", 6, "carpet/pink");
            carpet.carverHelper.addVariation("Dark grey carpet block", 7, "carpet/darkgrey");
            carpet.carverHelper.addVariation("Grey carpet block", 8, "carpet/grey");
            carpet.carverHelper.addVariation("Teal carpet block", 9, "carpet/teal");
            carpet.carverHelper.addVariation("Purple carpet block", 10, "carpet/purple");
            carpet.carverHelper.addVariation("Dark blue carpet block", 11, "carpet/darkblue");
            carpet.carverHelper.addVariation("Brown carpet block", 12, "carpet/brown");
            carpet.carverHelper.addVariation("Green carpet block", 13, "carpet/green");
            carpet.carverHelper.addVariation("Red carpet block", 14, "carpet/red");
            carpet.carverHelper.addVariation("Black carpet block", 15, "carpet/black");
            carpet.carverHelper.forbidChiseling = true;
            carpet.carverHelper.register(carpet, "carpet_block");
            OreDictionary.registerOre("carpet", carpet);
            Carving.chisel.registerOre("carpet_block", "carpet");
        }

        if(Configurations.featureEnabled("carpetFloor"))
        {
            carpetFloor = (BlockMarbleCarpet) new BlockMarbleCarpet(Material.cloth).setHardness(2.0F).setResistance(10F).setStepSound(Block.soundTypeCloth);
            carpetFloor.carverHelper.setChiselBlockName("Carpet");
            carpetFloor.carverHelper.addVariation("White carpet", 0, "carpet/white");
            carpetFloor.carverHelper.addVariation("Orange carpet", 1, "carpet/orange");
            carpetFloor.carverHelper.addVariation("Magenta carpet", 2, "carpet/lily");
            carpetFloor.carverHelper.addVariation("Light blue carpet", 3, "carpet/lightblue");
            carpetFloor.carverHelper.addVariation("Yellow carpet", 4, "carpet/yellow");
            carpetFloor.carverHelper.addVariation("Light green carpet", 5, "carpet/lightgreen");
            carpetFloor.carverHelper.addVariation("Pink carpet", 6, "carpet/pink");
            carpetFloor.carverHelper.addVariation("Dark grey carpet", 7, "carpet/darkgrey");
            carpetFloor.carverHelper.addVariation("Grey carpet", 8, "carpet/grey");
            carpetFloor.carverHelper.addVariation("Teal carpet", 9, "carpet/teal");
            carpetFloor.carverHelper.addVariation("Purple carpet", 10, "carpet/purple");
            carpetFloor.carverHelper.addVariation("Dark blue carpet", 11, "carpet/darkblue");
            carpetFloor.carverHelper.addVariation("Brown carpet", 12, "carpet/brown");
            carpetFloor.carverHelper.addVariation("Green carpet", 13, "carpet/green");
            carpetFloor.carverHelper.addVariation("Red carpet", 14, "carpet/red");
            carpetFloor.carverHelper.addVariation("Black carpet", 15, "carpet/black");
            carpetFloor.carverHelper.forbidChiseling = true;
            carpetFloor.carverHelper.register(carpetFloor, "carpet");

            for(int i = 0; i < 16; i++)
            {
                String group = "carpet." + i;

                Carving.needle.addVariation(group, Blocks.carpet, i, 0);
                Carving.needle.addVariation(group, carpetFloor, i, 2);
                Carving.needle.addVariation(group, carpet, i, 1);
            }
        }

        if(Configurations.featureEnabled("bookshelf"))
        {
            bookshelf = (BlockCarvable) new BlockMarbleBookshelf().setHardness(1.5F).setStepSound(Block.soundTypeWood);
            Carving.chisel.addVariation("bookshelf", Blocks.bookshelf, 0, 0);
            bookshelf.carverHelper.addVariation("Bookshelf with rainbow colored books", 1, "bookshelf/rainbow");
            bookshelf.carverHelper.addVariation("Necromancer novice's bookshelf", 2, "bookshelf/necromancer-novice");
            bookshelf.carverHelper.addVariation("Necromancer's bookshelf", 3, "bookshelf/necromancer");
            bookshelf.carverHelper.addVariation("Bookshelf with red tomes", 4, "bookshelf/redtomes");
            bookshelf.carverHelper.addVariation("Abandoned bookshelf", 5, "bookshelf/abandoned");
            bookshelf.carverHelper.addVariation("Hoarder's bookshelf", 6, "bookshelf/hoarder");
            bookshelf.carverHelper.addVariation("Bookshelf filled to brim with boring pastel books", 7, "bookshelf/brim");
            bookshelf.carverHelper.addVariation("Historician's bookshelf", 8, "bookshelf/historician");
            bookshelf.carverHelper.register(bookshelf, "bookshelf");
            bookshelf.setHarvestLevel("axe", 0);
            Carving.chisel.registerOre("bookshelf", "bookshelf");
        }

        if(Configurations.featureEnabled("futuristicArmorPlating"))
        {
            tyrian = (BlockCarvable) new BlockCarvable(Material.iron).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
            tyrian.carverHelper.setChiselBlockName("Futuristic Armor Plating Block");
            tyrian.carverHelper.addVariation("Futuristic armor plating block", 0, "tyrian/shining");
            tyrian.carverHelper.addVariation("Bleak futuristic armor plating block", 1, "tyrian/tyrian");
            tyrian.carverHelper.addVariation("Purple futuristic armor plating block", 2, "tyrian/chaotic");
            tyrian.carverHelper.addVariation("Faded purple futuristic armor plating block", 3, "tyrian/softplate");
            tyrian.carverHelper.addVariation("Rusted futuristic armor plating block", 4, "tyrian/rust");
            tyrian.carverHelper.addVariation("Elaborate futuristic armor plating block", 5, "tyrian/elaborate");
            tyrian.carverHelper.addVariation("Futuristic armor plating block with many seams", 6, "tyrian/routes");
            tyrian.carverHelper.addVariation("Futuristic platform block", 7, "tyrian/platform");
            tyrian.carverHelper.addVariation("Futuristic armor plating tiles", 8, "tyrian/platetiles");
            tyrian.carverHelper.addVariation("Diagonal futuristic armor plating block", 9, "tyrian/diagonal");
            tyrian.carverHelper.addVariation("Futuristic armor plating block with dent", 10, "tyrian/dent");
            tyrian.carverHelper.addVariation("Blue futuristic armor plating block", 11, "tyrian/blueplating");
            tyrian.carverHelper.addVariation("Black futuristic armor plating block", 12, "tyrian/black");
            tyrian.carverHelper.addVariation("Black futuristic armor plating tiles", 13, "tyrian/black2");
            tyrian.carverHelper.addVariation("Black futuristic armor plating block with an opening", 14, "tyrian/opening");
            tyrian.carverHelper.addVariation("Futuristic armor plating with shining metal bits", 15, "tyrian/plate");
            tyrian.carverHelper.register(tyrian, "tyrian");
            OreDictionary.registerOre("tyrian", tyrian);
            Carving.chisel.registerOre("tyrian", "tyrian");
        }


        if(Configurations.featureEnabled("templeBlock"))
        {
            temple = (BlockCarvable) new BlockEldritch().setHardness(2.0F).setResistance(10F).setStepSound(Chisel.soundTempleFootstep);
            temple.carverHelper.setChiselBlockName("Temple Block");
            temple.carverHelper.addVariation("Temple cobblestone", 0, "temple/cobble");
            temple.carverHelper.addVariation("Orante temple block", 1, "temple/ornate");
            temple.carverHelper.addVariation("Temple plate", 2, "temple/plate");
            temple.carverHelper.addVariation("Cracked temple plate", 3, "temple/plate-cracked");
            temple.carverHelper.addVariation("Temple bricks", 4, "temple/bricks");
            temple.carverHelper.addVariation("Large temple bricks", 5, "temple/bricks-large");
            temple.carverHelper.addVariation("Weared temple bricks", 6, "temple/bricks-weared");
            temple.carverHelper.addVariation("Temple bricks in disarray", 7, "temple/bricks-disarray");
            temple.carverHelper.addVariation("Temple column", 8, "temple/column");
            temple.carverHelper.addVariation("Temple stand", 9, "temple/stand");
            temple.carverHelper.addVariation("Temple mosaic stand", 10, "temple/stand-mosaic");
            temple.carverHelper.addVariation("Temple creeper stand", 11, "temple/stand-creeper");
            temple.carverHelper.addVariation("Temple tiles", 12, "temple/tiles");
            temple.carverHelper.addVariation("Small temple tiles", 13, "temple/smalltiles");
            temple.carverHelper.addVariation("Light temple tiles", 14, "temple/tiles-light");
            temple.carverHelper.addVariation("Small light temple tiles", 15, "temple/smalltiles-light");
            temple.carverHelper.register(temple, "templeblock");

            if(Configurations.featureEnabled("templeBlockMossy"))
            {
                templeMossy = (BlockCarvable) new BlockEldritch().setHardness(2.0F).setResistance(10F).setStepSound(Chisel.soundTempleFootstep);
                templeMossy.carverHelper.setChiselBlockName("Mossy Temple Block");
                templeMossy.carverHelper.addVariation("Mossy temple cobblestone", 0, "templemossy/cobble");
                templeMossy.carverHelper.addVariation("Orante mossy temple block", 1, "templemossy/ornate");
                templeMossy.carverHelper.addVariation("Mossy temple plate", 2, "templemossy/plate");
                templeMossy.carverHelper.addVariation("Cracked mossy temple plate", 3, "templemossy/plate-cracked");
                templeMossy.carverHelper.addVariation("Mossy temple bricks", 4, "templemossy/bricks");
                templeMossy.carverHelper.addVariation("Large mossy temple bricks", 5, "templemossy/bricks-large");
                templeMossy.carverHelper.addVariation("Weared mossy temple bricks", 6, "templemossy/bricks-weared");
                templeMossy.carverHelper.addVariation("Mossy temple bricks in disarray", 7, "templemossy/bricks-disarray");
                templeMossy.carverHelper.addVariation("Mossy temple column", 8, "templemossy/column");
                templeMossy.carverHelper.addVariation("Mossy temple stand", 9, "templemossy/stand");
                templeMossy.carverHelper.addVariation("Mossy temple mosaic stand", 10, "templemossy/stand-mosaic");
                templeMossy.carverHelper.addVariation("Mossy temple creeper stand", 11, "templemossy/stand-creeper");
                templeMossy.carverHelper.addVariation("Mossy temple tiles", 12, "templemossy/tiles");
                templeMossy.carverHelper.addVariation("Small mossy temple tiles", 13, "templemossy/smalltiles");
                templeMossy.carverHelper.addVariation("Light mossy temple tiles", 14, "templemossy/tiles-light");
                templeMossy.carverHelper.addVariation("Small light mossy  temple tiles", 15, "templemossy/smalltiles-light");
                templeMossy.carverHelper.register(templeMossy, "mossy_templeblock");
            }
        }

        if(Configurations.featureEnabled("cloud"))
        {
            cloud = (BlockCloud) new BlockCloud().setHardness(0.2F).setLightOpacity(3).setStepSound(Block.soundTypeCloth);
            cloud.carverHelper.addVariation("Cloud block", 0, "cloud/cloud");
            cloud.carverHelper.register(cloud, "cloud");
            OreDictionary.registerOre("cloud", cloud);
            Carving.chisel.registerOre("cloud", "cloud");
        }

        if(Configurations.featureEnabled("factory"))
        {
            factory = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10F).setStepSound(Chisel.soundMetalFootstep);
            factory.carverHelper.setChiselBlockName("factoryblock");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.0.desc"), 0, "factory/dots");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.1.desc"), 1, "factory/rust2");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.2.desc"), 2, "factory/rust");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.3.desc"), 3, "factory/platex");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.4.desc"), 4, "factory/wireframewhite");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.5.desc"), 5, "factory/wireframe");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.6.desc"), 6, "factory/hazard");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.7.desc"), 7, "factory/hazardorange");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.8.desc"), 8, "factory/circuit");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.9.desc"), 9, "factory/metalbox");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.10.desc"), 10, "factory/goldplate");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.11.desc"), 11, "factory/goldplating");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.12.desc"), 12, "factory/grinder");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.13.desc"), 13, "factory/plating");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.14.desc"), 14, "factory/rustplates");
            factory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.factory.15.desc"), 15, "factory/column");
            factory.carverHelper.register(factory, "factoryblock");
        }

        // 1.7! Let's go! Let's go-go!

        String[] sGNames = new String[]{
                "White", "Orange", "Magenta", "Light Blue",
                "Yellow", "Lime", "Pink", "Gray",
                "Light Gray", "Cyan", "Purple", "Blue",
                "Brown", "Green", "Red", "Black"
        };

        if(Configurations.featureEnabled("glassStained")) for(int i = 0; i < 16; i++)
        {
            final String blockName = "stained_glass_" + sGNames[i].replaceAll(" ", "").toLowerCase();
            String oreName = "stainedGlass" + sGNames[i].replaceAll(" ", "");
            String texName = "glassdyed/" + sGNames[i].toLowerCase().replaceAll(" ", "") + "-";
            int glassPrefix = (i & 3) << 2;
            int glassId = i >> 2;
            Carving.chisel.addVariation(blockName, Blocks.stained_glass, i, 0);
            if(glassPrefix == 0)
            {
                stainedGlass[glassId] = (BlockCarvableGlass) new BlockCarvableGlass().setStained(true).setHardness(0.3F).setStepSound(Block.soundTypeGlass).setBlockName("Stained Glass");
                stainedGlass[glassId].carverHelper.registerBlock(stainedGlass[glassId], blockName);
                //stainedGlass[glassId].carverHelper.blockName = "Stained Glass";
            }
            stainedGlass[glassId].carverHelper.addVariation(sGNames[i] + " bubble glass", glassPrefix, texName + "bubble");
            stainedGlass[glassId].carverHelper.addVariation(sGNames[i] + " glass panel", glassPrefix + 1, texName + "panel");
            stainedGlass[glassId].carverHelper.addVariation(sGNames[i] + " fancy glass panel", glassPrefix + 2, texName + "panel-fancy");
            stainedGlass[glassId].carverHelper.addVariation(sGNames[i] + " borderless glass", glassPrefix + 3, texName + "transparent");
            OreDictionary.registerOre(oreName, new ItemStack(Blocks.stained_glass, 1, i));
            Carving.chisel.registerOre(blockName, oreName);
            for(CarvableVariation cv : stainedGlass[glassId].carverHelper.variations)
            {
                if(cv.metadata < glassPrefix || cv.metadata >= glassPrefix + 4)
                    continue;
                stainedGlass[glassId].carverHelper.registerVariation(blockName, cv, stainedGlass[glassId], cv.metadata);
            }
        }

        if(Configurations.featureEnabled("glassStainedPane")) for(int i = 0; i < 16; i++)
        {
            final String blockName = "stained_glass_pane_" + sGNames[i].replaceAll(" ", "").toLowerCase();
            String oreName = "stainedGlassPane" + sGNames[i].replaceAll(" ", "");
            String texName = "glasspanedyed/" + sGNames[i].toLowerCase().replaceAll(" ", "") + "-";
            Carving.chisel.addVariation(blockName, Blocks.stained_glass_pane, i, 0);
            int glassPrefix = (i & 1) << 3;
            int glassId = i >> 1;
            if(glassPrefix == 0)
            {
                stainedGlassPane[glassId] = (BlockCarvablePane) new BlockCarvablePane(Material.glass, false).setStained(true).setHardness(0.3F).setStepSound(Block.soundTypeGlass).setBlockName("Stained Glass Pane");
                stainedGlassPane[glassId].carverHelper.registerBlock(stainedGlassPane[glassId], blockName);
                stainedGlassPane[glassId].carverHelper.blockName = "Stained Glass Pane";
            }
            stainedGlassPane[glassId].carverHelper.addVariation(sGNames[i] + " bubble glass", glassPrefix, texName + "bubble");
            stainedGlassPane[glassId].carverHelper.addVariation(sGNames[i] + " glass panel", glassPrefix + 1, texName + "panel");
            stainedGlassPane[glassId].carverHelper.addVariation(sGNames[i] + " fancy glass panel", glassPrefix + 2, texName + "panel-fancy");
            stainedGlassPane[glassId].carverHelper.addVariation(sGNames[i] + " borderless glass", glassPrefix + 3, texName + "transparent");
            stainedGlassPane[glassId].carverHelper.addVariation(sGNames[i] + " quadrant glass", glassPrefix + 4, texName + "quad");
            stainedGlassPane[glassId].carverHelper.addVariation(sGNames[i] + " fancy quadrant glass", glassPrefix + 5, texName + "quad-fancy");
            OreDictionary.registerOre(oreName, new ItemStack(Blocks.stained_glass_pane, 1, i));
            Carving.chisel.registerOre(blockName, oreName);
            for(CarvableVariation cv : stainedGlassPane[glassId].carverHelper.variations)
            {
                if(cv.metadata < glassPrefix || cv.metadata >= glassPrefix + 8)
                    continue;
                stainedGlassPane[glassId].carverHelper.registerVariation(blockName, cv, stainedGlassPane[glassId], cv.metadata);
            }
        }

        if(Configurations.featureEnabled("paperWall"))
        {
            paperWall = (BlockCarvablePane) new BlockCarvablePane(Material.ground, true).setCreativeTab(ModTabs.tabChiselBlocks).setHardness(0.5F).setResistance(10F);
            paperWall.carverHelper.setChiselBlockName("Paper Wall");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.0.desc"), 0, "paper/box");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.1.desc"), 1, "paper/throughMiddle");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.2.desc"), 2, "paper/cross");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.3.desc"), 3, "paper/sixSections");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.4.desc"), 4, "paper/vertical");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.5.desc"), 5, "paper/horizontal");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.6.desc"), 6, "paper/floral");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.7.desc"), 7, "paper/plain");
            paperWall.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.paperwall.8.desc"), 8, "paper/door");

            paperWall.carverHelper.register(paperWall, "paperwall");

        }

        if(Configurations.featureEnabled("woolenClay"))
        {
            woolenClay = (BlockCarvable) new BlockCarvable().setCreativeTab(ModTabs.tabChiselBlocks).setHardness(2F).setResistance(10F);
            woolenClay.carverHelper.setChiselBlockName("Woolen Clay");

            for(int i = 0; i < 16; i++)
                woolenClay.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.woolenClay." + i + ".desc"), i, "woolenClay/" + sGNames[i].replaceAll(" ", "").toLowerCase());
            woolenClay.carverHelper.register(woolenClay, "woolen_clay");


        }

        if(Configurations.featureEnabled("laboratory"))
        {
            laboratory = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(10F).setStepSound(Chisel.soundMetalFootstep);
            laboratory.carverHelper.setChiselBlockName("laboratoryblock");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.0.desc"), 0, "laboratory/wallpanel");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.1.desc"), 1, "laboratory/dottedpanel");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.2.desc"), 2, "laboratory/largewall");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.3.desc"), 3, "laboratory/roundel");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.4.desc"), 4, "laboratory/wallvents");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.5.desc"), 5, "laboratory/largetile");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.6.desc"), 6, "laboratory/smalltile");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.7.desc"), 7, "laboratory/floortile");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.8.desc"), 8, "laboratory/checkertile");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.9.desc"), 9, "laboratory/clearscreen");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.10.desc"), 10, "laboratory/fuzzscreen");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.11.desc"), 11, "laboratory/largesteel");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.12.desc"), 12, "laboratory/smallsteel");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.13.desc"), 13, "laboratory/directionright");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.14.desc"), 14, "laboratory/directionleft");
            laboratory.carverHelper.addVariation(StatCollector.translateToLocal("chisel.tile.laboratory.15.desc"), 15, "laboratory/infocon");
            laboratory.carverHelper.register(laboratory, "laboratoryblock");
        }

        if(Configurations.featureEnabled("pumpkin"))
        {
            pumpkin = (BlockCarvable) new BlockCarvable().setHardness(2.0F).setResistance(5F).setStepSound(Block.soundTypeStone);
            Carving.chisel.addVariation("pumpkin", Blocks.pumpkin, 0, 0);
            //pumpkin.carverHelper.addVariation("chisel.title.pumpkin.1.desc", 1, "pumpkin/1");
        }

        Blocks.stone.setHarvestLevel("chisel", 0, 0);
    }
}