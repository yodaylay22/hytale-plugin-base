package dev.yodaylay22.framework.scan;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    public static Set<Class<?>> scan(ClassLoader classLoader, String basePackage) {

        String path = basePackage.replace('.', '/');
        Set<Class<?>> classes = new HashSet<>();

        try {
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {

                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();

                // 🔹 JAR (produção)
                if ("jar".equals(protocol)) {

                    String jarPath = resource.getPath()
                            .substring(5, resource.getPath().indexOf("!"));

                    jarPath = URLDecoder.decode(
                            jarPath,
                            StandardCharsets.UTF_8
                    );

                    try (JarFile jarFile = new JarFile(jarPath)) {

                        Enumeration<JarEntry> entries = jarFile.entries();

                        while (entries.hasMoreElements()) {

                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();

                            if (!name.endsWith(".class")) continue;
                            if (!name.startsWith(path)) continue;

                            String className = name
                                    .replace('/', '.')
                                    .replace(".class", "");

                            classes.add(Class.forName(className, false, classLoader));
                        }
                    }
                }

                // 🔹 FILE (dev)
                if ("file".equals(protocol)) {

                    File directory = new File(resource.toURI());

                    File[] files = directory.listFiles();
                    if (files == null) continue;

                    for (File file : files) {

                        if (!file.getName().endsWith(".class")) continue;

                        String className =
                                basePackage + "." +
                                        file.getName().replace(".class", "");

                        classes.add(Class.forName(className, false, classLoader));
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to scan package: " + basePackage, e
            );
        }

        return classes;
    }
}
