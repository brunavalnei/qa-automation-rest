package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Customer {

    private String endpointBase;
    public String getEndpointBase() {
        return endpointBase;
    }

    public void setEndpointBase(String endpointBase) {
        this.endpointBase = endpointBase;
    }




}
