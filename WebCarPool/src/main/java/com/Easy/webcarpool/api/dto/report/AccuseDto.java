package com.Easy.webcarpool.api.dto.report;

import com.Easy.webcarpool.api.domain.entity.AccuseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccuseDto {
    private String accuse_type;
    private String accused_nickname;
    private String accuser_nickname;
    private String accuse_content;

    public AccuseEntity toEntity(){
        return AccuseEntity.builder()
                .accuse_type(accuse_type)
                .accused_nickname(accused_nickname)
                .accuser_nickname(accuser_nickname)
                .accuse_content(accuse_content)
                .build();
    }

}
