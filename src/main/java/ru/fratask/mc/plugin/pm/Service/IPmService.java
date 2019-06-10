package ru.fratask.mc.plugin.pm.Service;

import org.spongepowered.api.entity.living.player.Player;

public interface IPmService {

    void sendMessage(Player from, Player to, String message);

    void reply(Player from, String message);
}
