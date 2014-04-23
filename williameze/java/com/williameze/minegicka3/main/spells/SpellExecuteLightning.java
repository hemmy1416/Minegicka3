package com.williameze.minegicka3.main.spells;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;

import com.williameze.minegicka3.main.entities.EntityLightning;
import com.williameze.minegicka3.main.spells.Spell.CastType;

public class SpellExecuteLightning extends SpellExecute
{
    public static Map<Spell, EntityLightning> lightnings = new HashMap();

    @Override
    public void startSpell(Spell s)
    {
	Entity caster = s.getCaster();
	if (caster == null) return;
	EntityLightning lightning = new EntityLightning(caster.worldObj);
	lightning.spell = s;
	lightning.setPosition(caster.posX, caster.posY, caster.posZ);
	if (!caster.worldObj.isRemote)
	{
	    caster.worldObj.spawnEntityInWorld(lightning);
	}
	lightnings.put(s, lightning);
    }

    @Override
    public void updateSpell(Spell s)
    {
	EntityLightning lig = lightnings.get(s);
	if (lig != null && !lig.originAndChainedMap.isEmpty())
	{
	    if (consumeMana(s, s.countElements() * 2.2, true, false, 0) == 0)
	    {
		s.toBeStopped = true;
	    }
	}
	if (s.spellTicks > 75 + s.countElements() * 25 || s.castType == CastType.Single && s.getCaster().getLookVec() == null)
	{
	    s.toBeStopped = true;
	}
    }

    @Override
    public void stopSpell(Spell s)
    {
	s.toBeStopped = true;
	lightnings.remove(s);
    }
}