package moe.quill.stratumnpcs.Events;

import moe.quill.stratumnpcs.NpcManagement.NpcKeys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class NpcImmortalityEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void npcDamageEvent(EntityDamageEvent event) {
        if (!event.getEntity().getPersistentDataContainer().has(NpcKeys.isNpc, PersistentDataType.BYTE_ARRAY)) return;
        event.setDamage(0);
        event.setCancelled(true);
    }
}
