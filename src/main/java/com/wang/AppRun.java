package com.wang;

import com.wang.pojo.DDContent;
import com.wang.pojo.JDContent;
import com.wang.pojo.TMContent;
import com.wang.util.DDHtmlParseUtil;
import com.wang.util.JDHtmlParseUtil;
import com.wang.util.TMHtmlParseUtil;

import java.io.IOException;
import java.util.List;

public class AppRun {
    public static void main(String[] args) throws IOException {
//        JDHtmlParseUtil jdHtmlParseUtil = new JDHtmlParseUtil();
//        List<JDContent> jdContents = jdHtmlParseUtil.parseJD("C++");
//        for (JDContent jdContent : jdContents) {
//            System.out.println(jdContent);
//        }

//        TMHtmlParseUtil tmHtmlParseUtil = new TMHtmlParseUtil();
//        List<TMContent> tmContents = tmHtmlParseUtil.parseTM("C语言");
//        for (TMContent tmContent : tmContents) {
//            System.out.println(tmContent);
//        }

        DDHtmlParseUtil ddHtmlParseUtil = new DDHtmlParseUtil();
        List<DDContent> ddContents = ddHtmlParseUtil.parseDD("C语言");
        for (DDContent ddContent : ddContents) {
            System.out.println(ddContent);
        }
    }
}
