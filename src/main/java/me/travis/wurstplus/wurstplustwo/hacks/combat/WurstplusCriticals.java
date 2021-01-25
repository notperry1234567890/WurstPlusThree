package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.event.Listener;
import me.travis.wurstplus.wurstplustwo.event.events.PacketEvent;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class WurstplusCriticals extends WurstplusHack {
    public WurstplusCriticals() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Criticals";
        this.tag = "Criticals";
    }

    WurstplusSetting crystals = create("Crystals", "Crystals", false);

    @Listener
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.INCOMING && event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround && (this.crystals.get_value(true) || !(((CPacketUseEntity)event.getPacket()).getEntityFromWorld(mc.world) instanceof EntityEnderCrystal))) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612D, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
        }
    }
}