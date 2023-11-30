package me.lukasschr.gpclaimrollback.events;

import me.lukasschr.gpclaimrollback.GPClaimRollback;
import me.lukasschr.gpclaimrollback.utils.CoreProtectRollback;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.events.ClaimDeletedEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClaimDeletedEventListener implements Listener {

    GPClaimRollback plugin;
    Thread performRollback;

    public ClaimDeletedEventListener(GPClaimRollback plugin) {this.plugin = plugin;}

    /**
     * Handles the ClaimDeletedEvent by initiating a rollback process using CoreProtect.
     * <p>
     * Once a player deletes a claim, this method performs a rollback
     * so that all changes made since the claim was created are reset.
     * For this purpose, the necessary variables are determined and calculated.
     * It then creates a new thread to perform the rollback using CoreProtectRollback.
     *
     * @param event ClaimDeletedEvent triggered when a claim is deleted.
     */
    @EventHandler
    public void onClaimDeletedEvent(ClaimDeletedEvent event) {
        Claim claim = event.getClaim();

        int time = calculateRollbackTime(claim);
        List<String> claimPlayers = getClaimPlayers(claim);
        int radius = calculateRadius(claim);
        Location claimCenter = calculateClaimCenter(claim);

        performRollback = new Thread(new CoreProtectRollback(
                plugin.getCoreProtectAPI(),
                time,
                claimPlayers,
                null,
                null,
                null,
                null,
                radius,
                claimCenter
        ));
        performRollback.start();
    }

    private Integer calculateRollbackTime(Claim claim) {
        Date claimModifiedDate = claim.modifiedDate;

        Instant claimModifiedInstant = claimModifiedDate.toInstant();
        Instant now = Instant.now();
        Duration duration = Duration.between(claimModifiedInstant, now);

        return Math.toIntExact(duration.getSeconds()) + 1;
    }

    private Location calculateClaimCenter(Claim claim) {
        Location lesserBoundaryCorner = claim.getLesserBoundaryCorner();
        Location greaterBoundaryCorner = claim.getGreaterBoundaryCorner();

        double x = (lesserBoundaryCorner.getX() + greaterBoundaryCorner.getX()) / 2;
        double y = (lesserBoundaryCorner.getY() + greaterBoundaryCorner.getY()) / 2;
        double z = (lesserBoundaryCorner.getZ() + greaterBoundaryCorner.getZ()) / 2;

        assert lesserBoundaryCorner.getWorld() == greaterBoundaryCorner.getWorld();
        return new Location(lesserBoundaryCorner.getWorld(), x, y, z);
    }

    private int calculateRadius(Claim claim) {
        int height = claim.getHeight();
        int width = claim.getWidth();
        return Math.max(height / 2, width / 2) + 1;
    }

    private List<String> getClaimPlayers(Claim claim) {
        List<String> claimPlayers = new ArrayList<>(claim.managers);
        claimPlayers.add(claim.getOwnerName());
        return claimPlayers;
    }
}
