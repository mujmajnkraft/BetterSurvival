package com.mujmajnkraft.bettersurvival.entities.siegeweapons;

import net.minecraft.world.World;

public enum EnumWeaponType {
	
	BALLISTA{
		@Override
		public EntitySiegeWeapon getEntityFromType(World worldIn) {
			
			return new EntityBallista(worldIn);
		}
		
		@Override
		public String getName() {
			
			return "Ballista";
		}
	},
	CANNON{
		@Override
		public EntitySiegeWeapon getEntityFromType(World worldIn) {
			
			return new EntityCannon(worldIn);
		}
		
		@Override
		public String getName() {
			
			return "Cannon";
		}
	},
	POTION_THROWER{
		@Override
		public EntitySiegeWeapon getEntityFromType(World worldIn) {
			
			return new EntityPotionThrower(worldIn);
		}
		
		@Override
		public String getName() {
			
			return "Potion_Thrower";
		}
	},
	ZEPPELIN{
		@Override
		public EntitySiegeWeapon getEntityFromType(World worldIn) {
			
			return new EntityZeppelin(worldIn);
		}
		
		@Override
		public String getName() {
			
			return "Zeppelin";
		}
	};
	
	private EnumWeaponType()
	{
		
	}
	
	public EntitySiegeWeapon getEntityFromType(World worldIn)
	{
		return null;
	}
	
	public String getName()
	{
		return "Missing";
	}
	
	public static EnumWeaponType getTypeFromName(String name)
	{
		switch(name)
		{
		case ("Ballista"): return BALLISTA;
		case ("Cannon"): return CANNON;
		case ("Potion_Thrower"):return POTION_THROWER;
		case ("Zeppelin"): return ZEPPELIN;
		}
		return null;
	}

}
