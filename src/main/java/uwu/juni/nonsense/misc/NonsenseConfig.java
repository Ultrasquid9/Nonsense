package uwu.juni.nonsense.misc;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NonsenseConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue DISABLE_QUASI_CONNECTIVITY = BUILDER
		.comment("Whether or not Quasi Connectivity should be disabled")
		.define("disable_quasi_connectivity", true);

	public static final ModConfigSpec.BooleanValue MOD_ONLY_DEBUG_SCREEN = BUILDER
		.comment("Whether or not the debug screen should be restricted to players with permission level 1 or above")
		.define("mod_only_debug_screen", true);

	public static final ModConfigSpec SPEC = BUILDER.build();

	public static boolean getConfigValue(ModConfigSpec.BooleanValue value) {
		boolean should;

		try {
			should = value.getAsBoolean();
		} catch (Exception e) {
			should = true;
		}

		return should;
	}
}
