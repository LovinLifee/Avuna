package net.avuna.event.impl.donations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.event.AbstractEvent;
import net.avuna.net.donation.Transaction;

@Getter
@RequiredArgsConstructor
public class PlayerDonatedEvent extends AbstractEvent {
	private final Transaction transaction;
}
