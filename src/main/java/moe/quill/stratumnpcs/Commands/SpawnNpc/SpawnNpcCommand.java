package moe.quill.stratumnpcs.Commands.SpawnNpc;

import moe.quill.stratumnpcs.Commands.NpcCommand;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnNpcCommand extends NpcCommand {

    public SpawnNpcCommand(NpcManager npcManager) {
        super(npcManager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        final var player = ((Player) sender).getPlayer();
        if (player == null) return true;

        var location = player.getLocation();
        var name = Component.empty();
        var entityType = EntityType.VILLAGER;
        if (args.length >= 1) {
            name = Component.text(args[0]);
        }
        if (args.length >= 2) {
            try {
                entityType = EntityType.valueOf(args[1].toUpperCase());
            } catch (Exception ignored) {
            }

        }
        //TODO: NPC SPAWNING HERE
        final var success = npcManager.spawnNpc(player.getLocation(), entityType, name);

        player.sendMessage(
                success ?
                        Component.text("Spawned new NPC: ").append(name)
                        : Component.text("Failed to spawn NPC, does it already exist?")
        );
        return true;
    }
}
