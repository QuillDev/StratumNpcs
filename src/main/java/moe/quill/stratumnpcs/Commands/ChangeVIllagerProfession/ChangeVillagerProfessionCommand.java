package moe.quill.stratumnpcs.Commands.ChangeVIllagerProfession;

import moe.quill.stratumnpcs.Commands.NpcCommand;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ChangeVillagerProfessionCommand extends NpcCommand {

    private final ArrayList<String> professionNames;

    public ChangeVillagerProfessionCommand(NpcManager npcManager) {
        super(npcManager);
        this.professionNames = Arrays.stream(Villager.Profession.values()).map(Enum::name).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 2) return false;

        if (!npcManager.isVillager(args[0])) {
            sender.sendMessage("This npc either does not exist, or is not a villager!");
            return true;
        }

        if (!professionNames.contains(args[1].toUpperCase())) {
            sender.sendMessage("There is no profession matching the given string!");
            return true;
        }
        final var npc = (Villager) npcManager.getNpc(args[0]);
        npc.setProfession(Villager.Profession.valueOf(args[1]));
        return true;
    }
}
