package me.travis.wurstplus.wurstplustwo.hacks.movement;


import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusElytraFly extends WurstplusHack {
    public WurstplusElytraFly() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "ElytraFly";
        this.tag = "ElytraFlight";
        this.description = "Allows you to fly with elytra.";
    }

    WurstplusSetting speed = create("Speed", "Speed", 1.16, 0.0, 5.0);

    public void update(){
        if(mc.player.capabilities.isFlying || mc.player.isElytraFlying())
            mc.player.setSprinting(false);
        if (mc.player.capabilities.isFlying) {
            mc.player.setVelocity(0, 0, 0);
            mc.player.setPosition(mc.player.posX, mc.player.posY - 0, mc.player.posZ);
            mc.player.capabilities.setFlySpeed((float)speed.get_value(1));
            mc.player.setSprinting(false);
        }

        if (mc.player.onGround) {
            mc.player.capabilities.allowFlying = false;
        }

        if (mc.player.isElytraFlying()) {
            mc.player.capabilities.setFlySpeed(.915f);
            mc.player.capabilities.isFlying = true;

            if (!mc.player.capabilities.isCreativeMode)
                mc.player.capabilities.allowFlying = true;
        }
    }

    protected void disable() {
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.setFlySpeed(0.05f);
        if (!mc.player.capabilities.isCreativeMode)
            mc.player.capabilities.allowFlying = false;
    }

}