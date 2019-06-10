package ru.fratask.mc.plugin.pm;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import ru.fratask.mc.plugin.pm.executors.PmCommandExecutor;
import ru.fratask.mc.plugin.pm.executors.ReplyCommandExecutor;

@Plugin(id = "chat-manager")
public class ChatManagerPlugin{

    private static ChatManagerPlugin instance;

    @Inject
    private Logger logger;


    @Listener
    public void onServerStart(GameInitializationEvent event){
        instance = this;
        logger.info("Chat-Manager plugin has started");


        Sponge.getCommandManager().register(instance, getPrivateMessageCommand(), "private", "pm");
        Sponge.getCommandManager().register(instance, getReplyPrivateMessageCommand(), "reply", "r");

    }

    private CommandSpec getPrivateMessageCommand(){
        return CommandSpec.builder()
                .description(Text.of("<player> <message> | /pm <player> <message> | Send private message to a player"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new PmCommandExecutor())
                .build();
    }
    private CommandSpec getReplyPrivateMessageCommand(){
        return CommandSpec.builder()
                .description(Text.of("<message> | /r <message> | Reply message to a player"))
                .arguments(

                        GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new ReplyCommandExecutor())
                .build();
    }

    public Logger getLogger() {
        return logger;
    }

    public static ChatManagerPlugin getInstance() {
        if (instance == null)
            instance = new ChatManagerPlugin();
        return instance;
    }
}
