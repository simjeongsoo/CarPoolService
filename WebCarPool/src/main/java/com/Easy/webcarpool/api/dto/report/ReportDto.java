package com.Easy.webcarpool.api.dto.report;

import com.Easy.webcarpool.api.domain.entity.ReportEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReportDto {

    private String report_title;
    private String report_content;
    private String report_user_email;

    public ReportEntity toEntity(){
        return ReportEntity.builder()
                .report_title(report_title)
                .report_content(report_content)
                .report_user_email(report_user_email)
                .build();
    }
}
