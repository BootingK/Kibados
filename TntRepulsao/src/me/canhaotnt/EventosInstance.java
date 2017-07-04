package me.canhaotnt;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventosInstance extends Event implements Cancellable {
	private static final HandlerList handlers;
	int x;
	int y;
	int z;
	public TNTPrimed tnt;
	boolean cancelled;

	static {
		handlers = new HandlerList();
	}

	public EventosInstance(final int x, final int y, final int z, final TNTPrimed tnt) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tnt = tnt;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public TNTPrimed getTNT() {
		return this.tnt;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(final boolean arg0) {
		this.cancelled = arg0;
	}
}
