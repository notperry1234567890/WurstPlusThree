// =============================================== //
// Recompile disabled. Please run Recaf with a JDK //
// =============================================== //

package me.travis.wurstplus.wurstplustwo.event.events;


import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBase;
import net.minecraft.network.Packet;

public final class PacketEvent extends WurstplusEventBase {
    private final Packet packet;
    private final PacketEvent.Type type;

    public PacketEvent(Packet packet, PacketEvent.Type type) {
        this.packet = packet;
        this.type = type;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public PacketEvent.Type getType() {
        return this.type;
    }

    public static enum Type {
        INCOMING,
        OUTGOING;
    }
}
 