package com.mujmajnkraft.bettersurvival.config;

import com.mujmajnkraft.bettersurvival.BetterSurvival;
import com.mujmajnkraft.bettersurvival.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

@Config(modid = Reference.MOD_ID)
public class ForgeConfigHandler {

    @Config.Comment("Weapon Config (server)")
    @Config.Name("Weapons")
    public static final WeaponConfig weapons = new WeaponConfig();
    
    @Config.Comment("Potion Config (server)")
    @Config.Name("Potions")
    public static final PotionConfig potions = new PotionConfig();
    
    @Config.Comment("Enchantment Config (server)")
    @Config.Name("Enchantments")
    public static final EnchantmentConfig enchantments = new EnchantmentConfig();
    
    @Config.Comment("Material Config (server)")
    @Config.Name("Materials")
    public static final MaterialConfig materials = new MaterialConfig();

    @Config.Comment("Client Config")
    @Config.Name("Client")
    public static final ClientConfig client = new ClientConfig();
    
    public static class EnchantmentConfig {
    	@Config.Name("Assassinate Max Level")
        @Config.RangeInt(min = 0, max = 10)
    	public int assassinteLevel = 3;

        @Config.Name("Agility Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int agilityLevel = 2;

        @Config.Name("Arrow Recovery Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int arrowRecoveryLevel = 3;

        @Config.Name("Bash Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int bashLevel = 3;

        @Config.Name("Blast Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int blastLevel = 2;

        @Config.Name("Blocking Power Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int blockingPowerLevel = 3;
        
        @Config.Name("Combo Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int comboLevel = 3;

        @Config.Name("Diamonds Everywhere Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int diamondsEverywhereLevel = 3;

        @Config.Name("Disarm Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int disarmLevel = 1;

        @Config.Name("Education Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int educationLevel = 3;

        @Config.Name("Fling Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int flingLevel = 2;

        @Config.Name("Heavy Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int heavyLevel = 1;

        @Config.Name("High Jump Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int highJumpLevel = 2;

        @Config.Name("Multishot Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int multishotLevel = 3;

        @Config.Name("Penetration Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int penetrationLevel = 5;

        @Config.Name("Range Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int rangeLevel = 1;

        @Config.Name("Rapid Fire Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int rapidFireLevel = 5;

        @Config.Name("Reflection Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int reflectionLevel = 3;

        @Config.Name("Smelting Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int smeltingLevel = 1;

        @Config.Name("Spell Shield Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int spellShieldLevel = 3;

        @Config.Name("Tunneling Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int tunnelingLevel = 2;

        @Config.Name("Vampirism Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int vampirismLevel = 1;

        @Config.Name("Versatility Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int versatilityLevel = 1;

        @Config.Name("Vitality Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int vitalityLevel = 1;

        @Config.Name("Weightless Max Level")
        @Config.RangeInt(min = 0, max = 10)
        public int weightlessLevel = 1;

        @Config.Comment("Whether or not is Assassinate enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Assassinate Treasure")
        public boolean assassinteTreasure = false;

        @Config.Comment("Whether or not is Agility enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Agility Treasure")
        public boolean agilityTreasure = false;

        @Config.Comment("Whether or not is Arrow Recovery enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Arrow Recovery Treasure")
        public boolean arrowRecoveryTreasure = false;

        @Config.Comment("Whether or not is Bash enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Bash Treasure")
        public boolean bashTreasure = false;

        @Config.Comment("Whether or not is Blast enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Blast Treasure")
        public boolean blastTreasure = false;

        @Config.Comment("Whether or not is Blocking Power enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Blocking Power Treasure")
        public boolean blockingPowerTreasure = false;
        
        @Config.Comment("Whether or not is Combo enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Combo Treasure")
        public boolean comboTreasure = false;

        @Config.Comment("Whether or not is Diamonds Everywhere enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Diamonds Everywhere Treasure")
        public boolean diamondsEverywhereTreasure = false;

        @Config.Comment("Whether or not is Disarm enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Disarm Treasure")
        public boolean disarmTreasure = false;

        @Config.Comment("Whether or not is Education enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Education Treasure")
        public boolean educationTreasure = false;

        @Config.Comment("Whether or not is this Fling a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Fling Treasure")
        public boolean flingTreasure = false;

        @Config.Comment("Whether or not is Heavy enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Heavy Treasure")
        public boolean heavyTreasure = false;

        @Config.Comment("Whether or not is High Jump enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is High Jump Treasure")
        public boolean highJumpTreasure = false;

        @Config.Comment("Whether or not is Multishot enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Multishot Treasure")
        public boolean multishotTreasure = false;

        @Config.Comment("Whether or not is Penetration enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Penetration Treasure")
        public boolean penetrationTreasure = false;

        @Config.Comment("Whether or not is Range enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Range Treasure")
        public boolean rangeTreasure = false;

        @Config.Comment("Whether or not is Rapid Fire enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Rapid Fire Treasure")
        public boolean rapidFireTreasure = false;

        @Config.Comment("Whether or not is Reflection enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Reflection Treasure")
        public boolean reflectionTreasure = false;

        @Config.Comment("Whether or not is Smelting enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Smelting Treasure")
        public boolean smeltingTreasure = false;

        @Config.Comment("Whether or not is Spell Shield enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Spell Shield Treasure")
        public boolean spellShieldTreasure = false;

        @Config.Comment("Whether or not is Tunneling enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Tunneling Treasure")
        public boolean tunnelingTreasure = false;

        @Config.Comment("Whether or not is Vampirism enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Vampirism Treasure")
        public boolean vampirismTreasure = false;

        @Config.Comment("Whether or not is this enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Versatility Treasure")
        public boolean versatilityTreasure = false;

        @Config.Comment("Whether or not is Vitality enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Vitality Treasure")
        public boolean vitalityTreasure = false;

        @Config.Comment("Whether or not is Weightless enchantment a treasure enchantment (exclusive to loot and trading).")
        @Config.Name("is Weightless Treasure")
        public boolean weightlessTreasure = false;
        
        @Config.Comment("Multiplier of arrow velocity for the Range enchantment.")
        @Config.Name("Range Multiplier")
        @Config.RangeDouble(min = 1.0, max = 10.0)
        public double rangeVelocity = 1.5D;

        @Config.Comment("Prevent Tunneling enchantment from attempting to mine TileEntities.")
        @Config.Name("Tunneling Ignores TileEntities")
        public boolean preventTunnelingTileEntities = true;
    }
    
    public static class PotionConfig {
    	@Config.Comment("Follow range reduction for non-player entities caused by blindness (in %)")
        @Config.Name("Blindness Strength")
        @Config.RangeDouble(min = 0.0, max = 100.0)
        public double blindnessStrength = 80.0;

        @Config.Comment("List of IDs of mobs not to be affected with the follow range reduction.")
        @Config.Name("Blindness Blacklist")
        public String[] blindnessBlacklist = new String[]{""};
        
        @Config.Comment("Number of hits a potion lasts for after a single dose.")
        @Config.Name("Potion Hits Amount")
        @Config.RangeInt(min = 1, max = 256)
        public int potionHits = 64;

        @Config.Comment("Maximum number of hits a potion can last for after repeated doses.")
        @Config.Name("Maximum Potion Hits")
        @Config.RangeInt(min = 1, max = 512)
        public int maximumPotionHits = 64;

        @Config.Comment("The value potion duration is divided by when the potion is applied onto a weapon.")
        @Config.Name("Potion Timer Divider")
        @Config.RangeInt(min = 1, max = 256)
        public int potionDivisor = 8;

        @Config.Comment("List of item classes that can have potions applied onto them.")
        @Config.Name("Potion-Applying Weapon Whitelist")
        public String[] paWeaponWhitelist = new String[]{"net.minecraft.item.ItemSword", "com.mujmajnkraft.bettersurvival.items.ItemCustomWeapon"};

        @Config.Comment("List of IDs of potions that cannot be applied onto a weapon.")
        @Config.Name("Potion-Applying Potion Blacklist")
        public String[] paPotionBlacklist = new String[]{""};
        
        private List<Class<?>> weaponWhitelist = null;
        
        public boolean isClassInstanceofWhitelistedWeapon(Class<?> clazz) {
            return ForgeConfigHandler.potions.getWeaponWhitelistClasses().stream().anyMatch(w -> w.isAssignableFrom(clazz));
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
    
    public static class MaterialConfig {
    	@Config.Comment("Allows you to craft weapons from materials commonly present in mods.")
	    @Config.Name("Modded Materials Support")
	    @Config.RequiresMcRestart
	    public boolean moddedMaterials = true;
    	
    	//Material stats
        @Config.Comment("Set stats of copper material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Copper Stats")
        @Config.RequiresMcRestart
        public Double[] copperStats = new Double[]{1d, 160d, 5.0d, 1.0d, 5d};
        
        @Config.Comment("Set stats of bronze material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Brone Stats")
        @Config.RequiresMcRestart
        public Double[] bronzeStats = new Double[]{2d, 200d, 6.0d, 1.8d, 14d};
        
        @Config.Comment("Set stats of invar material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Invar Stats")
        @Config.RequiresMcRestart
        public Double[] invarStats = new Double[]{2d, 250d, 6.5d, 2.1d, 10d};

        @Config.Comment("Set stats of silver material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Silver Stats")
        @Config.RequiresMcRestart
        public Double[] silverStats = new Double[]{0d, 32d, 12.0d, 0.5d, 22d};

        @Config.Comment("Set stats of electrum material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Electrum Stats")
        @Config.RequiresMcRestart
        public Double[] electrumStats = new Double[]{0d, 32d, 15.0d, 0.6d, 35d};

        @Config.Comment("Set stats of aluminium material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Aluminium Stats")
        @Config.RequiresMcRestart
        public Double[] aluminiumStats = new Double[]{2d, 220d, 12.0d, 1.8d, 14d};

        @Config.Comment("Set stats of steel material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Steel Stats")
        @Config.RequiresMcRestart
        public Double[] steelStats = new Double[]{2d, 350d, 6.5d, 2.5d, 14d};

        @Config.Comment("Set stats of signalum material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Signalum Stats")
        @Config.RequiresMcRestart
        public Double[] signalumStats = new Double[]{2d, 500d, 10.0d, 2.0d, 14d};

        @Config.Comment("Set stats of limium material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Lumium Stats")
        @Config.RequiresMcRestart
        public Double[] lumiumStats = new Double[]{2d, 600d, 12.0d, 2.5d, 14d};

        @Config.Comment("Set stats of enderium material (harvest level, durability, efficiency, damage, enchantability)")
        @Config.Name("Enderium Stats")
        @Config.RequiresMcRestart
        public Double[] enderiumStats = new Double[]{3d, 1000d, 10.0d, 4.0d, 20d};
    }    

    public static class WeaponConfig {
    	
    	final String damageModifierDesc = "Set damage factor for the weapon (compared to a sword of the same material).";
    	final String speedModifierDesc = "Set attack delay factor for the weapon (compared to a sword)";
        
        //Weapon stats
    	@Config.Comment("Extra attack reach given to spears.")
        @Config.Name("Spear Reach Bonus")
    	@Config.RequiresMcRestart
        public float spearReachBonus = 2.0F;
    	
        @Config.Comment("Base chance that a  full-strength hammer attack stuns the target.")
        @Config.Name("Stun Base Chance")
        public float stunBaseChance = 0.1F;

        @Config.Comment("Increased stun chance per level of the Bash enchantment")
        @Config.Name("Bash Stun Modifier")
        public float bashModifier = 0.05F;

        @Config.Comment("Base chance that a  full-strength battle axe attack causes the target to drop their weapon.")
        @Config.Name("Disarm Base Chance")
        public float disarmBaseChance = 0.1F;

        @Config.Comment("Disarm chance increase per level of the Disarming enchantment")
        @Config.Name("Disarming chance Modifier")
        public float disarmModifier = 0.05F;
        
        @Config.Comment(damageModifierDesc)
        @Config.Name("Battle Axe Damage Factor")
        @Config.RequiresMcRestart
        public float battleAxeDmgMod = 1.6f;

        @Config.Comment(damageModifierDesc)
        @Config.Name("Nunchaku Damage Factor")
        @Config.RequiresMcRestart
        public float nunchakuDmgMod = 0.5F;

        @Config.Comment(damageModifierDesc)
        @Config.Name("Hammer Damage Factor")
        @Config.RequiresMcRestart
        public float hammerDmgMod = 1.2F;

        @Config.Comment(damageModifierDesc)
        @Config.Name("Dagger Damage Factor")
        @Config.RequiresMcRestart
        public float daggerDmgMod = 0.7F;

        @Config.Comment(damageModifierDesc)
        @Config.Name("Spear Damage Factor")
        @Config.RequiresMcRestart
        public float spearDmgMod = 0.75F;

        @Config.Comment(damageModifierDesc)
        @Config.Name("Crossbow Damage Factor")
        public float crossbowDmgMod = 2.0F;
        
        @Config.Comment(speedModifierDesc)
        @Config.Name("Battle Axe Attack Delay Factor")
        @Config.RequiresMcRestart
        public float battleAxeSpd = 1.25F;

        @Config.Comment(speedModifierDesc)
        @Config.Name("Nunchaku Attack Delay Factor")
        @Config.RequiresMcRestart
        public float nunchakuSpd = 0.3F;

        @Config.Comment(speedModifierDesc)
        @Config.Name("Hammer Attack Delay Factor")
        @Config.RequiresMcRestart
        public float hammerSpd = 1.35F;

        @Config.Comment(speedModifierDesc)
        @Config.Name("Dagger Attack Delay Factor")
        @Config.RequiresMcRestart
        public float daggerSpd = 0.8F;

        @Config.Comment(speedModifierDesc)
        @Config.Name("Spear Attack Delay Factor")
        @Config.RequiresMcRestart
        public float spearSpd = 1.0F;

        @Config.Comment("The time it takes to load the crossbow (in ticks)")
        @Config.Name("Crossbow Reloading Time")
        public int crossbowSpd = 40;

        @Config.Comment("When set to false, disable Vanilla shield recipe, forcing player to use shields added by this mod.")
        @Config.Name("Allow Vanilla Shields")
        @Config.RequiresMcRestart
        public boolean allowVanillaShields = true;
    }

    public static class ClientConfig {
        @Config.Comment("Prevent custom shields from messing up your FoV. This will cause problems with other mods that change FoV.")
        @Config.Name("Custom Shield FoV")
        public boolean fovShields = true;

        @Config.Comment("Prevent FoV changes completely.")
        @Config.Name("Static FoV")
        public boolean staticFOV = false;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler{
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Reference.MOD_ID)) {
                ForgeConfigHandler.potions.refreshCache();
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
