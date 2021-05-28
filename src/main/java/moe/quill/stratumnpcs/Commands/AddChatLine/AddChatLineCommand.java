package moe.quill.stratumnpcs.Commands.AddChatLine;

import moe.quill.stratumnpcs.Commands.NpcCommand;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class AddChatLineCommand extends NpcCommand {
    public AddChatLineCommand(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return true;
        if (!npcManager.hasNpc(args[0])) {
            sender.sendMessage(String.format("No npc found with the name '%s", args[0]));
            return true;
        }
        final var message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        final var success = npcManager.addChatLine(args[0], message);
        sender.sendMessage(String.format("%s in adding the chat line %s to the npc %s", ((success) ? "Succeeded" : "Failed"), message, args[0]));
        return false;
    }
}
