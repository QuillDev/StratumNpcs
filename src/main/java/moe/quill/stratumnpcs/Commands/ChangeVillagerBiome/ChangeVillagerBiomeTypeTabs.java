package moe.quill.stratumnpcs.Commands.ChangeVillagerBiome;

import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChangeVillagerBiomeTypeTabs implements TabCompleter {

    private final NpcManager npcManager;

    public ChangeVillagerBiomeTypeTabs(NpcManager npcManager) {
        this.npcManager = npcManager;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return npcManager.getNpcList().stream()
                    .filter(npc -> npc instanceof Villager)
                    .map(npc -> ((TextComponent) npc.customName()))
                    .filter(Objects::nonNull)
                    .map(TextComponent::content)
                    .collect(Collectors.toList());
        }
        if (args.length == 2) {
            return Arrays.stream(Villager.Type.values()).map(Enum::name).filter(name -> name.toLowerCase().contains(args[1].toLowerCase())).collect(Collectors.toList());
        }
        return null;
    }
}
