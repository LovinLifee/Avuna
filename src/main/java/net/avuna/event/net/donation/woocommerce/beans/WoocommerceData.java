
package net.avuna.event.net.donation.woocommerce.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class WoocommerceData {

    @JsonProperty("unclaimed_orders")
    private List<UnclaimedOrder> unclaimedOrders = null;
}
