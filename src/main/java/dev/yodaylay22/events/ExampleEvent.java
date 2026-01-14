package dev.yodaylay22.events;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import dev.yodaylay22.framework.event.EventListener;
import dev.yodaylay22.framework.event.Listen;

@Listen(event = PlayerReadyEvent.class)
public class ExampleEvent implements EventListener<PlayerReadyEvent> {

    @Override
    public void handle(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(
                Message.raw("Bem-vindo " + player.getDisplayName())
        );
        //player.moveTo(player.getWorld().getWorldConfig().getSpawnProvider());
    }
}