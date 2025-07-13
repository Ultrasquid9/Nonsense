package uwu.juni.recharged.misc;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.content.blocks.ResistorBlock;

@EventBusSubscriber
public class RechargedEvents {
	@SubscribeEvent
	public static void blockColors(RegisterColorHandlersEvent.Block event) {
		event.register(
			ResistorBlock::color,
			RechargedBlocks.RESISTOR.get()
		);
	}
}
