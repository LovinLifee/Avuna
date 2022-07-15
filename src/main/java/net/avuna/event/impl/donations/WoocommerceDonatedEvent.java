package net.avuna.event.impl.donations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.event.AbstractEvent;
import net.avuna.event.net.donation.woocommerce.beans.WoocommerceData;

@Getter
@RequiredArgsConstructor
public class WoocommerceDonatedEvent extends AbstractEvent {

    private final String username;
    private final WoocommerceData data;
}
