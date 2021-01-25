package me.travis.wurstplus.wurstplustwo.hacks.chat;


import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusClearChat extends WurstplusHack {
    public WurstplusClearChat() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "NoChat";
        this.tag = "NoChat";
        this.description = "Remove the chat";
    }

    public void enable() {
        mc.ingameGUI.getChatGUI().clearChatMessages(true);
    }
    public void disable() {
        mc.ingameGUI.getChatGUI().clearChatMessages(false);
    }
}