package moe.quill.stratumnpcs.Commands.RemoveNpc;

import moe.quill.stratumnpcs.Commands.NpcCommand;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoveNpcCommand extends NpcCommand {

    public RemoveNpcCommand(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return true;
        npcManager.removeNpc(args[0]);
        return false;
    }
}
