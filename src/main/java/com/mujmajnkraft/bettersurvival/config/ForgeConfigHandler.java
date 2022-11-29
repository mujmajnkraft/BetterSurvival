package com.mujmajnkraft.bettersurvival.config;

import com.mujmajnkraft.bettersurvival.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;
import java.util.List;

@Config(modid = Reference.MOD_ID)
public class ForgeConfigHandler {

    @Config.Comment("Server Config")
    @Config.Name("Server")
    public static final ServerConfig server = new ServerConfig();

    @Config.Comment("Client Config")
    @Config.Name("Client")
    public static final ClientConfig client = new ClientConfig();

    public static class ServerConfig {
        @Config.Comment("Allows you to craft weapons from materials from other mods.")
        @Config.Name("Integration")
        @Config.RequiresMcRestart
        public boolean integration = true;

        @Config.Comment("When set to false, disable Vanilla shield recipe, forcing player to use this mod's shields.")
        @Config.Name("Allow Vanilla Shields")
        @Config.RequiresMcRestart
        public boolean allowVanillaShields = true;

        @Config.Comment("How strong should blindness affect mobs (Percentage)")
        @Config.Name("Blindness Strength")
        @Config.RangeDouble(min = 0.0, max = 100.0)
        public double blindnessStrength = 80.0;

        @Config.Comment("Prevent Tunneling enchantment from attempting to mine TileEntities.")
        @Config.Name("Prevent Tunneling TileEntities")
        public boolean preventTunnelingTileEntities = true;

        @Config.Comment("Blacklist of mobs to not affect with Blindness.")
        @Config.Name("Blindness Blacklist")
        public List<String> blindnessBlacklist = Collections.singletonList("");
    }

    public static class ClientConfig {
        @Config.Comment("Prevent custom shields from messing up your FoV. This will cause problems with other mods that change FoV.")
        @Config.Name("Custom Shield FOV")
        public boolean fovShields = true;

        @Config.Comment("Prevent FoV changes completely.")
        @Config.Name("Static FOV")
        public boolean staticFOV = false;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler{
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Reference.MOD_ID)) ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
