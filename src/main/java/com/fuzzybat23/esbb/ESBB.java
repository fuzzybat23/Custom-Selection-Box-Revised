package com.fuzzybat23.esbb;

import com.fuzzybat23.esbb.proxy.CommonProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.fuzzybat23.esbb.DrawSelectionCube.drawSelectionCube;
import static com.fuzzybat23.esbb.DrawSelectionBoundingBox.drawSelectionBoundingBox;
import static com.fuzzybat23.esbb.GetBlockDamage.getBlockDamage;



@net.minecraftforge.fml.common.Mod(modid = "esbb", version = "1.0.1.1", clientSideOnly = true, acceptedMinecraftVersions = "[1.12, 1.13)")
public class ESBB
{
    @net.minecraftforge.fml.common.Mod.Instance("ESBB")
    public static final String MODID = "esbb";
    //public static final Logger LOGGER = LogManager.getLogger();

    public static final String CLIENT_PROXY_CLASS = "com.fuzzybat23.esbb.proxy.ClientProxy";
    public static final String CLIENT_SERVER_CLASS = "com.fuzzybat23.esbb.proxy.ServerProxy";

    public static float Red;
    public static float Green;
    public static float Blue;
    public static float Alpha;
    public static float Width;

    public static float bRed;
    public static float bGreen;
    public static float bBlue;
    public static float bAlpha;

    static int Animation;
    public static boolean DepthBuffer;
    static float Speed;

    @Mod.Instance
    public static ESBB instance;

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = CLIENT_SERVER_CLASS)
    public static CommonProxy proxy;


    @EventHandler
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) throws InvocationTargetException, IllegalAccessException
    {
        proxy.registerTickHandler();
        MinecraftForge.EVENT_BUS.register(this);

        Method method = ReflectionHelper.findMethod(ConfigManager.class, "getConfiguration", null, String.class, String.class);
        Configuration config = (Configuration)method.invoke(null, ESBB.MODID, null);

        ConfigCategory esbbf = config.getCategory("general.enhanced selection bounding box frame");

        for(Property p: esbbf.getOrderedValues())
        {  p.setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class);  }

        ConfigCategory esbbc = config.getCategory("general.enhanced selection bounding box cube");

        for(Property p: esbbc.getOrderedValues())
        {  p.setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class);  }

        ConfigCategory esbba = config.getCategory("general.enhanced selection bounding box animation");
        Property esbba_speed_prop = esbba.get("2) Blink Animation Speed");
        esbba_speed_prop.setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class);

        loadVariables();
    }

    @SubscribeEvent
    public void onDrawBlockSelectionBox(DrawBlockHighlightEvent event)
    {
        drawSelectionBox(event.getPlayer(), event.getTarget(), event.getSubID(), event.getPartialTicks());
        event.setCanceled(true);
    }

    static void loadVariables()
    {
        Red = getFloat(ModConfig.aFrame.Red, 255.0F, 0.0F, 1.0F);
        Green = getFloat(ModConfig.aFrame.Green, 255.0F, 0.0F, 1.0F);
        Blue = getFloat(ModConfig.aFrame.Blue, 255.0F, 0.0F, 1.0F);
        Alpha = getFloat(ModConfig.aFrame.Alpha, 255.0F, 0.0F, 1.0F);
        Width = getFloat(ModConfig.aFrame.Width, 1.0F, 0.1F, 7.0F);

        bRed = getFloat(ModConfig.bBlink.Red, 255.0F, 0.0F, 1.0F);
        bGreen = getFloat(ModConfig.bBlink.Green, 255.0F, 0.0F, 1.0F);
        bBlue = getFloat(ModConfig.bBlink.Blue, 255.0F, 0.0F, 1.0F);
        bAlpha = getFloat(ModConfig.bBlink.Alpha, 255.0F, 0.0F, 1.0F);

        switch(ModConfig.cBreak.Animation)
        {
            case NONE:
                Animation = 0;
                break;

            case SHRINK:
                Animation = 1;
                break;

            case DOWN:
                Animation = 2;
                break;

            default:
                Animation = 0;
        }
        Speed = getFloat(ModConfig.cBreak.Speed, 100.0F, 0.0F, 1.0F);
        switch(ModConfig.cBreak.dBuffer)
        {
            case Enable:
                DepthBuffer = false;
                break;

            case Disable:
                DepthBuffer = true;
                break;

            default:
                DepthBuffer = false;
        }
    }

    private static float getFloat(int i, float f, float min, float max)
    {
        return between((i/f), min, max);
    }

    private static float between(float i, float x, float y)
    {
        return(i < x ? x : i > y ? y : i);
    }

    private void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            if (DepthBuffer)
            {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(Width);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);

            // removed:  && mc.objectMouseOver.getBlockPos() != null
            if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos blockpos = mc.objectMouseOver.getBlockPos();
                IBlockState iblockstate = player.getEntityWorld().getBlockState(blockpos);

                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;

                AxisAlignedBB bb = iblockstate.getSelectedBoundingBox(player.getEntityWorld(), blockpos).grow(0.0020000000949949026D).offset(-d0, -d1, -d2);

                // Do block break progress calc
                if(Animation == 1 || Animation == 2)
                {
                    float breakProgress = getBlockDamage(movingObjectPositionIn);
                    //float breakProgress = getBlockDamage(player, partialTicks);

                    if (Animation == 1)
                        bb = bb.shrink(breakProgress / 2);

                    if (Animation == 2)
                        bb = bb.contract(0.0, breakProgress, 0.0);
                }

                // Draw blinking cube
                drawSelectionCube(bb, bRed, bGreen, bBlue, bAlpha);

                // Draw custom selection box
                drawSelectionBoundingBox(bb, Red, Green, Blue, Alpha);
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();

            if (DepthBuffer)
                GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
    }
}


