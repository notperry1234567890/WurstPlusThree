package me.travis.wurstplus.wurstplustwo.event;

public abstract class WurstplusEventBase {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}