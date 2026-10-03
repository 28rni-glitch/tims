# Cyberpunk Craft

A cyberpunk mod for Minecraft: neon lights, chrome, laser blasters and cyber armor.
Build your own glowing megacity at night.

- **Minecraft:** 26.3 (Java Edition)
- **Mod loader:** [Fabric](https://fabricmc.net/use/installer/) + [Fabric API](https://modrinth.com/mod/fabric-api)

## Installing

1. Install the **Fabric Loader** for Minecraft 26.3 with the [Fabric installer](https://fabricmc.net/use/installer/).
2. Download **Fabric API** for 26.3 and put it in your `.minecraft/mods` folder.
3. Get the mod jar:
   - Open the **Actions** tab of this repository, click the latest green **build** run,
     and download the **cyberpunkcraft-mod** artifact (it's a zip).
   - Unzip it and put `cyberpunkcraft-1.0.0.jar` in your `.minecraft/mods` folder
     (not the `-sources` jar, if there is one).
4. Start Minecraft with the **Fabric** profile. Everything is in the **Cyberpunk Craft** creative tab.

## What's in the mod

### Gear

| Item | What it does |
| --- | --- |
| **Neon Katana** | A diamond-tier sword that hits a bit harder. **Right-click to dash forward** (2 second cooldown, no fall damage). |
| **Plasma Blaster** | **Right-click to fire a laser beam** that deals 8 damage to the first mob it hits, up to 48 blocks away. Uses durability, repair it with Chrome Ingots. |
| **Cyber Visor** | Helmet that gives **Night Vision** while you wear it. |
| **Cyber Chestplate / Leggings / Boots** | Armor a little tougher than diamond. Wear the **full set** to also get **Speed** and **Jump Boost**. |
| **Synth-Cola** | Energy drink: **Speed II and Haste II** for 1 minute. You get the bottle back. |

### Blocks

| Block | Notes |
| --- | --- |
| **Neon Blocks** (cyan, magenta, pink, purple, lime, orange) | Glow as bright as glowstone. |
| **Block of Chrome** | Shiny storage block for Chrome Ingots. |
| **Chrome Plating** | Riveted metal panels for building. |
| **Circuit Block** | Animated circuit board with light pulses running along it. Glows faintly. |
| **Cyber Glass** | Dark tinted glass with a glowing cyan frame. |
| **Asphalt** | Road block. **You walk faster on it.** |
| **Hazard Block** | Yellow and black warning stripes. |

## Recipes

Shaped recipes are shown as a 3x3 crafting grid, where `.` means an empty slot.

`C` = Chrome Ingot, `N` = Neon Dust, `M` = Microchip

### Materials

- **Chrome Ingot:** Iron Ingot + Copper Ingot (shapeless)
- **Neon Dust (x2):** Glowstone Dust + Redstone (shapeless)
- **Block of Chrome:** 9 Chrome Ingots (put it back in the grid to get 9 ingots)
- **Microchip (x2):** `R` = Redstone, `G` = Gold Ingot
  ```
  . R .
  C G C
  . R .
  ```

### Blocks

- **Neon Block (x4):** `G` = Glass, `D` = a dye. The dye picks the color (cyan, magenta, pink, purple, lime or orange).
  ```
  G N G
  N D N
  G N G
  ```
- **Chrome Plating (x8):** 4 Chrome Ingots in a 2x2 square
- **Circuit Block (x8):** 8 Chrome Plating around 1 Microchip
- **Cyber Glass (x8):** 8 Glass around 1 Neon Dust
- **Asphalt (x2):** 2 Gravel + Coal or Charcoal (shapeless)
- **Hazard Block (x2):** 2 Chrome Plating + Yellow Dye + Black Dye (shapeless)

### Gear

- **Synth-Cola:** Glass Bottle + 2 Sugar + Neon Dust (shapeless)

| Neon Katana (`S` = Stick) | Plasma Blaster | Cyber Visor | Cyber Chestplate | Cyber Leggings | Cyber Boots |
| --- | --- | --- | --- | --- | --- |
| `. . C`<br>`N C .`<br>`S N .` | `N C C`<br>`. M C`<br>`. . C` | `C M C`<br>`N . N` | `C . C`<br>`C M C`<br>`C C C` | `C M C`<br>`C . C`<br>`C . C` | `N . N`<br>`C . C` |

## Building it yourself

You need Java 25. Then run:

```
./gradlew build
```

The mod jar ends up in `build/libs/`. Use `./gradlew runClient` to start Minecraft with the mod for testing.

Every push to GitHub builds the mod, runs the game tests, and takes in-game screenshots (in the **screenshots** artifact of each build run).
