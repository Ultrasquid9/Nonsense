package uwu.juni.recharged.content.blocks.block_entities;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.RechargedBlockEntities;
import uwu.juni.recharged.content.blocks.ShulkerTendonBlock;

@ParametersAreNonnullByDefault
public class ShulkerTendonBlockEntity extends BlockEntity {
	private float cooldown;

	public ShulkerTendonBlockEntity(BlockPos pos, BlockState state) {
		super(RechargedBlockEntities.SHULKER_TENDON.get(), pos, state);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putFloat("Cooldown", this.cooldown);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.cooldown = tag.getFloat("Cooldown");
	}

	public static <BE extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, BE be) {
		if (level.isClientSide) {
			return;
		}
		
		if (be instanceof ShulkerTendonBlockEntity shulkerTendon) {
			if (shulkerTendon.cooldown <= 0) {
				return;
			}

			if (--shulkerTendon.cooldown <= 0) {
				level.setBlock(pos, state.setValue(ShulkerTendonBlock.SHUT, Boolean.FALSE), Block.UPDATE_ALL);
				level.scheduleTick(pos, level.getBlockState(pos).getBlock(), ShulkerTendonBlock.DELAY);
			}
		} else {
			Recharged.LOGGER.error("Attempted to create Shulker Tendon ticker for block entity ", be);
		}
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
}
