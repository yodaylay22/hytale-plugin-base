package dev.yodaylay22.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import dev.yodaylay22.framework.command.BaseCommand;
import dev.yodaylay22.framework.command.CommandDef;

import javax.annotation.Nonnull;

@CommandDef(
        name = "example",
        description = "An example command"
)
public class ExampleCommand extends BaseCommand {

    public ExampleCommand() {
        super("example", "An example command");
    }

    @Override
    protected void executeSync(@Nonnull CommandContext context) {
        context.sendMessage(Message.raw("Hello from ExampleCommand!"));
    }
}