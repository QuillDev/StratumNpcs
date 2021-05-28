package moe.quill.stratumnpcs.Commands.AddChatLine;

import moe.quill.stratumnpcs.Commands.NpcTabs;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class AddChatLineTabs extends NpcTabs {
    public AddChatLineTabs(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return getTabsMatchingName(args[0]);
        }
        if (args.length > 1) {
            return Collections.singletonList("<message>");
        }
        return null;
    }
}
