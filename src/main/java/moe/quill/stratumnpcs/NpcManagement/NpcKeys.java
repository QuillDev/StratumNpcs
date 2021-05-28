package moe.quill.stratumnpcs.NpcManagement;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class NpcKeys {

    public static NamespacedKey isNpc;
    public static NamespacedKey npcNameComponent;
    public static NamespacedKey npcNameRaw;
    public static NamespacedKey canChat;
    public static NamespacedKey chatData;
    public static HashMap<String, NamespacedKey> integrationKeys = new HashMap<>();

    public static void init(Plugin plugin) {
        isNpc = new NamespacedKey(plugin, "is_npc");
        npcNameComponent = new NamespacedKey(plugin, "npc_name");
        npcNameRaw = new NamespacedKey(plugin, "npc_name_raw");
        canChat = new NamespacedKey(plugin, "npc_can_chat");
        chatData = new NamespacedKey(plugin, "npc_chat_data");
    }

    public static void addIntegrationKeys(NamespacedKey... keys) {
        for (final var key : keys) {
            integrationKeys.put(key.toString(), key);
        }
    }
}
