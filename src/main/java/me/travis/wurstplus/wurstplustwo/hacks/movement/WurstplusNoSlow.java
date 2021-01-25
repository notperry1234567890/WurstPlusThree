package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;

import java.util.function.Predicate;

public class WurstplusNoSlow extends WurstplusHack {
    public WurstplusNoSlow() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "No Slow";
        this.tag = "NoSlow";
        this.description = "dont make you slow while doing anything, duh.";
    }

    @EventHandler
    private Listener<InputUpdateEvent> eventListener = new Listener<>(event -> {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    });
}

