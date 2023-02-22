package me.nicholxs.bombnotifier;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class BombBellModule {

    private static final String GUILD_CHAT_PREFIX = "/g";

    // Bomb bell pattern - supports avomod's nickname revealer, thats why its a mess
    private static final Pattern BOMB_BELL_PATTERN = Pattern.compile("\\[Bomb Bell\\] ((?:\\w+ )?\\w+)(?:\\((\\w+)\\))? has thrown a (.+) Bomb on WC(\\d+)");
    
    /**
     * Handle the bomb bell message and insert the clickable chat message.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        // Get the chat message text
        String messageText = event.getMessage().getUnformattedText();

        // Check if the message matches the bomb bell pattern
        Matcher matcher = BOMB_BELL_PATTERN.matcher(messageText);
        if (matcher.matches()) {
            // Extract the bomb type and world from the message
            String playerName = matcher.group(2) != null ? matcher.group(2) : matcher.group(1);
            String bombType = matcher.group(3);
            String world = "WC" + matcher.group(4);

            // Construct the message to send to guild chat
            String formattedMessage = String.format("%s %s has thrown a %s Bomb on %s", GUILD_CHAT_PREFIX, playerName, bombType, world);

            // Insert the clickable chat message below the bomb bell message
            ITextComponent sendToGuildChatButton = new TextComponentString("[SEND TO GUILD]")
                    .setStyle(new Style()
                            .setColor(TextFormatting.AQUA)
                            .setBold(true)
                            .setUnderlined(true)
                            .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to send to guild chat")))
                            .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, formattedMessage)));

            ITextComponent emptyLine = new TextComponentString("\n\n");
            event.setMessage(event.getMessage().appendSibling(emptyLine).appendSibling(sendToGuildChatButton).appendSibling(emptyLine));
        }
    }


}
