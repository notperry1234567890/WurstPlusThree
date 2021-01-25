// =============================================== //
// Recompile disabled. Please run Recaf with a JDK //
// =============================================== //

package me.travis.wurstplus.wurstplustwo.util;

import me.travis.wurstplus.wurstplustwo.MinecraftInstance;
import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class WorldUtil implements MinecraftInstance {
    public static void placeBlock(BlockPos pos) {
        EnumFacing[] var1 = EnumFacing.values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            EnumFacing enumFacing = var1[var3];
            if (!mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(pos)) {
                Vec3d vec = new Vec3d((double)pos.getX() + 0.5D + (double)enumFacing.getXOffset() * 0.5D, (double)pos.getY() + 0.5D + (double)enumFacing.getYOffset() * 0.5D, (double)pos.getZ() + 0.5D + (double)enumFacing.getZOffset() * 0.5D);
                float[] old = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};
                mc.player.connection.sendPacket(new Rotation((float)Math.toDegrees(Math.atan2(vec.z - mc.player.posZ, vec.x - mc.player.posX)) - 90.0F, (float)(-Math.toDegrees(Math.atan2(vec.y - (mc.player.posY + (double)mc.player.getEyeHeight()), Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ))))), mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
                mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
                mc.player.connection.sendPacket(new Rotation(old[0], old[1], mc.player.onGround));
                return;
            }
        }

    }

    public static boolean isIntercepted(BlockPos pos) {
        Iterator var1 = mc.world.loadedEntityList.iterator();

        Entity entity;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            entity = (Entity)var1.next();
        } while(!(new AxisAlignedBB(pos)).intersects(entity.getEntityBoundingBox()));

        return true;
    }

    public static boolean isInterceptedByOther(BlockPos pos) {
        Iterator var1 = mc.world.loadedEntityList.iterator();

        Entity entity;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            entity = (Entity)var1.next();
        } while(entity.equals(mc.player) || !(new AxisAlignedBB(pos)).intersects(entity.getEntityBoundingBox()));

        return true;
    }

    public static boolean isInHole(Entity entity) {
        return isHole(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }

    public static boolean isObsidianHole(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 3, 0)).getBlock().equals(Blocks.AIR)) {
            BlockPos[] var1 = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
            BlockPos[] var1 = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isHole(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
            BlockPos[] var1 = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK && iBlockState.getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isDoubleHoleX(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) && (mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) && (mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR))) {
            BlockPos[] var1 = new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK && iBlockState.getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isDoubleHoleZ(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) && (mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR)) && (mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR))) {
            BlockPos[] var1 = new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK && iBlockState.getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isDoubleBedrockHoleX(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) && (mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) && (mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR))) {
            BlockPos[] var1 = new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isDoubleBedrockHoleZ(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) && (mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR)) && (mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR))) {
            BlockPos[] var1 = new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isDoubleObsidianHoleX(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) && (mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) && (mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR))) {
            BlockPos[] var1 = new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isDoubleObsidianHoleZ(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) && (mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR)) && (mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR))) {
            BlockPos[] var1 = new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)};
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BlockPos blockPos2 = var1[var3];
                IBlockState iBlockState = mc.world.getBlockState(blockPos2);
                if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isBreakable(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock().getBlockHardness(mc.world.getBlockState(pos), mc.world, pos) != -1.0F;
    }
}
 