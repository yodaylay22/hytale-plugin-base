package dev.yodaylay22;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.yodaylay22.framework.command.CommandAutoRegistrar;
import dev.yodaylay22.framework.event.EventAutoRegistrar;
import dev.yodaylay22.framework.scan.ClassScanner;

import javax.annotation.Nonnull;
import java.util.Set;

public class Main extends JavaPlugin {

    public Main(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {

        ClassLoader pluginClassLoader = this.getClass().getClassLoader();

        Set<Class<?>> classes =
                ClassScanner.scan(pluginClassLoader, "dev.yodaylay22");

        CommandAutoRegistrar.register(this, classes);
        EventAutoRegistrar.register(this, classes);
    }
}
