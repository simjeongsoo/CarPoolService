package com.Easy.webcarpool.api.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostDistrictDto {

    private String postType;
    private String district;

}
