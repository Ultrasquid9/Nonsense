package uwu.juni.recharged;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class RechargedConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue DISABLE_QUASI_CONNECTIVITY = BUILDER
		.comment(" Whether or not Quasi Connectivity should be disabled")
		.define("disable_quasi_connectivity", true);

	public static final ModConfigSpec.BooleanValue FIX_PORTAL_BREAK = BUILDER
		.comment(" Whether or not methods to break indestructible blocks (like portals) should be patched")
		.define("fix_portal_break", true);

	public static final ModConfigSpec.BooleanValue PISTONS_BREAK_CARPETS = BUILDER
		.gameRestart()	
		.comment(" Whether or not pistons should be able to break carpets")
		.define("pistons_break_carpets", true);

	public static final ModConfigSpec SPEC = BUILDER.build();

	public static <T> T getConfigValue(ConfigValue<T> value) {
		T val;

		try {
			val = value.get();
		} catch (Exception e) {
			val = value.getDefault();
		}

		return val;
	}
}
