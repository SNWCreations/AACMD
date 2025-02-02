package snw.mods.aacmd.fabric.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

import static snw.mods.aacmd.Core.buildTexturePath;
import static snw.mods.aacmd.Core.readCustomModelData;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    @Shadow @Final private static Map<String, ResourceLocation> ARMOR_LOCATION_CACHE;

    private HumanoidArmorLayerMixin(RenderLayerParent<T, M> unused) {
        super(unused);
        throw new IllegalAccessError("Should never be called");
    }

    // I hate these pieces of shit!
    // If I can redirect all calls to renderModel with captured local variables
    //  in one method, I would be able to optimize this.

    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/HumanoidModel;ZFFFLjava/lang/String;)V", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onRenderModel0(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci, ItemStack itemStack, ArmorItem armorItem, boolean bl, DyeableArmorItem dyeableArmorItem, int j, float f, float g, float h) {
        aacmd$handleRenderModel(poseStack, multiBufferSource, livingEntity, equipmentSlot, i, humanoidModel, ci, itemStack, bl, f, g, h, null);
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/HumanoidModel;ZFFFLjava/lang/String;)V", ordinal = 1), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onRenderModel1(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci, ItemStack itemStack, ArmorItem armorItem, boolean bl, DyeableArmorItem dyeableArmorItem, int j, float f, float g, float h) {
        aacmd$handleRenderModel(poseStack, multiBufferSource, livingEntity, equipmentSlot, i, humanoidModel, ci, itemStack, bl, f, g, h, "overlay");
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/HumanoidModel;ZFFFLjava/lang/String;)V", ordinal = 2), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onRenderModel2(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci, ItemStack itemStack, ArmorItem armorItem, boolean bl) {
        aacmd$handleRenderModel(poseStack, multiBufferSource, livingEntity, equipmentSlot, i, humanoidModel, ci, itemStack, bl, 1, 1, 1, null);
    }

    // End shit

    @Unique
    private void aacmd$handleRenderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci, ItemStack itemStack, boolean bl, float f, float g, float h, @Nullable String type) {
        int cmd = readCustomModelData(itemStack);
        if (cmd != 0) {
            aacmd$renderModel(poseStack, multiBufferSource, i, itemStack, humanoidModel, bl, f, g, h, type, cmd);
            ci.cancel();
        }
    }

    @Unique
    private ResourceLocation aacmd$getArmorLocation(ItemStack itemStack, int cmdInt, boolean bl, @Nullable String type) {
        String texturePath = buildTexturePath(itemStack, cmdInt, bl, type);
        return ARMOR_LOCATION_CACHE.computeIfAbsent(texturePath, ResourceLocation::new);
    }

    @Unique
    private void aacmd$renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ItemStack itemStack, A humanoidModel, boolean bl, float f, float g, float h, @Nullable String string, int cmdInt) {
        ResourceLocation armorLocation = aacmd$getArmorLocation(itemStack, cmdInt, bl, string);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.armorCutoutNoCull(armorLocation));
        humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, f, g, h, 1.0F);
    }
}
