package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SquareOffRequestModel {
    @JsonProperty("positionSquareOffRequestList")
    private List<PositionSquareOffRequestList> positionSquareOffRequestList;

    @Override
    public String toString() {
        return "SquareOffRequestModel{" +
                "positionSquareOffRequestList=" + positionSquareOffRequestList +
                '}';
    }
}
