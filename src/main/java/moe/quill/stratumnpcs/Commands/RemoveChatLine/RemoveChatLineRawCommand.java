package moe.quill.stratumnpcs.Commands.RemoveChatLine;

import moe.quill.stratumnpcs.Commands.NpcCommand;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoveChatLineRawCommand extends NpcCommand {
    public RemoveChatLineRawCommand(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!npcManager.hasNpc(args[0])) {
            sender.sendMessage("This npc does not exist!");
            return true;
        }

        //Get the NPC
        final var index = Integer.parseInt(args[1]); //get the line index to remove
        final var lines = npcManager.getChatLines(args[0]); //get all of the npcs chat lines

        //If the index is out of bounds for the lines, exit the command
        if (index < 0 || index > lines.size() - 1) return true;
        final var line = lines.get(index);
        final var success = npcManager.removeChatLine(args[0], index);

        if (success) {
            sender.sendMessage(
                    Component.text("Removed chat line")
                            .append(Component.space())
                            .append(Component.text("\""))
                            .append(line)
                            .append(Component.text("\"!"))
                            .color(TextColor.color(0xBD92B))

            );
            return true;
        }

        sender.sendMessage("Failed to remove chat line!");

        return true;
    }
}
