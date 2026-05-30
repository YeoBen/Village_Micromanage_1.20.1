# Village Micromanager — Minecraft Forge Mod (1.20.1)
Minecraft Forge mod that gives players direct control over villager bed and worksites assignments, aka POIs (Point of Interest).

In vanilla Minecraft, 
1) Villagers claim the nearest available bed and worksite. Players have no easy way to assign villagers to specific POIs.
2) Villagers also tend to "forget" their beds for unknown reasons.  

This mod intends to solve both these problems.

## What This Mod Does
You can manually assign a bed and worksite to any specific villager using the "clipboard" item. The assigned coordinates will not be forgetten or reassigned.

## How It Works
The mod hooks into the villager creates a custom "persistent POI" NBT tag in villagers. When a player assigns a POI to a villager, 
the mod writes the assigned coordinates into this persistent tag.  
  
As of now, this value will only replace the real POI value when the villager is first loaded in, this will be changed in the future to make it more responsive.
