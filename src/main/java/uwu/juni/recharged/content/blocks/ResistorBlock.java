package uwu.juni.recharged.content.blocks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import uwu.juni.recharged.content.blocks.block_entities.ResistorBlockEntity;

@ParametersAreNonnullByDefault
public class ResistorBlock extends DiodeBlock implements EntityBlock {
	public static final MapCodec<ResistorBlock> CODEC = simpleCodec(ResistorBlock::new);
	public static final IntegerProperty RESISTANCE = IntegerProperty.create("resistance", 0, 15);

	@Override
	protected MapCodec<? extends ResistorBlock> codec() {
		return CODEC;
	}

	public ResistorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(POWERED, Boolean.FALSE).setValue(RESISTANCE, 0));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, RESISTANCE);
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ResistorBlockEntity(pos, state);
	}

	@Override
	protected int getDelay(BlockState state) {
		return 0;
	}

	@Override
	protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
		var signal = super.getInputSignal(level, pos, state);

		if (level.getBlockEntity(pos) instanceof ResistorBlockEntity entity) {
			entity.setOutputSignal(signal);
		}

		return signal;
	}

	@Override
	protected InteractionResult useWithoutItem(
		BlockState state,
		Level level,
		BlockPos pos,
		Player player,
		BlockHitResult hitResult
	) {
		if (!player.getAbilities().mayBuild) {
			return InteractionResult.PASS;
		}

		if (!level.isClientSide) {
			level.setBlock(
				pos,
				state.setValue(RESISTANCE, this.getAndWrapResistance(state)),
				3
			);
		} else {
			level.playLocalSound(
				pos,
				SoundEvents.STONE_BUTTON_CLICK_ON,
				SoundSource.BLOCKS,
				1F,
				1 + ((float)this.getAndWrapResistance(state) / 15F),
				false
			);
		}

		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
		return level.getBlockEntity(pos) instanceof ResistorBlockEntity entity
			? entity.calcOutputSignal(state.getValue(RESISTANCE))
			: 0;
	}

	public static int color(
		BlockState state,
		@Nullable BlockAndTintGetter level,
		@Nullable BlockPos pos,
		int tintIndex
	) {
		return RedStoneWireBlock.getColorForPower(state.getValue(RESISTANCE));
	}

	int getAndWrapResistance(BlockState state) {
		var resistance = state.getValue(RESISTANCE);
		resistance++;
		if (resistance > 15) {
			resistance = 0;
		}

		return resistance;
	}
}
