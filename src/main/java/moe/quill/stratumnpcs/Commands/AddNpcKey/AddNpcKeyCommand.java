package moe.quill.stratumnpcs.Commands.AddNpcKey;

import moe.quill.stratumnpcs.Commands.NpcCommand;
import moe.quill.stratumnpcs.NpcManagement.NpcKeys;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AddNpcKeyCommand extends NpcCommand {

    public AddNpcKeyCommand(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return true;
        final var key = NpcKeys.integrationKeys.get(args[1]);

        if (!npcManager.hasNpc(args[0])) {
            sender.sendMessage(String.format("There is no npc with the name '%s'!", args[0]));
        }
        //If the key is bad, return
        if (key == null) {
            sender.sendMessage("The key you provided does not exist!");
            return true;
        }

        final var success = npcManager.addKeyToNpc(args[0], key);
        sender.sendMessage(String.format("%s in adding the key %s to %s", ((success) ? "Succeeded" : "Failed"), key, args[0]));
        return true;
    }
}
