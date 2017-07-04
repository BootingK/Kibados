package me.canhaotnt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class Main extends JavaPlugin implements CommandExecutor {

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new Eventos(this), this);
		this.getCommand("givetnt").setExecutor(new Eventos(this));

		new Runnable(this).runTaskTimer(this, 0L, 1L);
		System.out.println("[CanhaoTNT-Reload] Ativado!");
	}

	public ItemStack item(final int quantidade) {
		final ItemStack tnt = new ItemStack(Material.TNT, quantidade);
		final ItemMeta meta = tnt.getItemMeta();
		meta.setDisplayName(this.getConfig().getString("Nome").replace("&", "§"));
		final List<String> lor = new ArrayList<String>();
		for (final String lore : this.getConfig().getStringList("Lore")) {
			lor.add(lore.replace("&", "§"));
		}
		meta.setLore(lor);
		tnt.setItemMeta(meta);
		return GlowsAPI(tnt);
	}

	@Override
	public void onDisable() {
		System.out.println("[CanhaoTNT-Reload] Desativado!");
	}

	public ItemStack GlowsAPI(ItemStack item) {

		final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);

		}
		if (tag == null) {
			tag = nmsStack.getTag();
		}
		final NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

}
