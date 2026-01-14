package dev.yodaylay22.framework.command;

import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;

public abstract class BaseCommand extends CommandBase {

    protected BaseCommand(String name, String description) {
        super(name, description);
    }
}