package me.lukasschr.gpclaimrollback.utils;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Location;

import java.util.List;

public class CoreProtectRollback implements Runnable{

    CoreProtectAPI api;
    int time;
    List<String> restrict_users;
    List<String> exclude_users;
    List<Object> restrict_blocks;
    List<Object> exclude_blocks;
    List<Integer> action_list;
    int radius;
    Location radius_location;

    /**
     * Initializes a CoreProtectRollback instance for performing rollbacks using CoreProtect.
     * Refer to the <a href="https://docs.coreprotect.net/api/version/v9/">CoreProtect API reference</a> for more details.
     *
     * @param coreProtectAPI    CoreProtectAPI instance,
     * @param time              Specify the amount of time to rollback. "5" would return results from the last 5 seconds.
     * @param restrict_users    Specify any usernames to perform the rollback on. Can be set to "null" if both a radius and a location are specified.
     * @param exclude_users     Specify any usernames to exclude from the rollback. Can be set to "null".
     * @param restrict_blocks   Specify a list of EntityType's or Material's to restrict the rollback to. Can be set to "null".
     * @param exclude_blocks    Specify a list of EntityType's or Material's to exclude from the rollback. Can be set to "null".
     * @param action_list       Specify a list of action types to restrict the rollback to. Can be set to "null"
     * @param radius            Specify a radius to restrict the rollback to. A location must be specified if using this. Set to "0" to disable.
     * @param radius_location   Specify a location to rollback around. Can be set to "null" if no radius is specified, and a user is specified.
     */
    public CoreProtectRollback(CoreProtectAPI coreProtectAPI, int time, List<String> restrict_users,
                               List<String> exclude_users, List<Object> restrict_blocks, List<Object> exclude_blocks,
                               List<Integer> action_list, int radius, Location radius_location) {
        this.api = coreProtectAPI;
        this.time = time;
        this.restrict_users = restrict_users;
        this.exclude_users = exclude_users;
        this.restrict_blocks = restrict_blocks;
        this.exclude_blocks = exclude_blocks;
        this.action_list = action_list;
        this.radius = radius;
        this.radius_location = radius_location;
    }


    /**
     * Performs rollback asynchronous
     */
    @Override
    public void run() {
        assert api != null;
        List<String[]> result = api.performRollback(time, restrict_users, exclude_users, restrict_blocks, exclude_blocks,
                                                    action_list, radius, radius_location);
    }
}
