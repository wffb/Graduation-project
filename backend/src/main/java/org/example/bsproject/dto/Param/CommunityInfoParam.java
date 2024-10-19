package org.example.bsproject.dto.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityInfoParam {

    private String name;

    private Integer floors;

    private Integer numOfPeople;

    private String extra;
}
