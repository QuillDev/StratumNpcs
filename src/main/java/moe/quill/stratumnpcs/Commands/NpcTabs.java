package moe.quill.stratumnpcs.Commands;

import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import java.util.List;
import java.util.stream.Collectors;

public abstract class NpcTabs implements TabCompleter {

    protected final NpcManager npcManager;

    public NpcTabs(NpcManager npcManager) {
        this.npcManager = npcManager;
    }

    protected List<String> getTabsMatchingName(String query) {
        return npcManager.getNpcNames().stream().filter(name -> name.toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
    }
}
