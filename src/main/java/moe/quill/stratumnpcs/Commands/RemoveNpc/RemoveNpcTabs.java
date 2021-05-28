package moe.quill.stratumnpcs.Commands.RemoveNpc;

import moe.quill.stratumnpcs.Commands.NpcTabs;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RemoveNpcTabs extends NpcTabs {

    public RemoveNpcTabs(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return getTabsMatchingName(args[0]);
        }
        return null;
    }
}
