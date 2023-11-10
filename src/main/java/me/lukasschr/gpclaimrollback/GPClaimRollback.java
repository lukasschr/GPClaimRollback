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

    public CoreProtectAPI getCoreProtectAPI() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        if (!(plugin instanceof CoreProtect)) { // check if CoreProtect is loaded
            return null;
        }

        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) { // check if the API is enabled
            return null;
        }

        return CoreProtect;
    }
}
