package net.avuna.net.vote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class VoteData {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("ip_address")
    @Expose
    private String ipAddress;

    @SerializedName("vote_key")
    @Expose
    private String voteKey;

    @SerializedName("site_id")
    @Expose
    private long siteId;

    @SerializedName("voted_on")
    @Expose
    private long votedOn;

    @SerializedName("started_on")
    @Expose
    private long startedOn;

    @SerializedName("claimed")
    @Expose
    private long claimed;
}