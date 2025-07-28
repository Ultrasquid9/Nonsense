package uwu.juni.recharged.content;

import java.util.function.Supplier;

import com.mojang.datafixers.DSL;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.blocks.block_entities.InjectorBlockEntity;
import uwu.juni.recharged.content.blocks.block_entities.LatchBlockEntity;
import uwu.juni.recharged.content.blocks.block_entities.ResistorBlockEntity;
import uwu.juni.recharged.content.blocks.block_entities.ShulkerTendonBlockEntity;

public class RechargedBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(
		Registries.BLOCK_ENTITY_TYPE, 
		Recharged.MODID
	);

	public static final Supplier<BlockEntityType<InjectorBlockEntity>> INJECTOR = REGISTER.register(
		"injector",
		() -> BlockEntityType.Builder.of(
			InjectorBlockEntity::new,
			RechargedBlocks.INJECTOR.get()
		)
		.build(DSL.emptyPartType())
	);

	public static final Supplier<BlockEntityType<LatchBlockEntity>> LATCH = REGISTER.register(
		"latch",
		() -> BlockEntityType.Builder.of(
			LatchBlockEntity::new,
			RechargedBlocks.LATCH.get()
		)
		.build(DSL.emptyPartType())
	);

	public static final Supplier<BlockEntityType<ResistorBlockEntity>> RESISTOR = REGISTER.register(
		"resistor",
		() -> BlockEntityType.Builder.of(
			ResistorBlockEntity::new,
			RechargedBlocks.RESISTOR.get()
		)
		.build(DSL.emptyPartType())
	);

	public static final Supplier<BlockEntityType<ShulkerTendonBlockEntity>> SHULKER_TENDON = REGISTER.register(
		"shulker_tendon",
		() -> BlockEntityType.Builder.of(
			ShulkerTendonBlockEntity::new,
			RechargedBlocks.SHULKER_TENDON.get()
		)
		.build(DSL.emptyPartType())
	);
}
