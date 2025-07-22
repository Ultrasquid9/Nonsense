package uwu.juni.recharged.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import uwu.juni.recharged.RechargedConfig;
import uwu.juni.recharged.utils.SmartSticky;

@Mixin(PistonStructureResolver.class)
public abstract class ChainstonePistonResolver {
	@Shadow
	private Level level;
	@Shadow
	private BlockPos pistonPos;
	@Shadow
	private List<BlockPos> toPush;
	@Shadow
	private List<BlockPos> toDestroy;

	@Shadow
	abstract Direction getPushDirection();
	@Shadow
	abstract void reorderListAtCollision(int offsets, int index);

	@WrapMethod(method = "addBranchingBlocks")
	private boolean Recharged_ChainstoneAddBranchingBlocks(BlockPos fromPos, Operation<Boolean> og) {
		if (RechargedConfig.getConfigValue(RechargedConfig.CHAINSTONE)) {
			return this.addBranchingBlocks2(fromPos);
		} else {
			return og.call(fromPos);
		}
	}

	@WrapMethod(method = "addBlockLine")
	private boolean Recharged_ChainstoneAddBlockLine(BlockPos originPos, Direction dir, Operation<Boolean> og) {
		if (RechargedConfig.getConfigValue(RechargedConfig.CHAINSTONE)) {
			return this.addBlockLine2(originPos, dir);
		} else {
			return og.call(originPos, dir);
		}
	}

	private boolean addBranchingBlocks2(BlockPos fromPos) {
		var fromState = this.level.getBlockState(fromPos);

		for (var dir : Direction.values()) {
			if (dir.getAxis() == this.getPushDirection().getAxis()) {
				continue;
			}

			var pos = fromPos.relative(dir);
			var state = this.level.getBlockState(pos);

			if (stickToEachOther(state, fromState, pos, fromPos, dir) && !this.addBlockLine2(pos, dir)) {
				return false;
			}
		}

		return true;
	}

	private boolean addBlockLine2(BlockPos originPos, Direction direction) {
		var state = this.level.getBlockState(originPos);

		if (state.isAir()) {
			return true;
		} else if (!PistonBaseBlock.isPushable(state, this.level, originPos, this.getPushDirection(), false, direction)) {
			return true;
		} else if (originPos.equals(this.pistonPos)) {
			return true;
		} else if (this.toPush.contains(originPos)) {
			return true;
		}

		var i = 1;
		if (i + this.toPush.size() > 12) {
			return false;
		}

		BlockState oldState;
		BlockPos oldPos;

		while (state.isStickyBlock()) {
			var pos = originPos.relative(this.getPushDirection().getOpposite(), i);
			oldPos = pos.relative(this.getPushDirection());

			oldState = state;
			state = this.level.getBlockState(pos);

			if (
				state.isAir()
				|| !stickToEachOther(state, oldState, pos, oldPos, this.getPushDirection().getOpposite())
				|| !PistonBaseBlock.isPushable(state, this.level, pos, this.getPushDirection(), false, this.getPushDirection().getOpposite())
				|| pos.equals(this.pistonPos)
			) {
				break;
			}

			if (++i + this.toPush.size() > 12) {
				return false;
			}
		}

		var l = 0;

		for (var i1 = i - 1; i1 >= 0; i1--) {
			this.toPush.add(originPos.relative(this.getPushDirection().getOpposite(), i1));
			l++;
		}

		var j1 = 1;

		while (true) {
			var blockpos1 = originPos.relative(this.getPushDirection(), j1);
			int j = this.toPush.indexOf(blockpos1);
			if (j > -1) {
				this.reorderListAtCollision(l, j);

				for (int k = 0; k <= j + l; k++) {
					var blockpos2 = this.toPush.get(k);
					if (this.level.getBlockState(blockpos2).isStickyBlock() && !this.addBranchingBlocks2(blockpos2)) {
						return false;
					}
				}

				return true;
			}

			state = this.level.getBlockState(blockpos1);
			if (state.isAir()) {
				return true;
			}

			if (
				!PistonBaseBlock.isPushable(state, this.level, blockpos1, this.getPushDirection(), true, this.getPushDirection())
				|| blockpos1.equals(this.pistonPos)
			) {
				return false;
			}

			if (state.getPistonPushReaction() == PushReaction.DESTROY) {
				this.toDestroy.add(blockpos1);
				return true;
			}

			if (this.toPush.size() >= 12) {
				return false;
			}

			this.toPush.add(blockpos1);
			l++;
			j1++;
		}
	}

	private boolean stickToEachOther(
		BlockState selfState,
		BlockState otherState,
		BlockPos selfPos,
		BlockPos otherPos,
		Direction dir
	) {
		var selfFlag = maybeSmartSticksTo(selfState, otherState, selfPos, otherPos, dir);
		var otherFlag = maybeSmartSticksTo(otherState, selfState, otherPos, selfPos, dir);

		return selfFlag && otherFlag;
	}

	private boolean maybeSmartSticksTo(
		BlockState selfState,
		BlockState otherState,
		BlockPos selfPos,
		BlockPos otherPos,
		Direction dir
	) {
		return selfState.getBlock() instanceof SmartSticky sticky
			? sticky.canStickTo(selfState, otherState, selfPos, otherPos, dir)
			: selfState.canStickTo(otherState);
	}
}
