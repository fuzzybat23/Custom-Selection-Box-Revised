package com.fuzzybat23.esbb;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Config(modid = ESBB.MODID)
@LangKey("esbb.config.title")
public class ModConfig
{
    @Name("Enhanced Selection Bounding Box Frame")
    @Comment("Color and opacity for the selection bounding box wire frame.")
    public static final Frame aFrame = new Frame();

    @Name("Enhanced Selection Bounding Box Cube")
    @Comment("Color and opacity for selection bounding box inner cube.")
    public static final BlinkAnimation bBlink = new BlinkAnimation();

    @Name("Enhanced Selection Bounding Box Animation")
    @Comment({"Break animation style and toggle the depth", "buffer for the selection bounding box wire frame.."})
    public static final BreakAnimation cBreak = new BreakAnimation();

    public static class Frame
    {
        @Name("1) Red")
        @Comment("Choose a red color value for the selection bounding box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Red = 255;

        @Name("2) Green")
        @Comment("Choose a green color value for the selection bounding box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Green = 128;

        @Name("3) Blue")
        @Comment("Choose a blue color value for the selection bounding box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Blue = 0;

        @Name("4) Alpha Channel")
        @Comment("Choose an alpha channel value for the selection bounding box between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Alpha = 255;

        @Name("5) Wire Thickness")
        @Comment("Choose a wire thickness for the selection bounding box between 1 and 7.")
        @RangeInt(min = 1, max = 7)
        public int Width = 2;
    }

    public static class BlinkAnimation
    {
        @Name("1) Red")
        @Comment("Choose a red color value for the inner cube between between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Red = 0;

        @Name("2) Green")
        @Comment("Choose a green color value for the inner cube between between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Green = 128;

        @Name("3) Blue")
        @Comment("Choose a blue color value for the inner cube between between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Blue = 255;

        @Name("4) Alpha Channel")
        @Comment("Choose an alpha channel value for the inner cube between 0 and 255.")
        @RangeInt(min = 0, max = 255)
        public int Alpha = 30;
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
        @Comment("Choose how fast the selection bounding box inner cube blinks.")
        @RangeInt(min = 0, max = 100)
        public int Speed = 20;

        @Name("3) Depth Buffer")
        @Comment({"Enable or disable the depth buffer for the selection bounding box wire frame.", "This will default to true if SHRINK or DOWN animation is selected"})
        public enumDepthBuffer dBuffer = enumDepthBuffer.Enable;
    }

    @Mod.EventBusSubscriber(modid = ESBB.MODID)
    private static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(ESBB.MODID))
            {
                ConfigManager.sync(ESBB.MODID, Config.Type.INSTANCE);
                ESBB.loadVariables();

                if(ESBB.Animation == 1)
                    ModConfig.cBreak.dBuffer = enumDepthBuffer.Disable;
                if(ESBB.Animation == 2)
                    ModConfig.cBreak.dBuffer = enumDepthBuffer.Disable;

                ConfigManager.sync(ESBB.MODID, Config.Type.INSTANCE);
                ESBB.loadVariables();
            }
        }
    }
}
