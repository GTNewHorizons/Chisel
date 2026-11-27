package com.cricketcraft.chisel.api.carving;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.collect.Lists;

public class CarvingUtils {

    /**
     * A simple way to compare two {@link ICarvingVariation} objects based on the {@link ICarvingVariation#getOrder()
     * getOrder()} method.
     *
     * @param v1 The first {@link ICarvingVariation variation}.
     * @param v2 The second {@link ICarvingVariation variation}.
     * @return A positive integer if the first's order is greater, a negative integer if the second's is greater, and 0
     *         if they are equal.
     */
    public static int compare(ICarvingVariation v1, ICarvingVariation v2) {
        return v1.getOrder() - v2.getOrder();
    }

    /**
     * Gets an {@link ItemStack} representing the passed {@link ICarvingVariation}.
     *
     * @param variation An {@link ICarvingVariation}
     * @return An {@link ItemStack}
     */
    public static ItemStack getStack(ICarvingVariation variation) {
        return new ItemStack(variation.getBlock(), 1, variation.getItemMeta());
    }

    public static ICarvingRegistry chisel;

    /**
     * @return The instance of the chisel carving registry from the chisel mod.
     *         <p>
     *         If chisel is not installed this will return null.
     */
    public static ICarvingRegistry getChiselRegistry() {
        return chisel;
    }

    /**
     * Creates a standard {@link ICarvingVariation} for the given data. Use this if you do not need any custom behavior
     * in your own variation.
     *
     * @param block    The block of the variation
     * @param metadata The metadata of both the block and item
     * @param order    The sorting order.
     * @return A standard {@link ICarvingVariation} instance.
     */
    public static ICarvingVariation getDefaultVariationFor(Block block, int metadata, int order) {
        return new SimpleCarvingVariation(block, metadata, order);
    }

    /**
     * Creates a standard {@link ICarvingGroup} for the given name. Use this if you do not need any custom behavior in
     * your own group.
     *
     * @param name The name of the group.
     * @return A standard {@link ICarvingGroup} instance.
     */
    public static ICarvingGroup getDefaultGroupFor(String name) {
        return new SimpleCarvingGroup(name);
    }

    /**
     * Attempts to chisel the {@link ItemStack} from {@code input} into the {@code target} {@link ItemStack}.
     * If it can be chiseled, the input's stack size and NBT data will be copied over
     * to the newly created {@link ItemStack}.
     *
     * @param input  The original {@link ItemStack} to be chiseled. Can't be {@code null}.
     * @param target The desired {@link ItemStack} chiseling target. Stack size and NBT data are ignored.
     *               Can't be {@code null}.
     *
     * @return A copy of the {@code target} {@link ItemStack} with the input's stack count and NBT data,
     *         if this is a valid chiseling operation. Will return {@code null} if it's not possible to chisel
     *         {@code input} into {@code target}.
     */
    public static @Nullable ItemStack tryChiselingInto(@Nonnull ItemStack input, @Nonnull ItemStack target) {
        ICarvingRegistry registry = getChiselRegistry();
        if (registry == null) {
            return null;
        }

        List<ItemStack> chiselGroup = registry.getItemsForChiseling(input);
        if (chiselGroup != null) {
            for (ItemStack variation : chiselGroup) {
                if (target.getItemDamage() == variation.getItemDamage() && target.getItem() == variation.getItem()) {
                    return applyInputToChiselTarget(input, variation);
                }
            }
        }

        return null;
    }

    /**
     * Creates an {@link ItemStack} copy of {@code target} with the stack size and NBT data from {@code input}.
     * Used as a step in a chisel craft. This method does not validate whether the chisel craft from
     * {@code input} to {@code target} does actually exist.
     * <p>
     * In general, you should prefer using the method {@link tryChiselingInto}, as it safeguards against
     * invalid chisel operations. This method is kept as an option in situations where the craft has already been
     * confirmed as valid, and we can skip the expensive operation of creating a list of chisel
     * variants to simply validate it again.
     *
     * @param input  The {@link ItemStack} provider for the stack size and NBT data. Can't be {@code null}.
     * @param target The {@link ItemStack} template that serves as the base for the result. Can't be {@code null}.
     */
    public static @Nonnull ItemStack applyInputToChiselTarget(@Nonnull ItemStack input, @Nonnull ItemStack target) {
        ItemStack result = new ItemStack(target.getItem(), input.stackSize, target.getItemDamage());

        // Copy NBT from input to target to both avoid dupes and prevent loss of stored information
        // (such as when chiseling drawers with items inside)
        NBTTagCompound inputNBT = input.getTagCompound();
        if (inputNBT != null) {
            inputNBT = (NBTTagCompound) inputNBT.copy();
        }

        result.setTagCompound(inputNBT);

        return result;
    }

    public static class SimpleCarvingVariation implements ICarvingVariation {

        private int order;
        private Block block;
        private int meta;
        private int damage;

        public SimpleCarvingVariation(Block block, int metadata, int order) {
            this.order = order;
            this.block = block;
            this.meta = metadata;
            this.damage = metadata;
        }

        @Override
        public Block getBlock() {
            return block;
        }

        @Override
        public int getBlockMeta() {
            return meta;
        }

        @Override
        public int getItemMeta() {
            return damage;
        }

        @Override
        public int getOrder() {
            return order;
        }
    }

    public static class SimpleCarvingGroup implements ICarvingGroup {

        private String name;
        private String sound;
        private String oreName;

        private List<ICarvingVariation> variations = Lists.newArrayList();

        public SimpleCarvingGroup(String name) {
            this.name = name;
        }

        @Override
        public List<ICarvingVariation> getVariations() {
            return Lists.newArrayList(variations);
        }

        @Override
        public void addVariation(ICarvingVariation variation) {
            variations.add(variation);
            Collections.sort(variations, new Comparator<ICarvingVariation>() {

                @Override
                public int compare(ICarvingVariation o1, ICarvingVariation o2) {
                    return CarvingUtils.compare(o1, o2);
                }
            });
        }

        @Override
        public boolean removeVariation(ICarvingVariation variation) {
            ICarvingVariation toRemove = null;
            for (ICarvingVariation v : variations) {
                if (v.getBlock() == variation.getBlock() && v.getBlockMeta() == variation.getBlockMeta()) {
                    toRemove = v;
                }
            }
            return toRemove == null ? false : variations.remove(toRemove);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSound() {
            return sound;
        }

        @Override
        public void setSound(String sound) {
            this.sound = sound;
        }

        @Override
        public String getOreName() {
            return oreName;
        }

        @Override
        public void setOreName(String oreName) {
            this.oreName = oreName;
        }
    }

}
