package com.williameze.minegicka3.main.magicks;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.williameze.api.lib.FuncHelper;
import com.williameze.minegicka3.ModBase;

public class MagickHaste extends Magick
{

    public MagickHaste()
    {
	super("Haste", "HAF");
    }

    @Override
    public Object[] getAdditionalCraftClickTabletMaterials()
    {
	return new Object[] {Items.carrot, Items.apple};
    }

    @Override
    public double getBaseManaCost()
    {
	return 100;
    }

    @Override
    public void doTheMagick(World world, double x, double y, double z, Entity caster, NBTTagCompound additionalData)
    {
	if (caster instanceof EntityLivingBase)
	{
	    double[] props = getStaffMainProperties(additionalData);
	    ((EntityLivingBase) caster).addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), (int) (100 * props[0]), (int) Math
		    .ceil(props[0] - 1)));
	}
    }
}
