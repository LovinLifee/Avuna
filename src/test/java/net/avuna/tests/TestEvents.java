package net.avuna.tests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.Avuna;
import net.avuna.event.AbstractEvent;
import net.avuna.util.Priority;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class TestEvents {

	static {
		Avuna.bootstrap();
	}
	
	@Test
	public void testRegisterEvent() {
		/*Avuna.getEventManager().registerEventListener(MockEvent.class, e -> {
			
		}, Priority.NORMAL);
		assertFalse(Avuna.getEventManager().getListeners().get(MockEvent.class).isEmpty());*/
	}
	
	@Test
	public void testEventCancelled() {
		/*Avuna.getEventManager().registerEventListener(MockEvent.class, e -> {
			if(e.getMessage().equals("hello world")) {
				e.cancel();
			}
		}, Priority.NORMAL);
		MockEvent event = new MockEvent("hello world");
		Avuna.getEventManager().submit(event);
		assertTrue(event.isCancelled());*/
	}
	
	@Test
	public void testEventCancelled2() {
		Avuna.getEventManager().registerEventListener(MockEvent.class, e -> {
			if(e.getMessage().equals("hello world")) {
				e.setCancelled(true);
			}
		}, Priority.NORMAL);
		MockEvent event = new MockEvent("look at all these tickens");
		Avuna.getEventManager().submit(event);
		assertFalse(event.isCancelled());
	}
	
	@Test
	public void testEvents() {
		/*MockPlayer player1 = new MockPlayer("hayden");
		MockPlayer player2 = new MockPlayer("colby");
		Avuna.getEventManager().registerEventListener(new EventType<RPSLSWinEvent<MockPlayer>>() {}, e -> {
			String msg = String.format("%s(%s) beat %s(%s)", e.getWinner().getUsername(), e.getWinningMove().getName(), e.getLoser().getUsername(), e.getLosingMove().getName());
			assertEquals(msg, "colby(ROCK) beat hayden(LIZARD)");
		}, Priority.NORMAL);
		RPSLS.battle(player1, RPSLS.LIZARD, player2, RPSLS.ROCK);*/
	}

	@Getter
	@RequiredArgsConstructor
	private static class MockEvent extends AbstractEvent {
		private final String message;
	}
}