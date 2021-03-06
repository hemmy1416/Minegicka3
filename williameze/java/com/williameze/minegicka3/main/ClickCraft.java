package com.williameze.minegicka3.main;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;

import org.apache.commons.lang3.ArrayUtils;

import com.williameze.minegicka3.ModBase;
import com.williameze.minegicka3.main.magicks.Magick;
import com.williameze.minegicka3.main.objects.items.ItemMagickTablet;
import com.williameze.minegicka3.main.objects.items.ItemStaff;
import com.williameze.minegicka3.main.packets.PacketPlayerClickCraft;

public class ClickCraft
{
    public static final String catGeneral = "General", catStaff = "Staff", catMagick = "Magick", catHat = "Hat";

    public static Map<ItemStack, List<Entry<ItemStack, Integer>>> clickCraftRecipes = new LinkedHashMap();
    public static Map<String, Map<ItemStack, List<Entry<ItemStack, Integer>>>> categorizedClickCraftRecipes = new LinkedHashMap();

    public static void load()
    {
	clickCraftRecipes.clear();
	categorizedClickCraftRecipes.clear();
	categorizedClickCraftRecipes.put(catGeneral, new LinkedHashMap());
	categorizedClickCraftRecipes.put(catStaff, new LinkedHashMap());
	categorizedClickCraftRecipes.put(catMagick, new LinkedHashMap());
	generalRecipes();
	stavesRecipe();
	magicksRecipe();
	hatsRecipe();
    }

    public static void generalRecipes()
    {
	registerClickCraftObject(new ItemStack(ModBase.thingy), new Object[] { Items.emerald, Items.gold_ingot });
	registerClickCraftObject(new ItemStack(ModBase.thingyGood), new Object[] { ModBase.thingy, 16 });
	registerClickCraftObject(new ItemStack(ModBase.thingySuper), new Object[] { ModBase.thingyGood, 16 });
	registerClickCraftObject(new ItemStack(ModBase.stick), new Object[] { ModBase.thingy, 2, Items.stick });
	registerClickCraftObject(new ItemStack(ModBase.stickGood), new Object[] { ModBase.thingyGood, 2, ModBase.stick });
	registerClickCraftObject(new ItemStack(ModBase.stickSuper), new Object[] { ModBase.thingySuper, 2, ModBase.stickGood });
	registerClickCraftObject(new ItemStack(ModBase.magicApple), new Object[] { Items.apple, ModBase.thingy });
	registerClickCraftObject(new ItemStack(ModBase.magicGoodApple), new Object[] { Items.golden_apple, ModBase.thingyGood });
	registerClickCraftObject(new ItemStack(ModBase.magicSuperApple),
		new Object[] { new ItemStack(Items.golden_apple, 1, 1), ModBase.thingySuper });
	registerClickCraftObject(new ItemStack(ModBase.magicCookie, 4), new Object[] { Items.cookie, 4, ModBase.thingy });
	registerClickCraftObject(new ItemStack(ModBase.magicGoodCookie, 4), new Object[] { Items.cookie, 4, ModBase.thingyGood });
	registerClickCraftObject(new ItemStack(ModBase.magicSuperCookie, 4), new Object[] { Items.cookie, 4, ModBase.thingySuper });
	registerClickCraftObject(new ItemStack(ModBase.essenceArcane), new Object[] { ModBase.thingy, Items.blaze_rod, 4, Items.fermented_spider_eye,
		16, Items.nether_wart, 4 });
	registerClickCraftObject(new ItemStack(ModBase.essenceCold), new Object[] { ModBase.thingy, Items.snowball, 64 });
	registerClickCraftObject(new ItemStack(ModBase.essenceEarth), new Object[] { ModBase.thingy, Blocks.dirt, 64, Blocks.cobblestone, 16,
		Blocks.obsidian, 4 });
	registerClickCraftObject(new ItemStack(ModBase.essenceFire), new Object[] { ModBase.thingy, Items.flint_and_steel, Items.blaze_rod, 4,
		Items.magma_cream, 4 });
	registerClickCraftObject(new ItemStack(ModBase.essenceIce), new Object[] { ModBase.thingy, Blocks.ice, 8, Items.arrow, 8 });
	registerClickCraftObject(new ItemStack(ModBase.essenceLife), new Object[] { ModBase.thingy, Items.bone, 8, Items.wheat_seeds, 8, Items.cake,
		new ItemStack(Items.dye, 4, 10) });
	registerClickCraftObject(new ItemStack(ModBase.essenceLightning), new Object[] { ModBase.thingy, Items.iron_ingot, 8,
		new ItemStack(Items.dye, 4, 13) });
	registerClickCraftObject(new ItemStack(ModBase.essenceShield), new Object[] { ModBase.thingy, Items.glowstone_dust, 16, Items.golden_apple,
		4, Items.iron_door, new ItemStack(Items.dye, 8, 11) });
	registerClickCraftObject(new ItemStack(ModBase.essenceSteam), new Object[] { ModBase.thingy, Items.flint_and_steel, 2, Items.snowball, 16 });
	registerClickCraftObject(new ItemStack(ModBase.essenceWater), new Object[] { ModBase.thingy, Items.glass_bottle, 3,
		new ItemStack(Items.dye, 4, 4) });
    }

