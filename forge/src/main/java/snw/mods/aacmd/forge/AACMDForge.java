package snw.mods.aacmd.forge;

import snw.mods.aacmd.AACMD;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AACMD.MOD_ID)
public final class AACMDForge {
    public AACMDForge(FMLJavaModLoadingContext loadingContext) {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(AACMD.MOD_ID, loadingContext.getModEventBus());

        // Run our common setup.
        AACMD.init();
    }
}
