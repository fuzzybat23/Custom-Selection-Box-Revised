package com.fuzzybat23.esbb;

import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class GetBlockDamage
{

    static float getBlockDamage(RayTraceResult target)
    {
        try
        {
            final Field f = ReflectionHelper.findField(RenderGlobal.class, "damagedBlocks","field_72738_E");

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
    /*
    private static final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.<Integer, DestroyBlockProgress>newHashMap();

    static float getBlockDamage(Entity entityIn, float partialTicks)
    {
        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;

        if (!damagedBlocks.isEmpty())
        {
            Iterator<DestroyBlockProgress> iterator = damagedBlocks.values().iterator();

            while (iterator.hasNext())
            {
                DestroyBlockProgress destroyblockprogress = iterator.next();
                BlockPos blockpos = destroyblockprogress.getPosition();
                double d3 = (double)blockpos.getX() - d0;
                double d4 = (double)blockpos.getY() - d1;
                double d5 = (double)blockpos.getZ() - d2;
                Block block = entityIn.getEntityWorld().getBlockState(blockpos).getBlock();
                TileEntity te = entityIn.getEntityWorld().getTileEntity(blockpos);
                boolean hasBreak = block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull;

                if (!hasBreak) hasBreak = te != null && te.canRenderBreaking();

                if (!hasBreak)
                {
                    if (d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D)
                    {
                        iterator.remove();
                    }
                    else
                    {
                        IBlockState iblockstate = entityIn.getEntityWorld().getBlockState(blockpos);

                        if (iblockstate.getMaterial() != Material.AIR)
                        {
                            return destroyblockprogress.getPartialBlockDamage() / 10.0F;
                        }
                    }
                }
            }
        }
        return 0F;
    }
    */
}