    public static void stavesRecipe()
    {
	registerClickCraftObject(catStaff, new ItemStack(ModBase.staff), new Object[] { ModBase.stick, 3, ModBase.thingy,
		new ItemStack(Items.dye, 1, 13) });
	registerClickCraftObject(catStaff, new ItemStack(ModBase.staffGrand), new Object[] { ModBase.stick, 2, ModBase.stickGood, 2,
		ModBase.thingyGood });
	registerClickCraftObject(catStaff, new ItemStack(ModBase.staffSuper), new Object[] { ModBase.stick, 2, ModBase.stickSuper, 3,
		ModBase.thingySuper, new ItemStack(Items.dye, 3, 1) });
	registerClickCraftObject(catStaff, new ItemStack(ModBase.staffBlessing), new Object[] { ModBase.staff, ModBase.essenceLife, 2,
		Items.glass_bottle, Items.fermented_spider_eye, 2, Items.magma_cream, Items.blaze_powder, Items.ghast_tear, Items.redstone, 8,
		Items.glowstone_dust, 8, Items.speckled_melon, Items.golden_carrot, Items.sugar, 4, new ItemStack(Items.fish, 1, 3) });
	registerClickCraftObject(catStaff, new ItemStack(ModBase.staffDestruction), new Object[] { ModBase.staff, ModBase.essenceArcane,
		ModBase.essenceFire, ModBase.essenceEarth, Items.gunpowder, 8, Items.flint_and_steel, Items.blaze_powder, 2 });
	registerClickCraftObject(catStaff, new ItemStack(ModBase.staffTelekinesis), new Object[] { ModBase.staff, ModBase.essenceSteam, Items.arrow,
		16, Items.feather, 16, Blocks.sticky_piston });
	registerClickCraftObject(catStaff, new ItemStack(ModBase.staffManipulation), new Object[] { ModBase.staff, ModBase.essenceArcane,
		ModBase.essenceLife, Items.golden_carrot, Items.fermented_spider_eye, 2, Items.redstone, 2, Items.rotten_flesh, 4 });
    }

    public static void magicksRecipe()
    {
	List<ItemStack> l = ((ItemMagickTablet) ModBase.magickTablet).allMagickPages();
	for (int a = 0; a < l.size(); a++)
	{
	    ItemStack is = l.get(a);
	    Magick m = ((ItemMagickTablet) ModBase.magickTablet).getUnlocking(is);
	    if (m != null && m.craftable) registerClickCraftObject(catMagick, is, m.getCraftClickTabletRecipe());
	}

    }

    public static void hatsRecipe()
    {
	registerClickCraftObject(catHat, new ItemStack(ModBase.hat), new Object[] { Items.leather, 8, Items.dye, ModBase.thingy });
	registerClickCraftObject(catHat, new ItemStack(ModBase.hatRisk), new Object[] { ModBase.hat, ModBase.essenceLife,
		new ItemStack(Items.dye, 3, 1) });
    }

    public static List<Entry<ItemStack, Integer>> getRecipe(ItemStack is)
    {
	return getRecipeFrom(is, clickCraftRecipes);
    }

    public static List<Entry<ItemStack, Integer>> getRecipe(String category, ItemStack is)
    {
	if (categorizedClickCraftRecipes.containsKey(category))
	{
	    return getRecipeFrom(is, categorizedClickCraftRecipes.get(category));
	}
	else return null;
    }

    public static List<Entry<ItemStack, Integer>> getRecipeFrom(ItemStack is, Map<ItemStack, List<Entry<ItemStack, Integer>>> recipes)
    {
	Iterator<Entry<ItemStack, List<Entry<ItemStack, Integer>>>> ite = recipes.entrySet().iterator();
	while (ite.hasNext())
	{
	    Entry<ItemStack, List<Entry<ItemStack, Integer>>> entry = ite.next();
	    ItemStack entryIS = entry.getKey();
	    if (entryIS != null && is != null && entryIS.getItem() == is.getItem() && entryIS.getItemDamage() == is.getItemDamage()
		    && entryIS.stackSize == is.stackSize)
	    {
		if (entryIS.stackTagCompound == null && is.stackTagCompound == null) return entry.getValue();

		if (entryIS.stackTagCompound != null && is.stackTagCompound != null && entryIS.stackTagCompound.equals(is.stackTagCompound))
		{
		    return entry.getValue();
		}
	    }
	}
	return null;
    }

    public static void registerClickCraftObject(ItemStack output, Object... objects)
    {
	registerClickCraftObject(catGeneral, output, objects);
    }

    public static void registerClickCraftObject(String category, ItemStack output, Object... objects)
    {
	Entry<ItemStack, List<Entry<ItemStack, Integer>>> entry = getClickCraftObjectRecipe(output, objects);
	if (!categorizedClickCraftRecipes.containsKey(category))
	{
	    categorizedClickCraftRecipes.put(category, new LinkedHashMap());
	}
	Map<ItemStack, List<Entry<ItemStack, Integer>>> map = categorizedClickCraftRecipes.get(category);
	map.put(entry.getKey(), entry.getValue());
	clickCraftRecipes.put(entry.getKey(), entry.getValue());
    }

