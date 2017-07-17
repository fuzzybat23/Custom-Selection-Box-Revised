package com.fuzzybat23.csbr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;

class DrawSelectionCube
{
    static void drawSelectionCube(AxisAlignedBB box, float red, float green, float blue, float alpha)
    {
        drawCube(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
    }

    private static void drawCube(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder b = t.getBuffer();
        b.begin(7, DefaultVertexFormats.POSITION_COLOR);

        if(alpha > 0.0F)
        {
            if(CSBR.Speed > 0)
                alpha *= (float) Math.abs(Math.sin(Minecraft.getSystemTime() / 100.0D * CSBR.Speed));

            drawCube(b, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
            t.draw();
        }
    }

    private static void drawCube(BufferBuilder b, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha)
    {
        //up
        b.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        //b1.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();

        //down
        b.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        //b1.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();

        //north
        b.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        //b1.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();

        //south
        b.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        //b1.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();

        //east
        b.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        //b1.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();

        //west
        b.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        //b1.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
    }
}
