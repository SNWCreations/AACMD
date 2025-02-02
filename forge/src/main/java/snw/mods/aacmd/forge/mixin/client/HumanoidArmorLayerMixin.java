package snw.mods.aacmd.forge.mixin.client;

import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static snw.mods.aacmd.Core.*;

@Mixin(HumanoidArmorLayer.class)
@SuppressWarnings("UnstableApiUsage")
public abstract class HumanoidArmorLayerMixin {

    @Shadow protected abstract boolean usesInnerModel(EquipmentSlot arg);

    // Forge-added method does not have mapping
    @Redirect(method = "getArmorResource", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;getArmorTexture(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;Ljava/lang/String;Lnet/minecraft/world/entity/EquipmentSlot;Ljava/lang/String;)Ljava/lang/String;"), remap = false)
    private String onGetArmorTexture(Entity entity, ItemStack armor, String _default, EquipmentSlot slot, String type) {
        int cmd = readCustomModelData(armor);
        if (cmd != 0) {
            return buildTexturePath(armor, cmd, usesInnerModel(slot), type);
        }
        // Use super method if we can't do anything
        return ForgeHooksClient.getArmorTexture(entity, armor, _default, slot, type);
    }
}
