package uwu.juni.recharged.misc;

import net.neoforged.neoforge.common.ModConfigSpec;

public class RechargedConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue DISABLE_QUASI_CONNECTIVITY = BUILDER
		.comment("Whether or not Quasi Connectivity should be disabled")
		.define("disable_quasi_connectivity", true);

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
