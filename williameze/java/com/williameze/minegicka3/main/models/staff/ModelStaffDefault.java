package com.williameze.minegicka3.main.models.staff;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.williameze.api.math.Vector;
import com.williameze.api.models.Cylinder;
import com.williameze.api.models.CylinderConjunc;
import com.williameze.api.models.ModelObject;
import com.williameze.api.models.Sphere;
import com.williameze.minegicka3.ModBase;
import com.williameze.minegicka3.main.Values;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelStaffDefault extends ModelStaff
{
    public static Color defaultOrbColor = new Color(0.8F, 0, 0.9F, 1);
    public Sphere orb;

    @Override
    public void addComponents()
    {
	Color c = new Color(255, 255, 100, 255);
	Color c1 = new Color(255, 255, 130, 255);
	Color c2 = new Color(255, 255, 70, 255);

	components.add(CylinderConjunc.createTorus(new Vector(0, 8, 0), new Vector(0, 11, 0), new Vector(1, 0, 0), 0, 16, 0.4685,
		16).setColor(c1));
	components.add(orb = (Sphere) new Sphere(0, 11, 0, 1, 2, 4).setColor(defaultOrbColor).setPivot(new Vector(0,11,0)));
	components.add(Cylinder.create(new Vector(0, 8, 0), new Vector(0, -6, 0), 0.4685, 16).setColor(c));
	components.add(new Sphere(0, -6.25, 0, 0.75, 2, 4).setColor(c2));
    }

    @Override
    public void componentPreRender(ItemStack staff, ModelObject o)
    {
	super.componentPreRender(staff, o);
	if (o == orb)
	{
	    if (Minecraft.getMinecraft().thePlayer.inventory.hasItemStack(staff))
	    {
		float manaRate = (float) ModBase.proxy.getCoreClient().getManaRate();
		orb.setColor(new Color(0.8F, 0.8F * Math.max(0, 0.8F - manaRate), 0.9F * manaRate, 1));
	    }
	    orb.setRotation(Values.clientTicked*2, 0);
	}
    }

    @Override
    public void componentPostRender(ItemStack staff, ModelObject o)
    {
	super.componentPostRender(staff, o);
	if (o == orb)
	{
	    if (Minecraft.getMinecraft().thePlayer.inventory.hasItemStack(staff))
	    {
		orb.setColor(defaultOrbColor);
	    }
	    orb.setRotation(-Values.clientTicked*2, 0);
	}
    }
}
