package moe.quill.stratumnpcs.Commands.AddNpcKey;

import moe.quill.stratumnpcs.Commands.NpcTabs;
import moe.quill.stratumnpcs.NpcManagement.NpcKeys;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddNpcKeyTabs extends NpcTabs {

    public AddNpcKeyTabs(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return getTabsMatchingName(args[0]);
        } else if (args.length == 2) {
            return new ArrayList<>(NpcKeys.integrationKeys.keySet());
        }
        return null;
    }
}
