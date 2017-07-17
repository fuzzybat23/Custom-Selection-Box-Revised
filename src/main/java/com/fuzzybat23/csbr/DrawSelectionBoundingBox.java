package com.fuzzybat23.csbr;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;

public class DrawSelectionBoundingBox
{
    static void drawSelectionBoundingBox(AxisAlignedBB box, float red, float green, float blue, float alpha)
    {
        drawBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
    }

    private static void drawBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder b = t.getBuffer();
        b.begin(3, DefaultVertexFormats.POSITION_COLOR);
        drawBoundingBox(b, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
        t.draw();
    }

    private static void drawBoundingBox(BufferBuilder b, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha)
    {
        b.pos(minX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
        b.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(minX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
        b.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
        b.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, maxY, minZ).color(red, green, blue, 0.0F).endVertex();
        b.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        b.pos(maxX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
    }
}
