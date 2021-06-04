package com.nord.rest.retry.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsumerRequest {

    @JsonProperty
    private int id;

    @JsonProperty
    private String type;

    @Override
    public String toString() {
        return "ConsumerRequest{" +
                "id=" + id +
                ", type='" + type + '\'' +
                "}";
    }
}
