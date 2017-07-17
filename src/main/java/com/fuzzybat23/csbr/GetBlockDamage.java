package com.fuzzybat23.csbr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.RayTraceResult;
import java.lang.reflect.Field;
import java.util.HashMap;

class GetBlockDamage
{
    static float getBlockDamage(RayTraceResult target)
    {
        try
        {
            Field f;
            try
            {  f = RenderGlobal.class.getDeclaredField("damagedBlocks");  }
             catch (NoSuchFieldException e)
            {  f = RenderGlobal.class.getDeclaredField("damagedBlocks");  }
            f.setAccessible(true);

            HashMap<Integer, DestroyBlockProgress> map = (HashMap<Integer, DestroyBlockProgress>) f.get(Minecraft.getMinecraft().renderGlobal);

            for (DestroyBlockProgress destroyblockprogress : map.values())
            {
                if (destroyblockprogress.getPosition().equals(target.getBlockPos()))
                {
                    if (destroyblockprogress.getPartialBlockDamage() >= 0 && destroyblockprogress.getPartialBlockDamage() <= 10)
                        return destroyblockprogress.getPartialBlockDamage() / 10.0F;
                }
            }
        } catch (Exception e)
        {  e.printStackTrace();  }
        return 0F;
    }
}