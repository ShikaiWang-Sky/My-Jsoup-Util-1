package com.wang.util;

import com.wang.pojo.JDContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class JDHtmlParseUtil {

    public List<JDContent> parseJD(String keywords) throws IOException {
        //关键字转码
        String keyword = URLEncoder.encode(keywords, "UTF-8");
        //拼接URL
        String url = "https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8";
        //爬取前5页, jd为奇数编号
        List<String> urlList = new ArrayList<>();
        for (int page = 1; page <= 9; page++) {
            String pageNum = "&page=" + page;
            urlList.add(url + pageNum);
        }

        //存放每个页面的解析结果
        ArrayList<Elements> elementsList = new ArrayList<>();
        for (String singleUrl : urlList) {
            //解析网页
            Document document = Jsoup.parse(new URL(singleUrl), Constant.JDTimeout);
            //获取元素
            Element element = document.getElementById("J_goodsList");
            Elements elements = element.getElementsByTag("li");
            elementsList.add(elements);
        }

        //存放爬取数据
        List<JDContent> goodList = new ArrayList<>();

        //遍历出内容
        for (Elements elements : elementsList) {
            for (Element element : elements) {
                //图片
                String img = element.getElementsByTag("img").eq(0).attr("data-lazy-img");
                //价格
                String price = element.getElementsByClass("p-price").eq(0).text();
                //标题
                String title = element.getElementsByClass("p-name").eq(0).text();
                //店铺名
                String shopName = element.getElementsByClass("curr-shop hd-shopname").eq(0).text();
                //标签
                String label = element.getElementsByClass("goods-icons J-picon-tips J-picon-fix").eq(0).text();
                //链接
                String link = element.getElementsByTag("a").eq(1).attr("href");

                //放入JDContent实体类
                JDContent content = new JDContent();
                content.setImg(img + "\n")
                        .setPrice(price + "\n")
                        .setTitle(title + "\n")
                        .setShopName(shopName + "\n")
                        .setLabel(label + "\n")
                        .setLink(link);
                //放入List
                goodList.add(content);
            }
        }
        return goodList;

    }

}
