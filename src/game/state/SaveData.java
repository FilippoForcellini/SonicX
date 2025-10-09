package game.state;


import static game.util.ResourceUtil.*;
import java.util.prefs.Preferences;

public class SaveData {
    private static final Preferences PREFS = Preferences.userRoot().node("sonic_menu");

    public static boolean isLevelCompleted(int i) {
        return PREFS.getBoolean("level" + i + "_completed", false);
    }
    public static void setLevelCompleted(int i, boolean v) {
        PREFS.putBoolean("level" + i + "_completed", v);
    }

    public static double getMaster() { return PREFS.getDouble("master", 0.8); }
    public static double getMusic()  { return PREFS.getDouble("music", 0.6); }
    public static double getSfx()    { return PREFS.getDouble("sfx", 0.8); }

    public static void setMaster(double v) { PREFS.putDouble("master", clamp01(v)); }
    public static void setMusic(double v)  { PREFS.putDouble("music",  clamp01(v)); }
    public static void setSfx(double v)    { PREFS.putDouble("sfx",    clamp01(v)); }

    private static double clamp01(double v) {
        if (v < 0) return 0; if (v > 1) return 1; return v;
    }
}
