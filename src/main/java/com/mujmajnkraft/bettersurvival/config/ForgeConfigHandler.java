package com.mujmajnkraft.bettersurvival.config;

import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

        private List<Class<?>> weaponWhitelist = null;
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

        @Config.Comment("Multiplier of arrow velocity for the Range enchantment.")
        @Config.Name("Range Multiplier")
        @Config.RangeDouble(min = 1.0, max = 10.0)
        public double rangeVelocity = 1.5D;

        @Config.Comment("Prevent Tunneling enchantment from attempting to mine TileEntities.")
        @Config.Name("Prevent Tunneling TileEntities")
        public boolean preventTunnelingTileEntities = true;

        @Config.Comment("Blacklist of mobs to not affect with Blindness.")
        @Config.Name("Blindness Blacklist")
        public String[] blindnessBlacklist = new String[]{""};

        @Config.Comment("When applying a potion to a weapon, how many hits should a dose give on the weapon.")
        @Config.Name("Potion Hits Amount")
        @Config.RangeInt(min = 1, max = 256)
        public int potionHits = 64;

        @Config.Comment("Maximum number of hits to apply to a weapon with repeated doses.")
        @Config.Name("Maximum Potion Hits")
        @Config.RangeInt(min = 1, max = 512)
        public int maximumPotionHits = 64;

        @Config.Comment("When applying a potion to a weapon, what to divide the potion's time by for a single dose.")
        @Config.Name("Potion Timer Divider")
        @Config.RangeInt(min = 1, max = 256)
        public int potionDivisor = 8;

        @Config.Comment("Whitelist of weapons classes that can have potions applied to them.")
        @Config.Name("Potion-Applying Weapon Whitelist")
        public String[] paWeaponWhitelist = new String[]{"net.minecraft.item.ItemSword"};

        @Config.Comment("Blacklist of potions that can be applied to weapons.")
        @Config.Name("Potion-Applying Potion Blacklist")
        public String[] paPotionBlacklist = new String[]{""};

        @Config.Comment("Chance per full-strength hit that Stun procs.")
        @Config.Name("Stun Base Chance")
        public float stunBaseChance = 0.1F;

        @Config.Comment("Increased Stun chance per level of Bash")
        @Config.Name("Bash Stun Modifier")
        public float bashModifier = 0.05F;

        @Config.Comment("Chance per full-strength hit that Disarm procs.")
        @Config.Name("Disarm Base Chance")
        public float disarmBaseChance = 0.1F;

        @Config.Comment("Increased Disarm chance per level of Disarm")
        @Config.Name("Bash Stun Modifier")
        public float disarmModifier = 0.05F;

        public boolean isClassInstanceofWhitelistedWeapon(Class<?> clazz) {
            return ForgeConfigHandler.server.getWeaponWhitelistClasses().stream().anyMatch(w -> w.isAssignableFrom(clazz));
        }

        private List<Class<?>> getWeaponWhitelistClasses() {
            if(weaponWhitelist==null) {
                weaponWhitelist = new ArrayList<>();
                for(String name : paWeaponWhitelist) {
                    try { weaponWhitelist.add(Class.forName(name)); }
                    catch(Exception ex) { BetterSurvival.LOG.log(Level.WARN, "Failed to parse weapon whitelist entry: " + name); }
                }
            }
            return weaponWhitelist;
        }

        public void refreshCache() { weaponWhitelist = null; }
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
            if(event.getModID().equals(Reference.MOD_ID)) {
                ForgeConfigHandler.server.refreshCache();
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
