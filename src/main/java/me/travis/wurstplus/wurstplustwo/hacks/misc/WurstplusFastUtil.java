package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.math.BlockPos;

public class WurstplusFastUtil extends WurstplusHack {
	WurstplusSetting fast_place = this.create("Place", "WurstplusFastPlace", false);
	WurstplusSetting fast_break = this.create("Break", "WurstplusFastBreak", false);
	WurstplusSetting crystal = this.create("Crystal", "WurstplusFastCrystal", false);
	WurstplusSetting exp = this.create("EXP", "WurstplusFastExp", true);
	WurstplusSetting bow = this.create("Bow", "WurstplusFastBow", true);

	public WurstplusFastUtil() {
		super(WurstplusCategory.WURSTPLUS_MISC);
		this.name = "Fast Util";
		this.tag = "FastUtil";
		this.description = "use things faster";
	}

	public void update() {
		Item main = mc.player.getHeldItemMainhand().getItem();
		Item off = mc.player.getHeldItemOffhand().getItem();
		boolean main_exp = main instanceof ItemExpBottle;
		boolean off_exp = off instanceof ItemExpBottle;
		boolean main_cry = main instanceof ItemEndCrystal;
		boolean off_cry = off instanceof ItemEndCrystal;
		boolean main_bow = main instanceof ItemBow;
		boolean off_bow = off instanceof ItemBow;
		if (main_exp | off_exp && this.exp.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (main_bow | off_bow && this.bow.get_value(true) && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
			mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
			mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
			mc.player.stopActiveHand();
		}

		if (main_cry | off_cry && this.crystal.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (!(main_exp | off_exp | main_cry | off_cry) && this.fast_place.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (this.fast_break.get_value(true)) {
			mc.playerController.blockHitDelay = 0;
		}

	}
}