package net.avuna.event.net.vote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VoteData {

    @JsonProperty("id")
    private long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("vote_key")
    private String voteKey;

    @JsonProperty("site_id")
    private long siteId;

    @JsonProperty("voted_on")
    private long votedOn;

    @JsonProperty("started_on")
    private long startedOn;

    @JsonProperty("claimed")
    private long claimed;
}