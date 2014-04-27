package com.williameze.minegicka3.main.entities;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.williameze.api.lib.FuncHelper;
import com.williameze.api.math.IntVector;
import com.williameze.api.math.Vector;
import com.williameze.minegicka3.main.Values;
import com.williameze.minegicka3.main.spells.DefaultSpellSelector;
import com.williameze.minegicka3.main.spells.Spell;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityProjectile extends Entity implements IEntityAdditionalSpawnData
{
    public Spell spell = Spell.none;
    public double gravity;
    public double friction;
    public int onGroundTick;

    public EntityProjectile(World par1World)
    {
	super(par1World);
	renderDistanceWeight = Values.renderDistance;
	setSize(0.5F, 0.5F);
	gravity = 0.01;
	friction = 0.985;
	onGroundTick = 0;
    }

    @Override
    public boolean isInRangeToRenderDist(double par1)
    {
	return par1 < renderDistanceWeight * renderDistanceWeight;
    }

    @Override
    public AxisAlignedBB getBoundingBox()
    {
	return boundingBox;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity e)
    {
	if (e == null || e instanceof EntityProjectile || spell.getCaster() == e || !e.canBeCollidedWith()) return null;
	return boundingBox;
    }

    @Override
    public boolean canBeCollidedWith()
    {
	return true;
    }

    @Override
    protected void entityInit()
    {
    }

    public void setSpell(Spell s)
    {
	spell = s;
    }

    public Spell getSpell()
    {
	return spell;
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	ticksExisted++;

	prevPosX = posX;
	prevPosY = posY;
	prevPosZ = posZ;
	moveEntity(motionX, motionY, motionZ);
	if (!onGround) motionY -= gravity;
	motionX *= friction;
	motionY *= friction;
	motionZ *= friction;

	if (isCollided)
	{
	    motionX *= 0.9 * friction;
	    motionY *= 0.9 * friction;
	    motionZ *= 0.9 * friction;
	    onGroundTick++;
	}

	if (!isDead)
	{
	    List<IntVector> blocks = getBlocksWithinAABB();
	    for (IntVector i : blocks)
	    {
		collideWithBlock(i.x, i.y, i.z);
	    }
	}
	if (!isDead)
	{
	    List<Entity> entities = FuncHelper.getEntitiesWithinBoundingBoxMovement(worldObj, boundingBox, new Vector(motionX, motionY, motionZ), EntityLivingBase.class,
		    new DefaultSpellSelector(getSpell()));
	    entities.remove(spell.getCaster());
	    Entity e = FuncHelper.getEntityClosestTo(posX, posY, posZ, entities);
	    collideWithEntity(e);
	}

	if (onGroundTick >= 200 || ticksExisted >= 2000) setDead();
    }

    public void collideWithBlock(int x, int y, int z)
    {

    }

    public void collideWithEntity(Entity e)
    {

    }

    public List<IntVector> getBlocksWithinAABB()
    {
	AxisAlignedBB bb2 = boundingBox;
	int i = MathHelper.floor_double(bb2.minX - 0.2D);
	int j = MathHelper.floor_double(bb2.maxX + 0.2D);
	int k = MathHelper.floor_double(bb2.minY - 0.2D);
	int l = MathHelper.floor_double(bb2.maxY + 0.2D);
	int i1 = MathHelper.floor_double(bb2.minZ - 0.2D);
	int j1 = MathHelper.floor_double(bb2.maxZ + 0.2D);

	if (bb2.minX < 0.0D)
	{
	    --i;
	}

	if (bb2.minY < 0.0D)
	{
	    --k;
	}

	if (bb2.minZ < 0.0D)
	{
	    --i1;
	}
	List<IntVector> list = new ArrayList();

	for (int k1 = i; k1 < j; ++k1)
	{
	    for (int l1 = k; l1 < l; ++l1)
	    {
		for (int i2 = i1; i2 < j1; ++i2)
		{
		    Block block = worldObj.getBlock(k1, l1, i2);

		    if (block.getMaterial() != Material.air)
		    {
			list.add(new IntVector(k1, l1, i2));
		    }
		}
	    }
	}

	return list;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1)
    {
	setSpell(Spell.createFromNBT(var1.getCompoundTag("Spell")));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1)
    {
	var1.setTag("Spell", spell.writeToNBT());
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
	try
	{
	    byte[] b = CompressedStreamTools.compress(spell.writeToNBT());
	    buffer.writeInt(b.length);
	    buffer.writeBytes(b);
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    @Override
    public void readSpawnData(ByteBuf additionalData)
    {
	try
	{
	    byte[] b = new byte[additionalData.readInt()];
	    additionalData.readBytes(b);
	    NBTTagCompound tag = CompressedStreamTools.decompress(b);
	    setSpell(Spell.createFromNBT(tag));
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

}
