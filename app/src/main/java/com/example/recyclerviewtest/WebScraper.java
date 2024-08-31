package com.example.recyclerviewtest;

import android.util.Log;

import com.example.recyclerviewtest.Model.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {

    public WebScraper() {

    }
    public static final String MAIN_URL = "http://albiruni.iium.edu.my/myapps/StudentOnline/schedule1.php";
    public static final int DAY = 0;
    public static final int TIME= 1;
    public static final int VENUE= 2;
    public static final int LECTURERS= 3;
    private static final String TAG = "MyActivity";
    static String[] KulliyyahListVal = {
            "AED",//1
            "BRIDG",//2
            "CFL",//3
            "CCAC",//4
            "EDUC",//6
            "ENGIN",//7
            "ECONS",//8
            "KICT",//9
            "IRKHS",//11
            "KLM",//14
            "LAWS",};//19

    public static List<Course> URLManipFunc(int kulliyyahSelectPosition, int pageSelectPosition, int semesterSelectPosition) throws IOException {

        pageSelectPosition++;

        semesterSelectPosition++;

        String sessionVal = "2020/2021";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(MAIN_URL)
                .append("?action=view&view=")
                .append(pageSelectPosition * 50)
                .append("&kuly=")
                .append(KulliyyahListVal[kulliyyahSelectPosition])
                .append("&ctype=%3C&course=&sem=")
                .append(semesterSelectPosition)
                .append("&ses=")
                .append(sessionVal);



        //Only used once coz I'm too lazy to write the values one by one
//
//        GetKulyList(url);



        return ScheduleScraper(stringBuilder.toString());
    }

    public static void GetKulyList(String url) throws IOException {
        Document doc = Jsoup
                .connect(url)
                .userAgent("Google Chrome")
                .timeout(6000).get();

        Elements elements = doc
                .select("body > form > table > tbody > tr:nth-child(2) > td:nth-child(2) > select > option:nth-child(n)");

        for (Element element : elements) {
            char[] ListKul;
            ListKul = element
                    .select("option:nth-child(n)")
                    .val()
                    .toCharArray();
        }
    }

    public static List<String> getElems(Elements x, int i){
        List<String> data = new ArrayList<>();
        for(Element element: x){
            data.add(element.getElementsByTag("td").get(i).text());
        }
        return data;
    }
    public static List<Course> ScheduleScraper(String url) throws IOException {
        //Tries to connect. Haven't implement the "if it fails" option
        Document document = Jsoup
                .connect(url)
                .userAgent("Google Chrome")
                .timeout(6000)
                .get();

        Elements elements = document.getElementsByTag("tbody").get(1).children();
        List<Course> DataBind = new ArrayList<>();
        for(int i =2;i<elements.size()-3;i++) {
            String code = elements.get(i).getElementsByTag("td").get(0).text();
            String sect = elements.get(i).getElementsByTag("td").get(1).text();
            String Title = elements.get(i).getElementsByTag("td").get(2).text();
            String chr = elements.get(i).getElementsByTag("td").get(3).text();
            Elements data = elements.get(i).getElementsByTag("td").get(4).getElementsByTag("tbody").select("tr");
            String htmlTable = elements.get(i).getElementsByTag("td").get(4).getElementsByTag("table").outerHtml();
//            String finalTable = "<style>\n" +
//                    "table { border-radius:10px; border-collapse:collapse; }\n"+
//                    "table, td {\n" +
//                    "border: 1px solid black;\n" +
//                     "font-size: 14px;\n"+
//                    "} td{ padding: 5px 5px 5px 5px; }</style> " + htmlTable;
            String finalTable = "<style>\n" +
                    "table { \n" +
                    "  width: 100%; \n" +
                    "  border-collapse: collapse; \n" +
                    "  border: 1px solid #ddd; \n" +
                    "} \n" +
                    "th, td { \n" +
                    "  padding: 12px; \n" +
                    "  text-align: left; \n" +
                    "  font-size: 14px; \n" +
                    "  border-bottom: 1px solid #ddd; \n" +
                    "} \n" +
                    "th { \n" +
                    "  background-color: #f0f0f0; \n" +
                    "  color: #333; \n" +
                    "  font-weight: bold; \n" +
                    "} \n" +
                    "tr:nth-child(even) { \n" +
                    "  background-color: #f9f9f9; \n" +
                    "} \n" +
                    "</style> " + htmlTable;



            DataBind.add(new Course(code,sect,Title,chr,getElems(data,DAY),getElems(data,TIME),getElems(data,VENUE),getElems(data,LECTURERS),finalTable));



        }
        return DataBind;
    }


}