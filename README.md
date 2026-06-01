# Village Micromanage — Forge (1.20.1)
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

## How To Use It
### Assignment mode
Right click a villager while holding the clipboard item to go into "assignment mode"  

<img width="1920" height="1080" alt="2026-06-01_13 28 22" src="https://github.com/user-attachments/assets/addaedef-2f2b-4eb3-9736-6977ec0dd6fd" />

### In assignment mode
- Sneak right click to exit assignment mode
- Right click a bed to assign bed POI, any other block will be registered as worksite POI 

<img width="1920" height="1080" alt="2026-06-01_13 28 44" src="https://github.com/user-attachments/assets/66028feb-6e1f-4c9e-9ee5-49dfc573c002" />

### Remove assigned POIs
- Sneak right click a villager to remove all previously assigned POIs  

<img width="1920" height="1080" alt="2026-06-01_13 33 36" src="https://github.com/user-attachments/assets/41986deb-2baa-4e1a-8eb8-4036f8b79a65" />
