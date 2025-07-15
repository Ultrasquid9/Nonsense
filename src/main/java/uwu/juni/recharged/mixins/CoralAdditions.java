package uwu.juni.recharged.mixins;

import java.util.Optional;

import javax.annotation.ParametersAreNonnullByDefault;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags.Fluids;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.datagen.RechargedTags;
import uwu.juni.recharged.datagen.loot.RechargedMiscLoot;

@Mixin(BaseCoralPlantTypeBlock.class)
@ParametersAreNonnullByDefault
public abstract class CoralAdditions extends Block implements SimpleWaterloggedBlock, BonemealableBlock {
	private CoralAdditions() {
		super(null);
	}

	@Inject(method = "<init>", at = @At("HEAD"))
	private static void setRandomTick(BlockBehaviour.Properties properties, CallbackInfo ci) {
		properties.randomTicks();
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!this.isDead(state)) {
			return;
		}

		var fluid = level.getFluidState(pos);
		if (!fluid.is(Fluids.WATER) || fluid.getFlow(level, pos) == Vec3.ZERO) {
			return;
		}

		this.dropErosionLoot(level, pos, state);
		if (random.nextInt(0, 5) == 0) {
			level.removeBlock(pos, false);
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED)
			&& !this.isDead(state)
			&& !(state.getBlock() instanceof BaseCoralWallFanBlock);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return level.getBlockState(pos.above()).getBlock() == Blocks.WATER && random.nextInt(0, 5) == 0;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		final var resourceKey = AquaticFeatures.WARM_OCEAN_VEGETATION;

		Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = level.registryAccess()
			.registryOrThrow(Registries.CONFIGURED_FEATURE)
			.getHolder(resourceKey);

		if (optional.isEmpty()) {
			Recharged.LOGGER.warn("Large coral " + resourceKey + " not found");
			return;
		}

		level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
		if (!optional.get().value().place(level, level.getChunkSource().getGenerator(), random, pos)) {
			level.setBlock(pos, state, 3);
		}
	}

	private boolean isDead(BlockState state) {
		return state.is(RechargedTags.Blocks.DEAD_CORALS);
	}

	private void dropErosionLoot(ServerLevel level, BlockPos pos, BlockState state) {
		var erosionLoot = level
			.getServer()
			.reloadableRegistries()
			.getLootTable(RechargedMiscLoot.CORAL_EROSION);

		var erosionParams = new LootParams.Builder(level)
			.withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
			.withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
			.withParameter(LootContextParams.BLOCK_STATE, state)
			.create(LootContextParamSets.BLOCK);

		for (var item : erosionLoot.getRandomItems(erosionParams)) {
			popResource(level, pos, item);
		}
	}
}
