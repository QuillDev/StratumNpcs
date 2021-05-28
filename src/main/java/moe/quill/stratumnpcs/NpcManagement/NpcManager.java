package moe.quill.stratumnpcs.NpcManagement;

import moe.quill.stratumnpcs.Serialization.StratumSerialization;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class NpcManager {

    private final HashSet<Entity> npcList = new HashSet<>();
    private final static Random rand = new Random();

    /**
     * Create a new NPC manager for the given plugin
     *
     * @param plugin to create the npc manager for
     */
    public NpcManager(Plugin plugin) {
        Bukkit.getWorlds()
                .forEach(world -> {
                    world.getEntities()
                            .stream()
                            .filter(entity -> entity.getPersistentDataContainer().has(NpcKeys.isNpc, PersistentDataType.BYTE_ARRAY))
                            .forEach(npcList::add);
                });

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            //TODO: this may be slow, if it actually matters just manage them in a better
            npcList.stream()
                    //Get only NPCs with the correct keys
                    .filter(npc -> {
                        final var npcData = npc.getPersistentDataContainer();
                        return npcData.has(NpcKeys.canChat, PersistentDataType.BYTE_ARRAY)
                                && npcData.has(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY);
                    })
                    .forEach(npc -> {
                        //If there are no nearby players, return
                        final var nearby = npc.getLocation().getNearbyPlayers(3);
                        if (nearby.isEmpty()) return;
                        //Check if our data is good
                        final var npcData = npc.getPersistentDataContainer();
                        final var lineBytes = npcData.get(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY);
                        final var lines = StratumSerialization.deserializeComponentList(lineBytes);
                        if (lines == null || lines.isEmpty()) return;
                        //Choose a random line to send to nearby players
                        final var line = lines.get(rand.nextInt(lines.size()));
                        nearby.forEach(player -> player.sendMessage(line));
                    });
        }, 20 * 5, 20 * 5);
    }

    /**
     * Add the given key to the given NPC
     *
     * @param name of the npc to add the keyt to
     * @param key  to add to the npc
     * @return whether the key was added to the NPC correctly
     */
    public boolean addKeyToNpc(String name, NamespacedKey key) {
        final var npc = getNpc(name);
        if (npc == null) return false;
        try {
            npc.getPersistentDataContainer().set(key, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeBoolean(true));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the NPC with the given name
     *
     * @param name of the NPC to get
     * @return the npc if it exists, null if it doesnt
     */
    public Entity getNpc(String name) {
        for (final var npc : npcList) {
            final var customName = npc.customName();
            if (customName == null) continue;
            if (((TextComponent) customName).content().equals(name)) {
                return npc;
            }
        }
        return null;
    }

    /**
     * Spawn an NPC at the given location with the given type and name
     *
     * @param location   to spawn the npc
     * @param entityType type of entity to spawn
     * @param name       of the entity to spawn
     * @return whether spawning was successful
     */
    public boolean spawnNpc(Location location, EntityType entityType, Component name) {
        if (hasNpc(((TextComponent) name).content())) {
            return false;
        }
        final var npc = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        npc.setAI(false);
        npc.setInvulnerable(true);
        npc.setPersistent(true);
        npc.customName(name);
        npc.setCustomNameVisible(true);
        final var npcData = npc.getPersistentDataContainer();
        npcData.set(NpcKeys.isNpc, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeBoolean(true));
        npcData.set(NpcKeys.npcNameComponent, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeComponent(name));
        npcData.set(NpcKeys.npcNameRaw, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeString(((TextComponent) name).content()));
        npcList.add(npc);
        return true;
    }

    /**
     * Remove the npc with the given name from the npc manager
     *
     * @param name of the npc to remove
     */
    public void removeNpc(String name) {
        for (final var npc : npcList) {
            if (!StratumSerialization.deserializeString(npc.getPersistentDataContainer()
                    .get(NpcKeys.npcNameRaw, PersistentDataType.BYTE_ARRAY)).equals(name)) continue;

            npc.remove();
            npcList.remove(npc);
            return;
        }
    }

    /**
     * Add a chat line to the given npc using the given string
     *
     * @param name of the npc to add the line to
     * @param line to add to the npc
     * @return whether the chat line was successfully added or not
     */
    public boolean addChatLine(String name, String line) {
        final var npc = getNpc(name);
        if (npc == null) return false;
        //Get npc data
        final var npcData = npc.getPersistentDataContainer();
        try {
            //If they don't already have the can chat flag, set it
            if (!npcData.has(NpcKeys.canChat, PersistentDataType.BYTE_ARRAY)) {
                npcData.set(NpcKeys.canChat, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeBoolean(true));
            }
            //Set this chat line on the npc
            if (!npcData.has(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY)) {
                //Set this line to the NPC
                npcData.set(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeComponentList(
                        new ArrayList<>(Collections.singletonList(Component.text(line).asComponent()))
                ));
                return true;
            }

            //Get the existing chat data
            final var chatDataBytes = npcData.get(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY);
            final var chatData = StratumSerialization.deserializeComponentList(chatDataBytes);
            if (chatData == null) return false; //if the chat data is bad, return false
            chatData.add(Component.text(line));
            npcData.set(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeComponentList(chatData));
            return true; //if it all worked out, return true
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Check if the given NPC name is a villager
     *
     * @param name of the npc
     * @return whether they are a villager
     */
    public boolean isVillager(String name) {
        final var target = getNpc(name);
        if (target == null) return false;
        return target instanceof Villager;
    }

    /**
     * Check whether the npc manager has an npc with the given name
     *
     * @param name of the NPC
     * @return whether that NPC exists or not
     */
    public boolean hasNpc(String name) {
        return getNpcNames().contains(name);
    }

    /**
     * Get the names of all existing NPCs
     *
     * @return all npc names
     */
    public ArrayList<String> getNpcNames() {
        return npcList
                .stream()
                .map(entity -> StratumSerialization.deserializeString(entity.getPersistentDataContainer().get(NpcKeys.npcNameRaw, PersistentDataType.BYTE_ARRAY)))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Remove the chat line of the given index from the given npc
     *
     * @param name  of the npc to remove the line from
     * @param index of the line to remove
     * @return whether the line was removed successfully or not.
     */
    public boolean removeChatLine(String name, int index) {
        final var npc = getNpc(name);
        if (npc == null) return false;
        //Get chat data
        final var npcData = npc.getPersistentDataContainer();
        final var lines = StratumSerialization.deserializeComponentList(npcData.get(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY));

        //If lines is null, just return out
        if (lines == null) return false;
        try {
            lines.remove(index);
            npcData.set(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY, StratumSerialization.serializeComponentList(lines));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Get the chat line components for the npc with the given name
     *
     * @param name of the npc to get chat lines for
     * @return the list of components if they exist
     */
    public ArrayList<Component> getChatLines(String name) {
        final var npc = getNpc(name);

        //If the npc doesnt exist return an empty list
        if (npc == null) {
            return new ArrayList<>();
        }
        final var npcData = npc.getPersistentDataContainer();
        if (!npcData.has(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY)) {
            return new ArrayList<>();
        }
        final var chatDataBytes = npcData.get(NpcKeys.chatData, PersistentDataType.BYTE_ARRAY);
        final var chatData = StratumSerialization.deserializeComponentList(chatDataBytes);
        if (chatData == null) {
            return new ArrayList<>();
        }

        return chatData;
    }

    /**
     * Get the list of npcs that currently exist
     *
     * @return the list of npcs as a hash set
     */
    public HashSet<Entity> getNpcList() {
        return npcList;
    }
}
