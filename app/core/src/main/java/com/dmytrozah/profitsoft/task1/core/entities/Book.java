package com.dmytrozah.profitsoft.task1.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Book(@JsonProperty("title") String title,
                   @JsonProperty("author") String author,
                   @JsonProperty("year_published") int yearPublished)
{ }
