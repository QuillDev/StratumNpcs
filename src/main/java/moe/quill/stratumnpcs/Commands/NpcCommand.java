package moe.quill.stratumnpcs.Commands;

import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.CommandExecutor;

public abstract class NpcCommand implements CommandExecutor {

    protected final NpcManager npcManager;

    public NpcCommand(NpcManager npcManager) {
        this.npcManager = npcManager;
    }
}
