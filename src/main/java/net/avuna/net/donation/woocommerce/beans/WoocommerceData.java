
package net.avuna.net.donation.woocommerce.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class WoocommerceData {

    @Expose
    @SerializedName("unclaimed_orders")
    private List<UnclaimedOrder> unclaimedOrders = null;

}
