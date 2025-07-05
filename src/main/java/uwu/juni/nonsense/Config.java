package uwu.juni.nonsense;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue DISABLE_QUASI_CONNECTIVITY = BUILDER
		.comment("Whether or not Quasi Connectivity should be disabled")
		.define("disable_quasi_connectivity", true);

	static final ModConfigSpec SPEC = BUILDER.build();
}
