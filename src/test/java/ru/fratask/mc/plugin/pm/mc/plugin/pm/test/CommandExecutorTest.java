package ru.fratask.mc.plugin.pm.mc.plugin.pm.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import ru.fratask.mc.plugin.pm.ChatManagerPlugin;
import ru.fratask.mc.plugin.pm.executors.PmCommandExecutor;
import ru.fratask.mc.plugin.pm.executors.ReplyCommandExecutor;

import java.lang.reflect.Field;

public class CommandExecutorTest {

    @Test
    public void pmCommandExecutorTest() throws NoSuchFieldException, IllegalAccessException {
        ChatManagerPlugin chatManagerPlugin = ChatManagerPlugin.getInstance();
        Field loggerField = chatManagerPlugin.getClass().getDeclaredField("logger");
        loggerField.setAccessible(true);
        loggerField.set(chatManagerPlugin, Mockito.mock(Logger.class));
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);
        PmCommandExecutor pmCommandExecutor = new PmCommandExecutor();
        CommandContext commandContext = new CommandContext();
        commandContext.putArg("player", player2);
        commandContext.putArg("message", "Some message");

        int counter = 0;

        Mockito.when(player1.getName()).thenReturn("Player1");
        Mockito.when(player2.getName()).thenReturn("Player2");

        pmCommandExecutor.execute(player1, commandContext);

        counter++;

        Mockito.verify(player1, Mockito.times(counter)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counter)).sendMessage((Text) Mockito.any());
    }

    @Test
    public void replyCommandExecutorTest() throws IllegalAccessException, NoSuchFieldException {
        ChatManagerPlugin chatManagerPlugin = ChatManagerPlugin.getInstance();
        Field loggerField = chatManagerPlugin.getClass().getDeclaredField("logger");
        loggerField.setAccessible(true);
        loggerField.set(chatManagerPlugin, Mockito.mock(Logger.class));
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);
        ReplyCommandExecutor replyCommandExecutor = new ReplyCommandExecutor();
        PmCommandExecutor pmCommandExecutor = new PmCommandExecutor();

        CommandContext commandContextForMessage = new CommandContext();
        commandContextForMessage.putArg("player", player2);
        commandContextForMessage.putArg("message", "Some reply message");

        CommandContext commandContextForReply = new CommandContext();
        commandContextForReply.putArg("message", "Some reply message");

        int counter = 0;

        Mockito.when(player1.getName()).thenReturn("Player1");
        Mockito.when(player2.getName()).thenReturn("Player2");

        pmCommandExecutor.execute(player1, commandContextForMessage);
        counter++;

        Mockito.verify(player1, Mockito.times(counter)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counter)).sendMessage((Text) Mockito.any());

        replyCommandExecutor.execute(player2, commandContextForReply);
        counter++;

        Mockito.verify(player1, Mockito.times(counter)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counter)).sendMessage((Text) Mockito.any());
    }
}
