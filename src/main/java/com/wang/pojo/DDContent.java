package com.wang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DDContent {
    private String img;
    private String price;
    private String title;
    private String shopName;
    //电子书
    private String EBook;
    private String link;
}
