package com.Easy.webcarpool.api.dto.post;

import com.Easy.webcarpool.api.domain.entity.PostReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostReviewDto {

    private Long postId;    // reserved_post_entity에서 column삭제를 위해 사용 -> database테이블에 저장X
    private String postType;    // reserved_post_entity에서 column삭제를 위해 사용 -> database테이블에 저장X
    private String host_email;
    private String host_nickname;
    private String writer_email;
    private String writer_nickname;
    private Float rate;
    private String title;
    private String content;

    public PostReviewEntity toEntity(){
        return PostReviewEntity.builder()
                .host_email(host_email)
                .host_nickname(host_nickname)
                .writer_email(writer_email)
                .writer_nickname(writer_nickname)
                .rate(rate)
                .title(title)
                .content(content)
                .build();
    }
}
