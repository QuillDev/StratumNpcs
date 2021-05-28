package moe.quill.stratumnpcs.Commands.SpawnNpc;

import moe.quill.stratumnpcs.Commands.NpcTabs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpawnNpcTabs implements TabCompleter {

    final ArrayList<String> livingEntityList = Arrays.stream(EntityType.values())
            .filter(entityType -> {
                final var entityClass = entityType.getEntityClass();
                if (entityClass == null) return false;
                return LivingEntity.class.isAssignableFrom(entityType.getEntityClass());
            })
            .map(entityType -> entityType.name().toUpperCase())
            .collect(Collectors.toCollection(ArrayList::new));

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("<name>");
        }
        if (args.length == 2) {
            return livingEntityList.stream()
                    .filter(item -> item.toLowerCase().contains(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
