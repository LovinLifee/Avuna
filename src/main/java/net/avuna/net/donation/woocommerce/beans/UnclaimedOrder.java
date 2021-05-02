
package net.avuna.net.donation.woocommerce.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class UnclaimedOrder {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("parent_id")
    private Integer parentId;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("total")
    private String total;

    @Expose
    @SerializedName("total_tax")
    private String totalTax;

    @Expose
    @SerializedName("customer_id")
    private Integer customerId;

    @Expose
    @SerializedName("payment_method")
    private String paymentMethod;

    @Expose
    @SerializedName("payment_method_title")
    private String paymentMethodTitle;

    @Expose
    @SerializedName("items")
    private List<Item> items = null;
}
