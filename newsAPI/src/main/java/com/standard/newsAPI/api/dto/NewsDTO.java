package com.standard.newsAPI.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NewsDTO {

    @Valid
    @NotNull
    private String title;

    @Valid
    @NotNull
    private String content;

    @Valid
    @NotNull
    private long category;
}
