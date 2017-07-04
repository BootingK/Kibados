package me.canhaotnt;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import io.netty.util.internal.ThreadLocalRandom;

public class Eventos implements Listener,CommandExecutor {
	public int randomNumber;
	public Main Main;

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void place(final BlockPlaceEvent e) {
		final ItemStack item = e.getPlayer().getItemInHand();
		if (item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName()
				.equals(this.Main.getConfig().getString("Nome").replace("&", "§"))) {
			final Block b = e.getBlockPlaced();
			b.setMetadata("CanhaoTnT",
					new FixedMetadataValue(this.Main, "CanhaoTnT"));
		}
	}

	private List<Location> random(final Location a) {
		final List<Location> list = Lists.newLinkedList();
		final int field0;
		double n2;
		double n = n2 = -(field0 = this.randomNumber);
		while (n <= field0) {
			double n4;
			double n3 = n4 = -field0;
			while (n3 <= field0) {
				double n6;
				double n5 = n6 = -field0;
				while (n5 <= field0) {
					final double n7 = n2;
					final double n8 = n7 * n7;
					final double n9 = n4;
					final double n10 = n8 + n9 * n9;
					final double n11 = n6;
					final double n12 = n10 + n11 * n11;
					final int n13 = field0;
					final Location add;
					if (n12 <= n13 * n13
							&& !(add = a.clone().add((int) n2, (int) n4, (int) n6))
									.equals(a)) {
						list.add(add);
					}
					n5 = ++n6;
				}
				n3 = ++n4;
			}
			n = ++n2;
		}
		return list;
	}

	@EventHandler
	public void removeWaterFunction(final EntityExplodeEvent a) {
		final TNTPrimed tntPrimed;
		if (this.Main.getConfig().getBoolean("AguaExplodirTnT") && a.getEntityType() == EntityType.PRIMED_TNT
				&& !(tntPrimed = (TNTPrimed) a.getEntity()).hasMetadata("CanhaoTnT")
				&& (tntPrimed.getLocation().getBlock().getType() == Material.STATIONARY_WATER
						|| tntPrimed.getLocation().getBlock().getType() == Material.WATER)) {
			final List<Location> loc = random(tntPrimed.getLocation());
			int n;
			int i = n = 0;
			while (i < loc.size()) {
				final List<Block> blockList = a.blockList();
				final Location value = loc.get(n);
				++n;
				blockList.add(value.getBlock());
				i = n;
			}
		}
		if (this.Main.getConfig().getBoolean("AguaExplodirCreeper") && a.getEntityType() == EntityType.CREEPER
				&& (a.getEntity().getLocation().getBlock().getType() == Material.STATIONARY_WATER
						|| a.getEntity().getLocation().getBlock().getType() == Material.WATER)) {
			final List<Location> loc = this.random(a.getEntity().getLocation());
			int n2;
			int j = n2 = 0;
			while (j < loc.size()) {
				final List<Block> blockList2 = a.blockList();
				final Location value2 = loc.get(n2);
				++n2;
				blockList2.add(value2.getBlock());
				j = n2;
			}
		}
	}

	@EventHandler
	public void antibug(final BlockDispenseEvent a) {
		final ItemStack item;
		if ((item = a.getItem()).getItemMeta().getDisplayName() != null
				&& item.getItemMeta().getDisplayName().equals(this.Main.getConfig().getString("Nome").replace("&", "§"))
				&& item.getType() == Material.TNT) {
			a.getBlock().getRelative(((Dispenser) a.getBlock().getState().getData()).getFacing()).setMetadata(
					"CanhaoTnT", new FixedMetadataValue(this.Main, "CanhaoTnT"));
		}
	}

	@EventHandler
	public void explodir(final EntityExplodeEvent a) {
		if (a.getEntityType() == EntityType.PRIMED_TNT && ((TNTPrimed) a.getEntity()).hasMetadata("CanhaoTnT")) {
			a.blockList().clear();
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void Method5(final BlockBreakEvent a) {
		if (a.getBlock().getState().hasMetadata("CanhaoTnT")) {
			a.getBlock().getState().removeMetadata("CanhaoTnT", this.Main);
			a.getPlayer().getInventory().addItem(new ItemStack[] { this.Main.item(1) });
			a.getBlock().setType(Material.AIR);
		}
	}

	@EventHandler
	public void Method6(final EventosInstance a) {
		final Block block;
		if ((block = a.getTNT().getWorld().getBlockAt(a.getX(), a.getY(), a.getZ())).hasMetadata("CanhaoTnT")) {
			a.getTNT().setMetadata("CanhaoTnT", block.getMetadata("CanhaoTnT").get(0));
			a.getTNT().setFuseTicks(80);
		}
		new Run(this, block).runTaskLater(Main, 60L);
	}

	@Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String cmdLabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("givetnt")) {
            if (!sender.hasPermission("tintatnt.reload")) {
                sender.sendMessage("§cVoce nao tem permissao para isso!");
                return true;
            }
            if (args.length == 0 || args.length == 1) {
                sender.sendMessage("§cUse /givetnt <player> <quantidade>");
                return true;
            }
            if (args.length >= 2 && Bukkit.getPlayer(args[0]) != null) {
                try {
                    if (Integer.parseInt(args[1]) <= 0) {
                        sender.sendMessage("§cValor Invalido");
                        return true;
                    }
                }
                catch (final Exception e) {
                    sender.sendMessage("§cValor Invalido");
                    return true;
                }
                Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { this.Main.item(Integer.parseInt(args[1])) });
                sender.sendMessage("§aItem adicionado com sucesso!");
            }
        }
        return false;
    }

	public Eventos(final Main a) {
		this.randomNumber = ThreadLocalRandom.current().nextInt(2, 5);
		this.Main = a;
	}
}
