package net.dg.newsscrapingapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class News {

    private String title;
    private String url;
    private String imgSrc;
    private String source;

}
