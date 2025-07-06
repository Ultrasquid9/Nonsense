package uwu.juni.nonsense.content;

import java.util.function.Supplier;

import com.mojang.datafixers.DSL;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.nonsense.Nonsense;
import uwu.juni.nonsense.content.blocks.block_entities.InjectorBlockEntity;

public class NonsenseBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(
		Registries.BLOCK_ENTITY_TYPE, 
		Nonsense.MODID
	);

	public static final Supplier<BlockEntityType<InjectorBlockEntity>> INJECTOR = REGISTER.register(
		"null",
		() -> BlockEntityType.Builder.of(
			InjectorBlockEntity::new,
			NonsenseBlocks.INJECTOR.get()
		)
		.build(DSL.emptyPartType())
	);
}
