package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMove;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPlayerJump;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;


public class WurstplusSpeed extends WurstplusHack {

    public WurstplusSpeed() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);

        this.name        = "Speed";
        this.tag         = "Speed";
    }

    WurstplusSetting speed_mode = create("Mode", "StrafeMode", "OnGround", combobox("Strafe", "On Ground"));
    WurstplusSetting on_water = create("On Water", "StrafeOnWater", true);
    WurstplusSetting auto_jump = create("Auto Jump", "StrafeAutoJump", true);
    WurstplusSetting backward = create("Backwards", "StrafeBackwards", true);

    @Override
    public void update() {

        if (mc.player.isRiding()) return;

        if (mc.player.isInWater() || mc.player.isInLava()) {
            if (!on_water.get_value(true)) return;
        }

        if (mc.player.moveForward != 0 || mc.player.moveStrafing != 0) {

            if (mc.player.moveForward < 0 && !backward.get_value(true)) return;

            if (mc.player.onGround && speed_mode.in("Strafe")) {

                if (auto_jump.get_value(true)) {
                    mc.player.motionY = 0.410f;
                }

                final float yaw = get_rotation_yaw() * 0.017453292F;
                mc.player.motionX -= MathHelper.sin(yaw) * 0.2880f;
                mc.player.motionZ += MathHelper.cos(yaw) * 0.2880f;

            } else if (mc.player.onGround && speed_mode.in("On Ground")) {
                    mc.player.motionX -= 0.2790f;
                    mc.player.motionZ -= 0.2790f;

                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }

        if (mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
            mc.player.motionY = 0.405f;
        }

    }

    @EventHandler
    private Listener<WurstplusEventPlayerJump> on_jump = new Listener<>(event -> {

        if (speed_mode.in("Strafe")) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<WurstplusEventMove> player_move = new Listener<>(event -> {

        if (mc.player.isInWater() || mc.player.isInLava()) {
            if (!speed_mode.get_value(true)) return;
        }

        if (mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) return;

        float player_speed = 0.2873f;
        float move_forward = mc.player.movementInput.moveForward;
        float move_strafe = mc.player.movementInput.moveStrafe;
        float rotation_yaw = mc.player.rotationYaw;

        if (mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amp = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            player_speed *= (1.2f * (amp+1));
        }

        if (move_forward == 0 && move_strafe == 0) {
            event.set_x(0.0d);
            event.set_z(0.0d);
        } else {
            if (move_forward != 0.0f) {
                if (move_strafe > 0.0f) {
                    rotation_yaw += ((move_forward > 0.0f) ? -45 : 45);
                } else if (move_strafe < 0.0f) {
                    rotation_yaw += ((move_forward > 0.0f) ? 45 : -45);
                }
                move_strafe = 0.0f;
                if (move_forward > 0.0f) {
                    move_forward = 1.0f;
                } else if (move_forward < 0.0f) {
                    move_forward = -1.0f;
                }
            }

            event.set_x((move_forward * player_speed) * Math.cos(Math.toRadians((rotation_yaw + 90.0f))) + (move_strafe * player_speed) * Math.sin(Math.toRadians((rotation_yaw + 90.0f))));
            event.set_z((move_forward * player_speed) * Math.sin(Math.toRadians((rotation_yaw + 90.0f))) - (move_strafe * player_speed) * Math.cos(Math.toRadians((rotation_yaw + 90.0f))));

        }

        event.cancel();

    });

    private float get_rotation_yaw() {
        float rotation_yaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotation_yaw += 180.0f;
        }
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            rotation_yaw -= 90.0f * n;
        }
        if (mc.player.moveStrafing < 0.0f) {
            rotation_yaw += 90.0f * n;
        }
        return rotation_yaw * 0.017453292f;
    }

}