# AACMD

AACMD (Another Armor Custom Model Data) is a mod which adds CustomModelData support
 for vanilla-like armor items.

This mod is inspired by [ArmorCustomModelData](https://github.com/cimdy/ArmorCustomModelData)
 and made as a technical solution of my friend's creation.

This branch is for Minecraft 1.20.1 .

## What does it do?

Words above should be clear.

Technically, "vanilla-like armor items" means the armor items which are implemented on top of
 vanilla "ArmorItem" class.

So armors which have custom renderers will **NOT** be supported by this mod because
 I don't know how they retrieve armor textures, and will they use vanilla armor models
 or their own geo models.

As far as I know, these armors are commonly registered through Fabric Render API
 with the implementation of Fabric `ArmorRenderer` interface, or something like it on
 other platforms.

## Why make it?

I know modders could register their own armors through Mod APIs, so this mod is not for them.

This mod is for the developers who just want to add many custom armor textures in a more vanilla way
 to their bigger project which could accept mods.

## How to use?

Just install this mod to your "mods" folder, then start your game.

Now you can use `CustomModelData` NBT on items.

However, this NBT will just make the "missing texture" rendered if you
 don't have a resource pack associated with the custom model data value
 you uses.

The texture path (in resource location format) just like vanilla armor texture path,
 but there are some changes:
- Namespace changed to `aacmd`, the namespace of this mod.
- CustomModelData value appended after the armor material name

For example, `assets/aacmd/textures/models/armor/leather_1_layer_X.png`
 is the texture for leather armor items with CustomModelData valued 1, layer X.

If you want to ignore the material name, use `IgnoreMaterial:1b` NBT on the item.

For example, `assets/aacmd/textures/models/armor/1_layer_X.png` is the texture
 for armor items with CustomModelData valued 1, layer X and ignored the material name.

## License

Copyright (C) 2025 SNWCreations, Licensed under GNU AGPL v3 License.
