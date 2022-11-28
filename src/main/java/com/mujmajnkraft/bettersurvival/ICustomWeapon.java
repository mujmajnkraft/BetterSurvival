package com.mujmajnkraft.bettersurvival;

//Retain ICustomWeapon so ReachFix doesn't crash indev
public interface ICustomWeapon {
    default float getReach() { return 0; }
}