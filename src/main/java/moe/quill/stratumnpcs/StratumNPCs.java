package moe.quill.stratumnpcs;

import moe.quill.stratumnpcs.Commands.AddChatLine.AddChatLineCommand;
import moe.quill.stratumnpcs.Commands.AddChatLine.AddChatLineTabs;
import moe.quill.stratumnpcs.Commands.AddNpcKey.AddNpcKeyCommand;
import moe.quill.stratumnpcs.Commands.AddNpcKey.AddNpcKeyTabs;
import moe.quill.stratumnpcs.Commands.ChangeVIllagerProfession.ChangeVillagerProfessionCommand;
import moe.quill.stratumnpcs.Commands.ChangeVIllagerProfession.ChangeVillagerProfessionTabs;
import moe.quill.stratumnpcs.Commands.ChangeVillagerBiome.ChangeVillagerBiomeTypeCommand;
import moe.quill.stratumnpcs.Commands.ChangeVillagerBiome.ChangeVillagerBiomeTypeTabs;
import moe.quill.stratumnpcs.Commands.RemoveChatLine.RemoveChatLineCommand;
import moe.quill.stratumnpcs.Commands.RemoveChatLine.RemoveChatLineRawCommand;
import moe.quill.stratumnpcs.Commands.RemoveNpc.RemoveNpcCommand;
import moe.quill.stratumnpcs.Commands.RemoveNpc.RemoveNpcTabs;
import moe.quill.stratumnpcs.Commands.SpawnNpc.SpawnNpcCommand;
import moe.quill.stratumnpcs.Commands.SpawnNpc.SpawnNpcTabs;
import moe.quill.stratumnpcs.Events.NpcImmortalityEvent;
import moe.quill.stratumnpcs.NpcManagement.IntegrationsManager;
import moe.quill.stratumnpcs.NpcManagement.NpcKeys;
import moe.quill.stratumnpcs.NpcManagement.NpcManager;
import moe.quill.stratumnpcs.Serialization.StratumSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class StratumNPCs extends JavaPlugin {

    @Override
    public void onEnable() {
        //Init Serialization and Keys
        StratumSerialization.init();
        NpcKeys.init(this);
        new IntegrationsManager(this); //setup integrations
        final var npcManager = new NpcManager(this); //create the npcmanager

        /**
         * EVENTS
         */
        final var pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new NpcImmortalityEvent(), this); //Make NPCs unable to take damage

        /**
         * COMMANDS
         */
        //Register NPC Commands
        final var spawnNpcCommand = getCommand("spawnnpc");
        if (spawnNpcCommand != null) {
            spawnNpcCommand.setExecutor(new SpawnNpcCommand(npcManager));
            spawnNpcCommand.setTabCompleter(new SpawnNpcTabs());
        }
        final var removeNpcCommand = getCommand("removenpc");
        if (removeNpcCommand != null) {
            removeNpcCommand.setExecutor(new RemoveNpcCommand(npcManager));
            removeNpcCommand.setTabCompleter(new RemoveNpcTabs(npcManager));
        }

        final var addNpcKey = getCommand("addnpckey");
        if (addNpcKey != null) {
            addNpcKey.setExecutor(new AddNpcKeyCommand(npcManager));
            addNpcKey.setTabCompleter(new AddNpcKeyTabs(npcManager));
        }

        final var changeProfession = getCommand("changeprofession");
        if (changeProfession != null) {
            changeProfession.setExecutor(new ChangeVillagerProfessionCommand(npcManager));
            changeProfession.setTabCompleter(new ChangeVillagerProfessionTabs(npcManager));
        }

        final var changeBiomeType = getCommand("changebiometype");
        if (changeBiomeType != null) {
            changeBiomeType.setExecutor(new ChangeVillagerBiomeTypeCommand(npcManager));
            changeBiomeType.setTabCompleter(new ChangeVillagerBiomeTypeTabs(npcManager));
        }

        final var addChatLine = getCommand("addchatline");
        if (addChatLine != null) {
            addChatLine.setExecutor(new AddChatLineCommand(npcManager));
            addChatLine.setTabCompleter(new AddChatLineTabs(npcManager));
        }

        final var removeChatLine = getCommand("removechatline");
        if (removeChatLine != null) {
            removeChatLine.setExecutor(new RemoveChatLineCommand(npcManager));
            removeChatLine.setTabCompleter(new RemoveNpcTabs(npcManager));
        }

        final var removeChatLineRaw = getCommand("removechatlineraw");
        if (removeChatLineRaw != null) {
            removeChatLineRaw.setExecutor(new RemoveChatLineRawCommand(npcManager));
        }
    }

    // Plugin startup logic
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
