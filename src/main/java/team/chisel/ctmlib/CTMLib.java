package team.chisel.ctmlib;

import cpw.mods.fml.common.ModAPIManager;
import team.chisel.Tags;

public class CTMLib {

    public static final String VERSION = Tags.VERSION;

    private static boolean chiselPresent, chiselInitialized;

    public static boolean chiselLoaded() {
        if (!chiselInitialized) {
            chiselInitialized = true;
            chiselPresent = ModAPIManager.INSTANCE.hasAPI("ChiselAPI");
        }
        return chiselPresent;
    }
}
