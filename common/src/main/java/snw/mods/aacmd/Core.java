package snw.mods.aacmd;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.requireNonNull;

// Core methods used to implement the feature of this mod.
public final class Core {
    public static final String TAG_IGNORE_MATERIAL = "IgnoreMaterial";
    private static final String GET_TAG_FATAL_MSG = "ItemStack#getTag returned null when ItemStack#hasTag returns true?";

    public static boolean isIgnoreMaterial(ItemStack itemStack) {
        return itemStack.hasTag() &&
                requireNonNull(itemStack.getTag(), GET_TAG_FATAL_MSG)
                        .getBoolean(TAG_IGNORE_MATERIAL);
    }

    public static int readCustomModelData(ItemStack itemStack) {
        final String tag = "CustomModelData";
        return itemStack.hasTag() ?
                requireNonNull(itemStack.getTag(), GET_TAG_FATAL_MSG)
                        .getInt(tag)
                : 0;
    }

    public static String buildTexturePath(ItemStack itemStack, int cmdInt, boolean useInnerModel, @Nullable String type) {
        String cmdString = String.valueOf(cmdInt);
        String pathHead;
        if (isIgnoreMaterial(itemStack) || !(itemStack.getItem() instanceof ArmorItem asArmorItem)) {
            pathHead = cmdString;
        } else {
            String materialName = asArmorItem.getMaterial().getName();
            pathHead = materialName + "_" + cmdString;
        }
        return "aacmd:textures/models/armor/" + pathHead + "_layer_" + (useInnerModel ? 2 : 1) + (type == null ? "" : "_" + type) + ".png";
    }
}
