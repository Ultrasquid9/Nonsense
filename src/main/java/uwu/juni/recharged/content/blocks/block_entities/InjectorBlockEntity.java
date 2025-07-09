package uwu.juni.recharged.content.blocks.block_entities;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.ticks.ContainerSingleItem;
import uwu.juni.recharged.content.RechargedBlockEntities;
import uwu.juni.recharged.content.blocks.InjectorBlock;

@ParametersAreNonnullByDefault
public class InjectorBlockEntity extends BlockEntity implements RandomizableContainer, ContainerSingleItem.BlockContainerSingleItem {
	public static final String TAG_ITEM = "item";
	private ItemStack item = ItemStack.EMPTY;
	private Direction facing;
	private int cooldownTime = -1;
	
	protected long lootTableSeed;
	protected ResourceKey<LootTable> lootTable;
	
	public InjectorBlockEntity(BlockPos pos, BlockState state) {
		super(RechargedBlockEntities.INJECTOR.get(), pos, state);
		facing = state.getValue(InjectorBlock.FACING);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag) && !this.item.isEmpty()) {
			tag.put("item", this.item.save(registries));
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, Provider registries) {
		super.loadAdditional(tag, registries);
		if (!this.tryLoadLootTable(tag)) {
			if (tag.contains("item", 10)) {
				this.item = ItemStack.parse(registries, tag.getCompound("item")).orElse(ItemStack.EMPTY);
			} else {
				this.item = ItemStack.EMPTY;
			}
		}
	}

	public static void onTick(
		Level level,
		BlockPos pos,
		BlockState state,
		InjectorBlockEntity blockEntity
	) {
		blockEntity.onTick(level, pos, state);
	}

	private void onTick(Level level, BlockPos pos, BlockState state) {
		this.cooldownTime--;

		if (!this.isOnCooldown()) {
			this.cooldownTime = 0;
			this.injectItems(level, pos);
		}
	}

	private boolean injectItems(Level level, BlockPos pos) {
		var container = getContainerAt(level, pos.relative(Direction.DOWN));
		if (!isContainerValid(container) || container == null) {
			return this.passToNext(level, pos);
		}

		if (!this.insertIntoContainer(container)) {
			return this.passToNext(level, pos);
		}

		return insertIntoContainer(container);
	}

	private boolean passToNext(Level level, BlockPos pos) {
		var blockEntity = level.getBlockEntity(pos.relative(this.facing.getOpposite()));
		
		if (blockEntity instanceof InjectorBlockEntity injector && injector.facing == this.facing) {
			if (!isContainerValid(injector)) {
				return false;
			}

			return insertIntoContainer(injector);
		}

		return false;
	}

	private boolean isContainerValid(@Nullable Container container) {
		if (container == null) {
			return false;
		}

		if (isContainerFull(container)) {
			return false;
		}

		return true;
	}

	private boolean insertIntoContainer(Container container) {
		for (var i = 0; i < this.getContainerSize(); i++) {
			var itemstack = this.getItem(i);
			if (itemstack.isEmpty()) {
				return false;
			}

			var count = itemstack.getCount();

			var itemstack1 = addItem(this, container, this.removeItem(i, 1), this.facing.getOpposite());
			if (itemstack1.isEmpty()) {
				container.setChanged();
				return true;
			}

			itemstack.setCount(count);
			if (count == 1) {
				this.setItem(i, itemstack);
			}
		}

		return false;
	}

	public static ItemStack addItem(@Nullable Container source, Container destination, ItemStack stack, @Nullable Direction direction) {
		if (destination instanceof WorldlyContainer worldlycontainer && direction != null) {
			int[] aint = worldlycontainer.getSlotsForFace(direction);

			for (int k = 0; k < aint.length && !stack.isEmpty(); k++) {
				stack = tryMoveInItem(source, destination, stack, aint[k], direction);
			}

			return stack;
		}

		int i = destination.getContainerSize();

		for (int j = 0; j < i && !stack.isEmpty(); j++) {
			stack = tryMoveInItem(source, destination, stack, j, direction);
		}

		return stack;
	}

	private static ItemStack tryMoveInItem(@Nullable Container source, Container destination, ItemStack stack, int slot, @Nullable Direction direction) {
		ItemStack itemstack = destination.getItem(slot);
		if (canPlaceItemInContainer(destination, stack, slot, direction)) {
			boolean flag = false;

			if (itemstack.isEmpty()) {
				destination.setItem(slot, stack);
				stack = ItemStack.EMPTY;
				flag = true;
			} else if (canMergeItems(itemstack, stack)) {
				int i = stack.getMaxStackSize() - itemstack.getCount();
				int j = Math.min(stack.getCount(), i);
				stack.shrink(j);
				itemstack.grow(j);
				flag = j > 0;
			}

			if (flag) {
				destination.setChanged();
			}
		}

		return stack;
	}

	private static boolean canPlaceItemInContainer(Container container, ItemStack stack, int slot, @Nullable Direction direction) {
		if (!container.canPlaceItem(slot, stack)) {
			return false;
		} else {
			if (container instanceof WorldlyContainer worldlycontainer && !worldlycontainer.canPlaceItemThroughFace(slot, stack, direction)) {
				return false;
			}

			return true;
		}
	}

	private static boolean canMergeItems(ItemStack stack1, ItemStack stack2) {
		return stack1.getCount() <= stack1.getMaxStackSize() && ItemStack.isSameItemSameComponents(stack1, stack2);
	}

	private static boolean isContainerFull(Container container) {
		int[] aint = getSlots(container);

		for (int i : aint) {
			ItemStack itemstack = container.getItem(i);
			if (itemstack.getCount() < itemstack.getMaxStackSize()) {
				return false;
			}
		}

		return true;
	}

	private static int[] getSlots(Container container) {
		if (container instanceof WorldlyContainer worldlycontainer) {
			return worldlycontainer.getSlotsForFace(Direction.DOWN);
		} else {
			return createFlatSlots(container.getContainerSize());
		}
	}

	private static int[] createFlatSlots(int size) {
		int[] aint = new int[size];
		int i = 0;

		while (i < aint.length) {
			aint[i] = i++;
		}

		return aint;
	}

	@Nullable
	private static Container getContainerAt(Level level, BlockPos pos) {
		var state = level.getBlockState(pos);
		var container = getBlockContainer(level, pos, state);
		
		if (container == null) {
			container = getEntityContainer(level, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5);
		}

		return container;
	}

	@Nullable
	private static Container getBlockContainer(Level level, BlockPos pos, BlockState state) {
		Block block = state.getBlock();
		if (block instanceof WorldlyContainerHolder) {
			return ((WorldlyContainerHolder)block).getContainer(state, level, pos);
		} else if (state.hasBlockEntity() && level.getBlockEntity(pos) instanceof Container container) {
			if (container instanceof ChestBlockEntity && block instanceof ChestBlock) {
				container = ChestBlock.getContainer((ChestBlock)block, state, level, pos, true);
			}

			return container;
		} else {
			return null;
		}
	}

	@Nullable
	private static Container getEntityContainer(Level level, double x, double y, double z) {
		List<Entity> list = level.getEntities(
			(Entity)null,
			new AABB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5),
			EntitySelector.CONTAINER_ENTITY_SELECTOR
		);
		return !list.isEmpty() ? (Container)list.get(level.random.nextInt(list.size())) : null;
	}

	boolean isOnCooldown() {
		return this.cooldownTime > 0;
	}

	@Override
	@Nullable
	public ResourceKey<LootTable> getLootTable() {
		return this.lootTable;
	}

	@Override
	public void setTheItem(ItemStack item) {
		this.unpackLootTable(null);
		this.item = item;
	}

	@Override
	public ItemStack getTheItem() {
		this.unpackLootTable(null);
		return this.item;
	}

	@Override
	public void setLootTable(@Nullable ResourceKey<LootTable> lootTable) {
		this.lootTable = lootTable;
	}

	@Override
	public void setLootTableSeed(long seed) {
		this.lootTableSeed = seed;
	}

	@Override
	public long getLootTableSeed() {
		return this.lootTableSeed;
	}

	@Override
	public BlockEntity getContainerBlockEntity() {
		return this;
	}
}
