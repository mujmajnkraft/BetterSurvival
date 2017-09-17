package com.mujmajnkraft.bettersurvival;

public class Reference {
	
	public static final String MOD_ID = "mujmajnkraftsbettersurvival";
	public static final String MOD_NAME = "Better Survival";
	public static final String MOD_VERSION = "1.3.2";
	public static final String MC_VERSIONS = "[1.11, 1.12)";
	
	public static final String CLIENT_PROXY_CLASS = "com.mujmajnkraft.bettersurvival.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.mujmajnkraft.bettersurvival.proxy.ServerProxy";
	
	public static enum ModItems
	{
		DEBUG("debug","ItemDebug");
		
		private String unlocallizedName;
		private String registryName;
		
		
		ModItems(String unlocallizedName, String registryName) {
			this.unlocallizedName = unlocallizedName;
			this.registryName = registryName;
		}
		
		public String getRegistryName() {
			return registryName;
		}
		
		public String getUnlocallizedName() {
			return unlocallizedName;
		}
	}
	public static enum ModBlocks
	{
		SILVERORE("silverore","BlockSilverOre");
		private String unlocallizedName;
		private String registryName;
		
		ModBlocks(String unlocallizedName, String registryName) {
			this.unlocallizedName = unlocallizedName;
			this.registryName = registryName;
		}
		
		public String getRegistryName() {
			return registryName;
		}
		
		public String getUnlocallizedName() {
			return unlocallizedName;
		}
	}
}
