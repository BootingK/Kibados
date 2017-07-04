package me.canhaotnt;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnable extends BukkitRunnable {

	public Main main;

	public Runnable(final Main a) {
		this.main = a;
	}

	@Override
	public void run() {
		for (final World world : Bukkit.getServer().getWorlds()) {
			for (final Entity e : world.getEntities()) {
				if (e.getType() == EntityType.PRIMED_TNT && !e.hasMetadata("Marked")) {
					final TNTPrimed tnt = (TNTPrimed) e;
					tnt.setMetadata("Marked", new FixedMetadataValue(this.main, "Marked"));
					Bukkit.getServer().getPluginManager().callEvent(new EventosInstance(tnt.getLocation().getBlockX(), tnt.getLocation().getBlockY(), tnt.getLocation().getBlockZ(), tnt));
				}
			}
		}
	}

}
