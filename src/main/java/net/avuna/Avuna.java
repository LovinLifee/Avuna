package net.avuna;

import lombok.Getter;
import net.avuna.commands.CommandHandler;
import net.avuna.event.EventManager;
import net.avuna.event.net.donation.DonationStrategy;
import net.avuna.event.net.donation.MasterDonationConfig;
import net.avuna.game.entity.npc.NpcHandler;
import net.avuna.game.entity.player.PlayerHandler;
import net.avuna.game.items.drops.DropConfig;
import net.avuna.game.items.drops.DropHandler;
import net.avuna.game.trivia.TriviaConfig;
import net.avuna.game.trivia.TriviaHandler;
import net.avuna.event.net.donation.kingfoxv2.KingFoxConfig;
import net.avuna.event.net.donation.kingfoxv2.KingFoxV2Handler;
import net.avuna.event.net.donation.woocommerce.WoocommerceConfig;
import net.avuna.event.net.donation.woocommerce.WoocommerceHandler;
import net.avuna.event.net.rest.RestEndpoint;
import net.avuna.event.net.rest.RestEndpointConfig;
import net.avuna.event.net.vote.VoteConfig;
import net.avuna.event.net.vote.VoteHandler;
import net.avuna.tasks.security.ConfirmationModule;
import net.avuna.tasks.security.punishments.BanHandler;
import net.avuna.tasks.security.punishments.MuteHandler;
import net.avuna.tasks.TaskManager;
import spark.Spark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Avuna {

	@Getter
	private static final Path dataDir = Paths.get("./avuna/");

	@Getter
	private static final Path configDir = dataDir.resolve("configs/");

	@Getter
	private static final TriviaHandler triviaHandler = new TriviaHandler(new TriviaConfig(configDir.resolve("trivia-config.json"), true));

	@Getter
	private static final CommandHandler commandHandler = new CommandHandler();

	@Getter
	private static final EventManager eventManager = new EventManager();

	@Getter
	private static final DropHandler dropHandler = new DropHandler(new DropConfig(configDir.resolve("npc-drops.json"), true));

	@Getter
	private static final RestEndpoint restEndpoint = new RestEndpoint(new RestEndpointConfig(configDir.resolve("rest-api.json"), true));

	@Getter
	private static final TaskManager taskManager = new TaskManager();

	private static DonationStrategy donationHandler = getDonationHandler();

	public static DonationStrategy getDonationHandler() {
		Person person = new Person("hi", 5);
		if(donationHandler == null) {
			MasterDonationConfig config = new MasterDonationConfig(configDir.resolve("master-donation-config.json"), true);
			config.load();
			donationHandler = switch(config.getDonationGateway()) {
				case "woocommerce" -> new WoocommerceHandler(new WoocommerceConfig(configDir.resolve("woocommerce-donation-config.json"), true));
				case "kingfox" -> new KingFoxV2Handler(new KingFoxConfig(configDir.resolve("kingfox-donation-config.json"), true));
				default -> throw new IllegalStateException("Unexpected value: " + config.getDonationGateway());
			};
		}
		return donationHandler;
	}

	@Getter
	private static final PlayerHandler players = new PlayerHandler();

	@Getter
	private static final NpcHandler npcs = new NpcHandler();

	@Getter
	private static final ConfirmationModule confirmationModule = new ConfirmationModule();

	@Getter
	private static final BanHandler banHandler = new BanHandler();

	@Getter
	private static final MuteHandler muteHandler = new MuteHandler();

	@Getter
	private static final VoteHandler voteHandler = new VoteHandler(new VoteConfig(configDir.resolve("vote-config.json"), true));

	public static void bootstrap() {
		//creating default directory structure
		try {
			if(!Files.exists(configDir)) {
				Files.createDirectories(configDir);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//loading default configs
		dropHandler.getConfig().load();
		voteHandler.getConfig().load();
		triviaHandler.getConfig().load();
		donationHandler.getConfig().load();
		restEndpoint.getConfig().load();

		//registering command handlers

		commandHandler.registerCommandListener(new AvunaCommands());
		commandHandler.registerCommandListener(confirmationModule);
		commandHandler.registerCommandListener(banHandler);
		commandHandler.registerCommandListener(muteHandler);
		commandHandler.registerCommandListener(triviaHandler);

		//registering event handlers
		eventManager.registerEventListener(banHandler);
		eventManager.registerEventListener(muteHandler);


		taskManager.schedule(triviaHandler);
		restEndpoint.init();
	}

	public static void shutdown() {
		Spark.stop();
		taskManager.getExecutorService().shutdown();
	}

	public static void main(String[] args) {
		bootstrap();
		System.out.println(Avuna.getRestEndpoint().getConfig().getAuthUsername());
	}
}
