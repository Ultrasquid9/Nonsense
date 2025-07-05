package uwu.juni.nonsense;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.nonsense.content.NonsenseBlocks;
import uwu.juni.nonsense.content.NonsenseItems;
import uwu.juni.nonsense.misc.NonsenseConfig;
import uwu.juni.nonsense.misc.NonsenseCreative;

@Mod(Nonsense.MODID)
public class Nonsense {
	public static final String MODID = "nonsense";
	public static final Logger LOGGER = LogUtils.getLogger();

	static final DeferredRegister<?>[] REGISTERS = {
		NonsenseBlocks.REGISTER,
		NonsenseItems.REGISTER,
	};

	public Nonsense(IEventBus bussin, ModContainer modContainer) {
		for (var register : REGISTERS) {
			register.register(bussin);
		}

		bussin.addListener(NonsenseCreative::addToCreative);
		modContainer.registerConfig(ModConfig.Type.COMMON, NonsenseConfig.SPEC);
	}
}
