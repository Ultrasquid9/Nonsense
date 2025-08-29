package uwu.juni.recharged.codecs.extras;

import java.util.function.Function;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;

public class MaybeRandomInt {
	private Either<Integer, Range> num;

	public static final Codec<MaybeRandomInt> CODEC = Codec.either(
		ExtraCodecs.POSITIVE_INT,
		Range.CODEC
	)
	.xmap(
		MaybeRandomInt::new,
		i -> i.num
	);

	public MaybeRandomInt(int num) {
		this.num = Either.left(num);
	}

	public MaybeRandomInt(Either<Integer, Range> num) {
		this.num = num;
	}

	public int get(RandomSource random) {
		return this.num.map(
			Function.identity(),
			range -> range.get(random)
		);
	}

	record Range(int min, int max) {
		public static final Codec<Range> CODEC = RecordCodecBuilder.<Range>create(
			instance -> instance.group(
				ExtraCodecs.POSITIVE_INT.fieldOf("min").forGetter(Range::min),
				ExtraCodecs.POSITIVE_INT.fieldOf("max").forGetter(Range::max)
			)
			.apply(instance, Range::new)
		)
		.validate((Range range) -> {
			if (range.min >= range.max) {
				return DataResult.error(() -> range.min + " is larger than or equal to " + range.max);
			} else {
				return DataResult.success(range);
			}
		});

		int get(RandomSource random) {
			return random.nextInt(this.min, this.max);
		}
	}
}
