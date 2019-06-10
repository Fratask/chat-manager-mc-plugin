package ru.fratask.mc.plugin.pm.mc.plugin.pm.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.pm.ChatManagerPlugin;
import ru.fratask.mc.plugin.pm.Service.IPmService;
import ru.fratask.mc.plugin.pm.Service.PmService;

import java.lang.reflect.Field;

public class PrivateMessageTest  {


    @Test
    public void sendMessageAndReplyTest() throws NoSuchFieldException, IllegalAccessException {
        ChatManagerPlugin chatManagerPlugin = ChatManagerPlugin.getInstance();
        Field loggerField = chatManagerPlugin.getClass().getDeclaredField("logger");
        loggerField.setAccessible(true);
        loggerField.set(chatManagerPlugin, Mockito.mock(Logger.class));
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);

        Mockito.when(player1.isOnline()).thenReturn(true);
        Mockito.when(player2.isOnline()).thenReturn(true);

        Mockito.when(player1.getName()).thenReturn("Player1");
        Mockito.when(player2.getName()).thenReturn("Player2");

        IPmService pmService = new PmService();
        int counter = 0;

        pmService.sendMessage(player1, player2, "Some message");
        counter++;

        Mockito.verify(player1, Mockito.times(counter)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counter)).sendMessage((Text) Mockito.any());

        pmService.reply(player2, "Some message");
        counter++;

        Mockito.verify(player1, Mockito.times(counter)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counter)).sendMessage((Text) Mockito.any());
    }

    @Test
    public void sendMessageWithThreePlayersTest() throws NoSuchFieldException, IllegalAccessException {
        ChatManagerPlugin chatManagerPlugin = ChatManagerPlugin.getInstance();
        Field loggerField = chatManagerPlugin.getClass().getDeclaredField("logger");
        loggerField.setAccessible(true);
        loggerField.set(chatManagerPlugin, Mockito.mock(Logger.class));
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);
        Player player3 = Mockito.mock(Player.class);

        Mockito.when(player1.isOnline()).thenReturn(true);
        Mockito.when(player2.isOnline()).thenReturn(true);
        Mockito.when(player3.isOnline()).thenReturn(true);

        Mockito.when(player1.getName()).thenReturn("Player1");
        Mockito.when(player2.getName()).thenReturn("Player2");
        Mockito.when(player3.getName()).thenReturn("Player3");

        IPmService pmService = new PmService();
        int counterPlayer1 = 0;
        int counterPlayer2 = 0;
        int counterPlayer3 = 0;

        pmService.sendMessage(player1, player2, "Some message from Player1 to Player2");
        counterPlayer1++;
        counterPlayer2++;

        Mockito.verify(player1, Mockito.times(counterPlayer1)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counterPlayer2)).sendMessage((Text) Mockito.any());
        Mockito.verify(player3, Mockito.times(counterPlayer3)).sendMessage((Text) Mockito.any());

        pmService.reply(player2, "Some reply message from Player2 to Player1");
        counterPlayer1++;
        counterPlayer2++;

        Mockito.verify(player1, Mockito.times(counterPlayer1)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counterPlayer2)).sendMessage((Text) Mockito.any());
        Mockito.verify(player3, Mockito.times(counterPlayer3)).sendMessage((Text) Mockito.any());

        pmService.sendMessage(player3, player2, "Some message from Player3 to Player2");
        counterPlayer2++;
        counterPlayer3++;

        Mockito.verify(player1, Mockito.times(counterPlayer1)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counterPlayer2)).sendMessage((Text) Mockito.any());
        Mockito.verify(player3, Mockito.times(counterPlayer3)).sendMessage((Text) Mockito.any());

        pmService.reply(player2, "Some reply message from Player2 to Player3");
        counterPlayer2++;
        counterPlayer3++;

        Mockito.verify(player1, Mockito.times(counterPlayer1)).sendMessage((Text) Mockito.any());
        Mockito.verify(player2, Mockito.times(counterPlayer2)).sendMessage((Text) Mockito.any());
        Mockito.verify(player3, Mockito.times(counterPlayer3)).sendMessage((Text) Mockito.any());

    }

    @Test
    public void messageDontSendReplyTest() {
        Player player1 = Mockito.mock(Player.class);
        Mockito.when(player1.getName()).thenReturn("Player1");

        IPmService pmService = new PmService();
        int counter = 0;

        pmService.reply(player1, "Some message");
        counter++;

        Mockito.verify(player1, Mockito.times(counter)).sendMessage(Text.of(TextColors.RED, "You don't have any private messages for reply!"));
    }
}
