package ru.fratask.mc.plugin.pm.executors;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import ru.fratask.mc.plugin.pm.Service.PmService;

public class PmCommandExecutor implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args){
        Player player = args.<Player>getOne("player").get();
        String message = args.<String>getOne("message").get();
        PmService.getInstance().sendMessage((Player) src, player, message);
        return CommandResult.success();
    }
}
