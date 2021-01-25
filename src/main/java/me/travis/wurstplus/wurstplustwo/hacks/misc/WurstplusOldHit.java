package me.travis.wurstplus.wurstplustwo.hacks.misc;


import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class WurstplusOldHit extends WurstplusHack {
    WurstplusSetting sword = this.create("Only Sword", "WurstplusSwingOnlySword", false);

    public WurstplusOldHit() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "OldHit";
        this.tag = "OldHit";
        this.description = "OldHit, lol.";
    }

    public void update() {
        EntityRenderer Entity;
        ItemRenderer Item;
        boolean boo;
        if (this.sword.get_value(true)) {
            label18: {
                EntityPlayerSP Sp = mc.player;
                ItemStack Stack = Sp.getHeldItemMainhand();
                if (Stack.getItem() instanceof ItemSword) {
                    Entity = mc.entityRenderer;
                    Item = Entity.itemRenderer;
                    if ((double)Item.prevEquippedProgressMainHand >= 0.9D) {
                        boo = true;
                        break label18;
                    }
                }

                ItemStack var70 = Sp.getHeldItemMainhand();
                boo = false;
            }
        } else {
            boo = true;
        }

        if (boo) {
            Entity = mc.entityRenderer;
            Item = Entity.itemRenderer;
            Item.equippedProgressMainHand = 1.0F;
            Entity = mc.entityRenderer;
            Item = Entity.itemRenderer;
            EntityPlayerSP var10001 = mc.player;
            Item.itemStackMainHand = var10001.getHeldItemMainhand();
        }

    }
}