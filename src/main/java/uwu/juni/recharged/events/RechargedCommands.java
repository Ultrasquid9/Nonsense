package uwu.juni.recharged.events;

import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class RechargedCommands {
	@SubscribeEvent
	static void customCommands(RegisterCommandsEvent event) {
		var dispatcher = event.getDispatcher();

		dispatcher.register(Commands
			.literal("hunger")
			.requires(e -> e.hasPermission(2))
			.then(Commands
				.argument("amount", IntegerArgumentType.integer(0, 20))
				.executes(command -> {
					var player = command.getSource().getPlayerOrException();
					var amount = IntegerArgumentType.getInteger(command, "amount");

					player.getFoodData().setFoodLevel(amount);

					return 0;
				})
				.then(Commands
					.argument("targets", EntityArgument.players())
					.executes(command -> {
						var players = EntityArgument.getPlayers(command, "targets");
						var amount = IntegerArgumentType.getInteger(command, "amount");

						for (var player : players) {
							player.getFoodData().setFoodLevel(amount);
						}
						
						return 0;
					})				
				)
			)
		);
	}
}
