package net.avuna.event.impl.donations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.event.AbstractEvent;
import net.avuna.net.donation.woocommerce.beans.UnclaimedOrder;
import net.avuna.net.donation.woocommerce.beans.WoocommerceData;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WoocommerceDonatedEvent extends AbstractEvent {

    private final String username;
    private final WoocommerceData data;
}
