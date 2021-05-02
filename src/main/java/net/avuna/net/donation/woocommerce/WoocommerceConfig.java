package net.avuna.net.donation.woocommerce;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;

@Getter
public class WoocommerceConfig extends Config {

    private String domain;
    private String consumerKey;
    private String consumerSecret;
    private boolean autoClaim;

    public WoocommerceConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}