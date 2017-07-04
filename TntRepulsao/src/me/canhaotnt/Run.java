package me.canhaotnt;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class Run extends BukkitRunnable
{
    private final Block block;
    public final Eventos eventos;

    @Override
    public void run() {
        this.block.removeMetadata("CanhaoTnT", this.eventos.Main);
    }

    public Run(final Eventos eventos, final Block block) {
        this.eventos = eventos;
        this.block = block;
    }
}
