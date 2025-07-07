package uwu.juni.nonsense.content.blocks.block_entities;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import uwu.juni.nonsense.content.NonsenseBlockEntities;

@ParametersAreNonnullByDefault
public class LatchBlockEntity extends BlockEntity {
	private int output;
	private boolean ub;

	public LatchBlockEntity(BlockPos pos, BlockState state) {
		super(NonsenseBlockEntities.LATCH.get(), pos, state);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putInt("OutputSignal", this.output);
		tag.putBoolean("HasUb", this.ub);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.output = tag.getInt("OutputSignal");
		this.ub = tag.getBoolean("HasUb");
	}

	public void setOutputSignal(int output) {
		this.output = output;
	}

	public int getOutputSignal() {
		return this.output;
	}

	public void setHasUb(boolean ub) {
		this.ub = ub;
	}

	public boolean getHasUb() {
		return this.ub;
	}

	public void setUbOutputSignal(RandomSource rand) {
		if (!this.ub) {
			return;
		}

		this.output = rand.nextInt(rand.nextInt(1, 5), 15);
	}
}
