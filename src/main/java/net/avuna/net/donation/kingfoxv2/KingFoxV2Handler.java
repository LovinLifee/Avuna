package net.avuna.net.donation.kingfoxv2;

import lombok.Getter;
import net.avuna.Avuna;
import net.avuna.config.Configurable;
import net.avuna.event.impl.donations.PlayerDonatedEvent;
import net.avuna.net.donation.DonationStrategy;
import net.avuna.net.donation.Transaction;
import pro.husk.mysql.MySQL;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/*
	TODO: make functions async
 */
public class KingFoxV2Handler implements DonationStrategy, Configurable<KingFoxConfig> {

	private static final String SELECT_QUERY = "SELECT * FROM `payments` WHERE player_name='%s' AND claimed=0 AND status='completed'";
	private static final String UPDATE_QUERY = "UPDATE `payments` SET `claimed` = '1' WHERE `payments`.`id` = %d;";
	private static final String GET_ITEM_ID = "SELECT `item_id` from `products` WHERE `products`.`id` = %d";

	@Getter
	private final KingFoxConfig config;

	public KingFoxV2Handler(KingFoxConfig config) {
		this.config = config;
	}

	/**
	 * @param playerName
	 * @throws SQLException
	 */
	public void checkDonations(String playerName) {
		try {
			final MySQL mysql = new MySQL(config.getDbHost(), String.valueOf(config.getDbPort()), config.getDatabase(), config.getDbUsername(), config.getDbPassword(), "");
			mysql.query(String.format(SELECT_QUERY, playerName), e -> {
				while(e.next()) {
					String username = e.getString("player_name");
					int transactionId = e.getInt("id");
					String itemName = e.getString("item_name");
					int productId = e.getInt("item_number");
					int itemAmount = e.getInt("quantity");
					//double price = e.getDouble("amount"); V2
					double price = e.getDouble("paid"); //V3
					Transaction transaction = new Transaction(username, itemName, productId, itemAmount, price);
					PlayerDonatedEvent event = new PlayerDonatedEvent(transaction);
					Avuna.getEventManager().submit(event);
					if(!event.isCancelled() && event.isConsumed()) {
						mysql.update(String.format(UPDATE_QUERY, transactionId));
					}
				}
			});
			mysql.closeConnection();
		} catch(SQLException e) {
			e.printStackTrace();
		}
    }

    public int getItemIdByProductId(int productId) throws SQLException {
		final MySQL mysql = new MySQL(config.getDbHost(), String.valueOf(config.getDbPort()), config.getDatabase(), config.getDbUsername(), config.getDbPassword(), "");
		final AtomicInteger itemId = new AtomicInteger(0);
		mysql.query(String.format(GET_ITEM_ID, productId), r -> {
			while(r.next()) {
				itemId.set(r.getInt("item_id"));
			}
		});
		mysql.closeConnection();
		return itemId.get();
	}
}
