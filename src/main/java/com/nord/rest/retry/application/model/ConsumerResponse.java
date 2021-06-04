package com.nord.rest.retry.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsumerResponse {

    @JsonProperty
    private int id;

    @JsonProperty
    private String content;

    @Override
    public String toString() {
        return "ConsumerResponse{" +
                "id=" + id +
                ", type='" + content + '\'' +
                '}';
    }
}
