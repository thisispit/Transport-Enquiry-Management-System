package com.thisispit.tems.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long routeId;
    private String routeName;
    private int rating;
    private String comments;
    private LocalDateTime createdAt;
}