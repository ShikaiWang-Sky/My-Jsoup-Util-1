package com.wang.util;

import com.wang.pojo.TMContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TMHtmlParseUtil {
    public List<TMContent> parseTM(String keywords) throws IOException {
        //关键字转码
        String keyword = URLEncoder.encode(keywords, "UTF-8");
        //拼接URL
        String url = "https://list.tmall.com/search_product.htm?q=" + keyword;
        //TODO 找到淘宝的翻页请求...

        //解析网页
        Document document = Jsoup.parse(new URL(url), Constant.TMTimeout);
        //获取元素
        Element element = document.getElementById("J_ItemList");
        Elements elements = element.getElementsByClass("product  ");

        //存放爬取数据
        List<TMContent> goodList = new ArrayList<>();

        //遍历出内容
        for (Element el : elements) {
            //图片 ==> 天猫用的是懒加载, 如果使用src属性爬取, 只能加载第一行! 在此处找到了标签 http://blog.itpub.net/28916011/viewspace-2659548/
            String img = el.getElementsByTag("img").eq(0).attr("data-ks-lazyload");
            //第一行不是懒加载, 如果使用懒加载获取不到, 我们就正常获取
            if (img.length() == 0) {
                img = el.getElementsByTag("img").eq(0).attr("src");
            }
            //标题
            String title = el.getElementsByClass("productTitle").text();
            //价格
            String price = null;
            Elements productPrice = el.getElementsByClass("productPrice").eq(0);
            for (Element tags : productPrice) {
                price = tags.getElementsByTag("em").eq(0).attr("title");
            }
            //店铺名
            String shopName = el.getElementsByClass("productShop").eq(0).text();
            //链接
            String link = null;
            Elements productTitle = el.getElementsByClass("productTitle").eq(0);
            for (Element tags : productTitle) {
                link = tags.getElementsByTag("a").eq(0).attr("href");
            }
            TMContent content = new TMContent();
            content.setImg(img + "\n")
                    .setPrice(price + "\n")
                    .setTitle(title + "\n")
                    .setShopName(shopName + "\n")
                    .setLink(link);
            ;
            //放入List
            goodList.add(content);
        }
        return goodList;

    }
}
