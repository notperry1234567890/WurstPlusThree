package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDiscordUtil;

public class WurstplusRichPresence extends WurstplusHack {
    public WurstplusRichPresence() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "DiscordRPC";
        this.tag = "DiscordRPC";
        this.description = "Discord Rich Presence";
    }

    public void enable() {
        WurstplusDiscordUtil.start();
    }

    public void disable() {
        WurstplusDiscordUtil.stop();
    }
}
