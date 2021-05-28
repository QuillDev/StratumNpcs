package moe.quill.stratumnpcs.NpcManagement;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class IntegrationsManager {

    private final PluginManager pluginManager;
    private final Plugin plugin;

    public IntegrationsManager(Plugin plugin) {
        this.pluginManager = plugin.getServer().getPluginManager();
        this.plugin = plugin;
        init();
    }

    public void init() {
        //Check for stratum survival
        final var stratumSurvival = pluginManager.getPlugin("StratumSurvival");
        if (stratumSurvival != null) {
            System.out.println("Found Stratum Survival, enabling integrations");
            final var cryptologistKey = new NamespacedKey(stratumSurvival, "npc_cryptologist");
            final var blacksmithKey = new NamespacedKey(stratumSurvival, "npc_blacksmith");
            NpcKeys.addIntegrationKeys(cryptologistKey, blacksmithKey);
        }

        final var stratumEconomy = pluginManager.getPlugin("StratumEconomy");
        if (stratumEconomy != null) {
            System.out.println("Found Stratum Economy, enabling integrations");
            final var vendorKey = new NamespacedKey(stratumEconomy, "npc_vendor");
            NpcKeys.addIntegrationKeys(vendorKey);
        }
    }
}
