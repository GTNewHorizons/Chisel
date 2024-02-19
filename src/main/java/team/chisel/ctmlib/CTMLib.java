package team.chisel.ctmlib;

import cpw.mods.fml.common.ModAPIManager;

public class CTMLib {

    public static final String VERSION = "GRADLETOKEN_VERSION";

    private static boolean chiselPresent, chiselInitialized;

    public static boolean chiselLoaded() {
        if (!chiselInitialized) {
            chiselInitialized = true;
            chiselPresent = ModAPIManager.INSTANCE.hasAPI("ChiselAPI");
        }
        return chiselPresent;
    }
}