    private static Entry<ItemStack, List<Entry<ItemStack, Integer>>> getClickCraftObjectRecipe(ItemStack output, Object... objects)
    {
	if (output == null || objects == null || objects.length <= 0) return null;

	List<Entry<ItemStack, Integer>> recipe = new ArrayList();
	for (int a = 0; a < objects.length; a++)
	{
	    Object o1 = objects[a];
	    if (o1 == null) continue;
	    ItemStack toAdd = null;
	    if (o1 instanceof Block || o1 instanceof Item)
	    {
		int value = 1;
		if (a < objects.length - 1)
		{
		    Object o2 = objects[a + 1];
		    if (o2 instanceof Integer)
		    {
			value = Integer.parseInt(o2.toString());
			a++;
		    }
		}
		if (o1 instanceof Block) toAdd = new ItemStack((Block) o1, value);
		else toAdd = new ItemStack((Item) o1, value);
	    }
	    else if (o1 instanceof ItemStack) toAdd = (ItemStack) o1;

	    boolean hasThisAlready = false;
	    for (int b = 0; b < recipe.size(); b++)
	    {
		Entry<ItemStack, Integer> entry = recipe.get(b);
		ItemStack prevStack = entry.getKey();
		int value = entry.getValue();
		if (ItemStack.areItemStackTagsEqual(toAdd, prevStack) && toAdd.isItemEqual(prevStack))
		{
		    hasThisAlready = true;
		    value += toAdd.stackSize;
		    entry.setValue(value);
		    recipe.set(b, entry);
		    break;
		}
	    }

	    if (!hasThisAlready)
	    {
		Entry<ItemStack, Integer> entry = new SimpleEntry<ItemStack, Integer>(toAdd, toAdd.stackSize);
		recipe.add(entry);
	    }
	}
	if (output.getItem() instanceof ItemStaff) ((ItemStaff) output.getItem()).getStaffTag(output);
	if (recipe.isEmpty()) return null;
	else return new SimpleEntry<ItemStack, List<Entry<ItemStack, Integer>>>(output, recipe);
    }

    public static void clientPlayerQueueCraft(EntityPlayer p, ItemStack is, int repeat)
    {
	ModBase.packetPipeline.sendToServer(new PacketPlayerClickCraft(p, is, repeat));
    }

    public static void playerCraft(EntityPlayer p, ItemStack is, int repeat)
    {
	List<Entry<ItemStack, Integer>> recipe = getRecipe(is);
	if (recipe == null)
	{
	    System.err.println("ClickCraft error. No recipe found for " + is.toString());
	    return;
	}
	for (int a = 0; a < repeat; a++)
	{
	    ItemStack toAdd = is.copy();
	    int addable = 0;
	    for (int b = 0; b < p.inventory.mainInventory.length; b++)
	    {
		ItemStack slot = p.inventory.getStackInSlot(b);
		if (slot == null)
		{
		    addable = toAdd.stackSize;
		}
		else if (slot.isItemEqual(toAdd) && ItemStack.areItemStackTagsEqual(slot, toAdd))
		{
		    addable += slot.getMaxStackSize() - slot.stackSize;
		}
		if (addable >= toAdd.stackSize) break;
	    }
	    if (addable >= toAdd.stackSize)
	    {
		for (int b = 0; b < p.inventory.mainInventory.length; b++)
		{
		    ItemStack slot = p.inventory.getStackInSlot(b);
		    if (slot == null)
		    {
			p.inventory.setInventorySlotContents(b, toAdd.copy());
			toAdd.stackSize = 0;
		    }
		    else if (slot.isItemEqual(toAdd) && ItemStack.areItemStackTagsEqual(slot, toAdd))
		    {
			int addAmount = Math.min(slot.getMaxStackSize() - slot.stackSize, toAdd.stackSize);
			slot.stackSize += addAmount;
			toAdd.stackSize -= addAmount;
		    }
		    if (toAdd.stackSize <= 0) break;
		}
		for (Entry<ItemStack, Integer> entry : recipe)
		{
		    ItemStack recipeIS = entry.getKey();
		    int stackSize = entry.getValue();

		    for (int b = 0; b < p.inventory.mainInventory.length; b++)
		    {
			ItemStack slot = p.inventory.getStackInSlot(b);
			if (slot != null && slot.isItemEqual(recipeIS) && ItemStack.areItemStackTagsEqual(slot, recipeIS))
			{
			    int reducing = Math.min(stackSize, slot.stackSize);
			    slot.stackSize -= reducing;
			    if (slot.stackSize <= 0)
			    {
				slot = null;
				p.inventory.setInventorySlotContents(b, slot);
			    }
			    stackSize -= reducing;
			}
			if (stackSize <= 0) break;
		    }
		}
	    }
	}
	p.inventory.markDirty();
    }
}
