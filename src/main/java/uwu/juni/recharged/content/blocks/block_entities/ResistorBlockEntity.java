package uwu.juni.recharged.content.blocks.block_entities;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import uwu.juni.recharged.content.RechargedBlockEntities;

@ParametersAreNonnullByDefault
public class ResistorBlockEntity extends BlockEntity {
	private int output;

	public ResistorBlockEntity(BlockPos pos, BlockState state) {
		super(RechargedBlockEntities.RESISTOR.get(), pos, state);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putInt("OutputSignal", this.output);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.output = tag.getInt("OutputSignal");
	}

	public void setOutputSignal(int output) {
		this.output = output;
	}

	public int calcOutputSignal(int resistance) {
		return Mth.clamp(this.output, 0, resistance);
	}
}
