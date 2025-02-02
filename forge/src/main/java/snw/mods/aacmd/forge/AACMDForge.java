package snw.mods.aacmd.forge;

import snw.mods.aacmd.AACMD;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AACMD.MOD_ID)
public final class AACMDForge {
    public AACMDForge(FMLJavaModLoadingContext loadingContext) {
        // Run our common setup.
        AACMD.init();
    }
}
