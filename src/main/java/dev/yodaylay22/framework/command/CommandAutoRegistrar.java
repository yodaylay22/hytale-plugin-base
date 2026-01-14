package dev.yodaylay22.framework.command;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Set;

public class CommandAutoRegistrar {

    public static void register(JavaPlugin plugin, Set<Class<?>> classes) {

        for (Class<?> clazz : classes) {

            if (!clazz.isAnnotationPresent(CommandDef.class)) {
                continue;
            }

            if (!BaseCommand.class.isAssignableFrom(clazz)) {
                throw new RuntimeException(
                        clazz.getName() + " has @CommandDef but does not extend BaseCommand"
                );
            }

            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);

                BaseCommand command =
                        (BaseCommand) constructor.newInstance();

                plugin.getCommandRegistry().registerCommand(command);

            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to register command: " + clazz.getName(), e
                );
            }
        }
    }
}