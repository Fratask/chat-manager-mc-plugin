package ru.fratask.mc.plugin.pm.Service;

import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.pm.ChatManagerPlugin;

import java.util.HashMap;
import java.util.Map;


public class PmService implements IPmService {

    private Map<Player, Player> privateMessagesTraceHashMap = new HashMap<>();

    Logger logger = ChatManagerPlugin.getInstance().getLogger();

    private static PmService instance;

    @Override
    public void sendMessage(Player from, Player to, String message) {
        if (from.getName().equals(to.getName())){
            from.sendMessage(Text.of(TextColors.RED, "You must choose player!"));
        } else {
            privateMessagesTraceHashMap.put(to, from);
            from.sendMessage(Text.of(TextColors.DARK_RED, from.getName() + ": ", TextColors.DARK_PURPLE, message));
            to.sendMessage(Text.of(TextColors.DARK_RED, from.getName() + ": ", TextColors.DARK_PURPLE, message));
            logger.info(from.getName() + " -> " + to.getName() + ": " + message);
        }
    }

    @Override
    public void reply(Player from, String message) {
        if (!privateMessagesTraceHashMap.containsKey(from)) {
            from.sendMessage(Text.of(TextColors.RED, "You don't have any private messages for reply!"));
        } else {
            Player player = privateMessagesTraceHashMap.get(from);
            privateMessagesTraceHashMap.put(player, from);
            from.sendMessage(Text.of(TextColors.DARK_RED, from.getName() + ": ", TextColors.DARK_PURPLE, message));
            player.sendMessage(Text.of(TextColors.DARK_RED, from.getName() + ": ", TextColors.DARK_PURPLE, message));
            logger.info(from.getName() + " -> " + player.getName() + ": " + message);
        }
    }

    public static PmService getInstance() {
        if (instance == null){
            instance = new PmService();
        }
        return instance;
    }
}
