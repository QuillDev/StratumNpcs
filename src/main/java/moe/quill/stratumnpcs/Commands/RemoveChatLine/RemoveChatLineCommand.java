package moe.quill.stratumnpcs.Commands.RemoveChatLine;

import moe.quill.stratumnpcs.Commands.NpcCommand;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemoveChatLineCommand extends NpcCommand {
    public RemoveChatLineCommand(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!npcManager.hasNpc(args[0])) {
            sender.sendMessage("This npc does not exist!");
            return true;
        }

        var lines = npcManager.getChatLines(args[0]);

        //If there are no lines to remove, tell the player
        if (lines.isEmpty()) {
            sender.sendMessage("This NPC has no chat lines to remove!");
            return true;
        }

        //Tell then player how to use the command
        sender.sendMessage(
                Component.text("Click the line you would like to remove!")
                        .color(TextColor.color(0xE04B46))
        );

        //Loop and print chat events for each line to remove it
        for (var i = 0; i < lines.size(); i++) {
            final var line = lines.get(i);
            final var lineToSend =
                    Component.text((i + 1) + ") ")
                            .append(line)
                            .clickEvent(
                                    ClickEvent.clickEvent(
                                            ClickEvent.Action.RUN_COMMAND,
                                            "/removechatlineraw " + args[0] + " " + i
                                    )
                            )
                            .color(TextColor.color(0x53C35E));
            sender.sendMessage(lineToSend);
        }
        return false;
    }
}
