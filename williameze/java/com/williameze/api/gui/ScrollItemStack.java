package com.williameze.api.gui;

import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.williameze.api.lib.DrawHelper;
import com.williameze.api.math.Vector;

public class ScrollItemStack extends ScrollObject
{
    public ItemStack is;

    public ScrollItemStack(PanelScrollList panel, ItemStack is, double occupyHeight)
    {
	this(panel, is, occupyHeight, 16D);
    }

    public ScrollItemStack(PanelScrollList panel, ItemStack is, double occupyHeight, double stackDrawSize)
    {
	this(panel, is, occupyHeight, true, true, stackDrawSize);
    }

    public ScrollItemStack(PanelScrollList panel, ItemStack is, double occupyHeight, boolean xCentered, boolean yCentered,
	    double stackDrawSize)
    {
	this(panel, is, occupyHeight, new Vector(xCentered ? panel.panelWidth / 2 : stackDrawSize / 2, yCentered ? occupyHeight / 2
		: stackDrawSize / 2, 0), stackDrawSize);
    }

    public ScrollItemStack(PanelScrollList panel, ItemStack is, double occupyHeight, Vector stackCenter, double stackDrawSize)
    {
	this(panel, is, occupyHeight, new Vector(stackCenter.x - stackDrawSize / 2, stackCenter.y - stackDrawSize / 2, stackCenter.z),
		new Vector(stackCenter.x + stackDrawSize / 2, stackCenter.y + stackDrawSize / 2, stackCenter.z));
    }

    public ScrollItemStack(PanelScrollList panel, ItemStack is, double occupyHeight, Vector stackDrawV1, Vector stackDrawV2)
    {
	super(panel, occupyHeight);
	localObjectMinX = Math.min(stackDrawV1.x, stackDrawV2.x);
	localObjectMinY = Math.min(stackDrawV1.y, stackDrawV2.y);
	localObjectMaxX = Math.max(stackDrawV1.x, stackDrawV2.x);
	localObjectMaxY = Math.max(stackDrawV1.y, stackDrawV2.y);
	this.is = is;
    }

    @Override
    public void draw()
    {
	super.draw();
	double size = Math.min(localObjectMaxX - localObjectMinX, localObjectMaxY - localObjectMinY);
	double originalSize = 16D;
	double scale = size / originalSize;
	GL11.glPushMatrix();
	GL11.glTranslated((localObjectMinX + localObjectMaxX) / 2 - size / 2, (localObjectMinY + localObjectMaxY) / 2 - size / 2, 0);
	GL11.glScaled(scale, scale, scale);
	DrawHelper.drawItemStack(is, 0, 0, null);
	GL11.glPopMatrix();
    }
}
