package com.Easy.webcarpool.api.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String report_title;

    @Column(nullable = false)
    private String report_content;

    @Column(nullable = false)
    private String report_user_email;

}
