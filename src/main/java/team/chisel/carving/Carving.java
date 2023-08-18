package team.chisel.carving;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.carving.ICarvingGroup;
import com.cricketcraft.chisel.api.carving.ICarvingRegistry;
import com.cricketcraft.chisel.api.carving.ICarvingVariation;

import team.chisel.Chisel;

public class Carving implements ICarvingRegistry {

    GroupList groups = new GroupList();

    public static final ICarvingRegistry chisel = new Carving();
    public static final Carving needle = new Carving();

    static {
        CarvingUtils.chisel = chisel;
    }

    public static void construct() {
        /* do nothing */
    }

    private Carving() {
        /* do nothing */
    }

    @Override
    public ICarvingVariation getVariation(Block block, int metadata) {
        ICarvingGroup g = getGroup(block, metadata);
        if (g != null) {
            for (ICarvingVariation v : g.getVariations()) {
                if (v.getBlock() == block && v.getBlockMeta() == metadata) {
                    return v;
                }
            }
        }
        return null;
    }

    @Override
    public List<ICarvingVariation> getGroupVariations(Block block, int metadata) {
        ICarvingGroup group = getGroup(block, metadata);
        if (group == null) return Collections.emptyList();

        return group.getVariations();
    }

    @Override
    public String getOreName(Block block, int metadata) {
        ICarvingGroup group = getGroup(block, metadata);
        if (group == null) return null;

        return group.getOreName();
    }

    @Override
    public List<ItemStack> getItemsForChiseling(ItemStack chiseledItem) {
        ArrayList<ItemStack> items = new ArrayList<>();

        ICarvingGroup group = null;

        int[] oreIDs = OreDictionary.getOreIDs(chiseledItem);
        for (int i : oreIDs) {
            group = groups.getGroupByOre(OreDictionary.getOreName(i));
            if (group != null) {
                break;
            }
        }

        if (group == null) {
            group = getGroup(Block.getBlockFromItem(chiseledItem.getItem()), chiseledItem.getItemDamage());
        }

        if (group == null) return items;

        HashSet<String> mapping = new HashSet<>();

        List<ICarvingVariation> variations = group.getVariations();

        for (ICarvingVariation v : variations) {
            String key = Block.getIdFromBlock(v.getBlock()) + "|" + v.getItemMeta();
            if (mapping.add(key)) items.add(new ItemStack(v.getBlock(), 1, v.getItemMeta()));
        }

        String oreName = group.getOreName();
        if (oreName != null) {
            ArrayList<ItemStack> ores = OreDictionary.getOres(oreName);
            for (ItemStack stack : ores) {
                String key = Item.getIdFromItem(stack.getItem()) + "|" + stack.getItemDamage();
                if (mapping.add(key)) items.add(stack);
            }
        }

        return items;
    }

    @Override
    public ICarvingGroup getGroup(Block block, int metadata) {
        int[] ids = OreDictionary.getOreIDs(new ItemStack(block, 1, metadata));
        if (ids.length > 0) {
            for (int id : ids) {
                ICarvingGroup oreGroup = groups.getGroupByOre(OreDictionary.getOreName(id));
                if (oreGroup != null) {
                    return oreGroup;
                }
            }
        }

        return groups.getGroup(block, metadata);
    }

    @Override
    public ICarvingGroup getGroup(String name) {
        return groups.getGroupByName(name);
    }

    @Override
    public ICarvingGroup removeGroup(String groupName) {
        ICarvingGroup g = groups.getGroupByName(groupName);
        return groups.remove(g) ? g : null;
    }

    @Override
    public ICarvingVariation removeVariation(Block block, int metadata) {
        return removeVariation(block, metadata, null);
    }

    @Override
    public ICarvingVariation removeVariation(Block block, int metadata, String group) {
        return groups.removeVariation(block, metadata, group);
    }

    @Override
    public void addVariation(String groupName, Block block, int metadata, int order) {
        if (block == null) {
            throw new NullPointerException("Cannot add variation in group " + groupName + " for null block.");
        }

        ICarvingVariation variation = CarvingUtils.getDefaultVariationFor(block, metadata, order);
        addVariation(groupName, variation);
    }

    @Override
    public void addVariation(String groupName, ICarvingVariation variation) {
        if (groupName == null) {
            throw new NullPointerException("Cannot add variation to null group name.");
        } else if (variation == null) {
            throw new NullPointerException("Cannot add variation in group " + groupName + " for null variation.");
        }

        ICarvingGroup group = groups.getGroupByName(groupName);

        if (group == null) {
            group = CarvingUtils.getDefaultGroupFor(groupName);
            addGroup(group);
        }

        groups.addVariation(groupName, variation);
    }

    @Override
    public void addGroup(ICarvingGroup group) {
        groups.add(group);
    }

    @Override
    public void registerOre(String name, String oreName) {
        ICarvingGroup group = groups.getGroupByName(name);
        if (group != null) {
            group.setOreName(oreName);
        } else {
            throw new NullPointerException("Cannot register ore name for group " + name + ", as it does not exist.");
        }
    }

    @Override
    public void setVariationSound(String name, String sound) {
        ICarvingGroup group = groups.getGroupByName(name);
        if (group != null) {
            group.setSound(sound);
        } else {
            throw new NullPointerException("Cannot set sound for group " + name + ", as it does not exist.");
        }
    }

    @Override
    public String getVariationSound(Block block, int metadata) {
        return getVariationSound(Item.getItemFromBlock(block), metadata);
    }

    @Override
    public String getVariationSound(Item item, int metadata) {
        ICarvingGroup group = groups.getGroup(Block.getBlockFromItem(item), metadata);
        String sound = group == null ? null : group.getSound();
        return sound == null ? Chisel.MOD_ID + ":chisel.fallback" : sound;
    }

    @Override
    public List<String> getSortedGroupNames() {
        List<String> names = new ArrayList<>(groups.getNames());
        Collections.sort(names);
        return names;
    }
}
