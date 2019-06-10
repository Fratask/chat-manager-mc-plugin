package ru.fratask.mc.plugin.pm.executors;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import ru.fratask.mc.plugin.pm.Service.PmService;

public class ReplyCommandExecutor implements CommandExecutor {

    PmService pmService;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        String message = args.<String>getOne("message").get();
        pmService.getInstance().reply((Player) src, message);
        return CommandResult.success();
    }
}
