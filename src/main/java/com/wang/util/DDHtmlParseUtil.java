package com.wang.util;

import com.wang.pojo.DDContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DDHtmlParseUtil {
    public List<DDContent> parseDD(String keywords) throws IOException {
        //关键字转码
        String keyword = URLEncoder.encode(keywords, "UTF-8");
        //拼接URL
        String url = "http://search.dangdang.com/?key=" + keyword + "&act=input";
        //爬取前5页, 当当为page_index编号(从1开始)
        List<String> urlList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            String pageNum = "&page_index=" + page;
            urlList.add(url + pageNum);
        }

        //存放每个页面的解析结果
        ArrayList<Elements> elementsList = new ArrayList<>();
        for (String singleUrl : urlList) {
            //解析网页, 当当使用GBK编码
            Document document = Jsoup.parse(new URL(singleUrl).openStream(), "GBK", singleUrl);
            //获取元素
            Element element = document.getElementById("search_nature_rg");
            Elements elements = element.getElementsByTag("li");
            elementsList.add(elements);
        }

        //存放爬取数据
        List<DDContent> goodList = new ArrayList<>();

        //遍历出内容
        for (Elements elements : elementsList) {
            for (Element element : elements) {
                //图片 当当此处很奇怪, 有data-original属性的图片用src无法正确获取, 且有些图片只有src属性, 因此要做判断
                String img = element.getElementsByTag("img").eq(0).attr("data-original");
                if (img.length() == 0) {
                    img = element.getElementsByTag("img").eq(0).attr("src");
                }
                //价格 并处理人民币符号(会变成?)
                String price = element.getElementsByClass("search_pre_price").eq(0).text().substring(1);
                //标题
                String title = element.getElementsByTag("a").eq(0).attr("title");
                //店铺名
                String shopName = null;
                Elements shopNameClass = element.getElementsByClass("search_book_author");
                for (Element tags : shopNameClass) {
                    shopName = tags.getElementsByTag("span").eq(2).text().replace("/", "");
                }
                //电子书
                String EBook = element.getElementsByClass("search_e_price").eq(0).text();
                EBook = EBook.substring(EBook.indexOf("¥") + 1);
                //链接
                String link = element.getElementsByClass("pic").eq(0).attr("href");

                //放入DDContent实体类
                DDContent ddContent = new DDContent();
                ddContent.setImg(img + "\n")
                        .setPrice(price + "\n")
                        .setTitle(title + "\n")
                        .setShopName(shopName + "\n")
                        .setEBook(EBook + "\n")
                        .setLink(link);

                //放入List
                goodList.add(ddContent);
            }
        }
        return goodList;
    }
}
