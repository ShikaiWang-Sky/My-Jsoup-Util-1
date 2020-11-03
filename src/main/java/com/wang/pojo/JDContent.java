package com.wang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JDContent {
    private String img;
    private String price;
    private String title;
    private String shopName;
    //JD 的标签是是否自营
    private String label;
    private String link;
}
