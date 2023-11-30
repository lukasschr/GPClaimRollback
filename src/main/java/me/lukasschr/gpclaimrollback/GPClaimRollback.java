package me.lukasschr.gpclaimrollback;

import me.lukasschr.gpclaimrollback.events.ClaimDeletedEventListener;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class GPClaimRollback extends JavaPlugin {

    @Override
    public void onEnable() {
        // initialize event listeners
        getServer().getPluginManager().registerEvents(new ClaimDeletedEventListener(this), this);
    }

    @Override
    public void onDisable() {}

    /**
     * Returns an instance of the CoreProtectAPI
     *
     * @return  CoreProtectAPI instance, or null if CoreProtect is not loaded or disabled.
     */
    public CoreProtectAPI getCoreProtectAPI() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");
        // check if CoreProtect is loaded
        if (!(plugin instanceof CoreProtect)) {
            return null;
        }
        // check if CoreProtect API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }
        return CoreProtect;
    }
}
