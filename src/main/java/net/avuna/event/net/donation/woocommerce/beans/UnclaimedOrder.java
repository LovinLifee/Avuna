
package net.avuna.event.net.donation.woocommerce.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class UnclaimedOrder {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("total")
    private String total;

    @JsonProperty("total_tax")
    private String totalTax;

    @JsonProperty("customer_id")
    private Integer customerId;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_method_title")
    private String paymentMethodTitle;

    @JsonProperty("items")
    private List<Item> items = null;
}
