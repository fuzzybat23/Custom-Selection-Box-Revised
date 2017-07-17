package com.fuzzybat23.csbr;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sun.security.util.DisabledAlgorithmConstraints;


@Config(modid = CSBR.MODID)
@LangKey("csbr.config.title")
public class ModConfig
{
    @Name("Custom Selection Box Frame")
    @Comment("Color and opacity for custom selection box wire frame.")
    public static final Frame aFrame = new Frame();

    @Name("Custom Selection Box Cube")
    @Comment("Color and opacity for custom selection box inner cube.")
    public static final BlinkAnimation bBlink = new BlinkAnimation();

    @Name("Custom Selection Box Animation")
    @Comment({"Break animation style and toggle the depth", "buffer for the custom selection box wire frame.."})
    public static final BreakAnimation cBreak = new BreakAnimation();

    public static class Frame
    {
        @Name("1) Red")
        @Comment("Choose a red color value for the custom selection box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Red = 255;

        @Name("2) Green")
        @Comment("Choose a green color value for the custom selection box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Green = 255;

        @Name("3) Blue")
        @Comment("Choose a blue color value for the custom selection box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Blue = 255;

        @Name("4) Alpha Channel")
        @Comment("Choose an alpha channel value for the custom selection box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Alpha = 255;

        @Name("5) Wire Thickness")
        @Comment("Choose a wire thickness for the custom selection box between 1 and 7.")
        @RangeInt(min = 1, max = 7)
        public int Width = 2;
    }

    public static class BlinkAnimation
    {
        @Name("1) Red")
        @Comment("Choose a red color value for the inner cube between between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Red = 255;

        @Name("2) Green")
        @Comment("Choose a green color value for the inner cube between between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Green = 255;

        @Name("3) Blue")
        @Comment("Choose a blue color value for the inner cube between between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Blue = 255;

        @Name("4) Alpha Channel")
        @Comment("Choose an alpha channel value for the inner cube between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Alpha = 255;
    }

    public enum enumAnimation
    {
        NONE,
        SHRINK,
        DOWN,
    }

    public enum enumDepthBuffer
    {
        Enable,
        Disable
    }

    public static class BreakAnimation
    {
        @Name("1) Break Animation")
        @Comment({"Break Animation NOTE:  SHRINK and DOWN animations will", "not work properly unless Depth Buffer is turned on."})
        public enumAnimation Animation = enumAnimation.NONE;

        @Name("2) Blink Animation Speed")
        @Comment("Choose how fast the custom selection box inner cube blinks.")
        @RangeInt(min = 0, max = 100)
        public int Speed = 0;

        @Name("3) Depth Buffer")
        @Comment({"Enable or disable the depth buffer for the custom selection box wire frame.", "This will default to true if SHRINK or DOWN animation is selected"})
        public enumDepthBuffer dBuffer = enumDepthBuffer.Enable;
    }

    @Mod.EventBusSubscriber(modid = CSBR.MODID)
    private static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(CSBR.MODID))
            {
                ConfigManager.sync(CSBR.MODID, Config.Type.INSTANCE);
                CSBR.loadVariables();

                if(CSBR.Animation == 1)
                    ModConfig.cBreak.dBuffer = enumDepthBuffer.Disable;
                if(CSBR.Animation == 2)
                    ModConfig.cBreak.dBuffer = enumDepthBuffer.Disable;

                ConfigManager.sync(CSBR.MODID, Config.Type.INSTANCE);
                CSBR.loadVariables();
            }
        }
    }
}