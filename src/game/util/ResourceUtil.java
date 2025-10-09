package game.util;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Resource helper that prefers loading from the source assets directory (src/game/assets)
 * while still falling back to the classpath if not found.
 *
 * Usage stays the same as before:
 *   image("/assets/images/foo.png")
 *   clip("/assets/sfx/bar.mp3")
 *
 * During development it will read directly from src/game/assets, so you can tweak files
 * without rebuilding. In a packaged JAR it will automatically fall back to classpath.
 */
public final class ResourceUtil {
    private static final String SRC_ASSETS_ROOT = "src/game/assets";
    private static final String ASSETS_PREFIX = "assets/";
    private ResourceUtil() {}

    /** Resolve a logical classpath-like path (e.g. "/assets/…") to a URL string. */
    public static String url(String classpathPath) {
        if (classpathPath == null) {
            throw new IllegalArgumentException("classpathPath is null");
        }
        // Normalize leading slash
        String p = classpathPath.startsWith("/") ? classpathPath.substring(1) : classpathPath;

        // Prefer loading from the source assets directory if the path starts with "assets/"
        if (p.startsWith(ASSETS_PREFIX)) {
            String rel = p.substring(ASSETS_PREFIX.length());
            Path fsPath = Paths.get(SRC_ASSETS_ROOT).resolve(rel).normalize();
            if (Files.exists(fsPath)) {
                return fsPath.toAbsolutePath().toUri().toString();
            }
        }

        // Fallback to classpath resource resolution
        URL u = ResourceUtil.class.getClassLoader().getResource(p);
        if (u == null) {
            // try with leading slash style
            u = ResourceUtil.class.getResource(classpathPath);
        }
        return Objects.requireNonNull(u, () ->
                "Resource not found: " + classpathPath + " (also looked under " + SRC_ASSETS_ROOT + ")").toString();
    }

    public static Image image(String classpathPath) {
        return new Image(url(classpathPath));
    }

    public static Image image(String classpathPath, double width, double height,
                              boolean preserveRatio, boolean smooth, boolean backgroundLoading) {
        return new Image(url(classpathPath), width, height, preserveRatio, smooth, backgroundLoading);
    }

    public static AudioClip clip(String classpathPath) {
        return new AudioClip(url(classpathPath));
    }
}
