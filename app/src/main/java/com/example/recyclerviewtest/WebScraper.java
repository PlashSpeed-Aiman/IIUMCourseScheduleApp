package com.example.recyclerviewtest;

import android.util.Log;

import com.example.recyclerviewtest.Model.Course;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {

    public WebScraper() {

    }
    public static final int DAY = 0;
    public static final int TIME= 1;
    public static final int VENUE= 2;
    public static final int LECTURERS= 3;
    private static final String TAG = "MyActivity";
    static List<List<String>> GroupBind = new ArrayList<>();
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

        List<Course> GroupBind2;

        pageSelectPosition++;

        semesterSelectPosition++;
        String url = "http://albiruni.iium.edu.my/myapps/StudentOnline/schedule1.php";

        String sessionVal = "2019/2020";

        String url2 = "?action=view&view=".concat(String.valueOf(pageSelectPosition * 50)) + "&kuly=".concat(KulliyyahListVal[kulliyyahSelectPosition]) + "&ctype=%3C&course=&sem=".concat(String.valueOf(semesterSelectPosition)) + "&ses=".concat(sessionVal);

        String url3 = url + url2;
        //Only used once coz I'm too lazy to write the values one by one
//
//        GetKulyList(url);


        GroupBind2 = WebScrapper(url3);

        return GroupBind2;
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
    public static List<Course> WebScrapper(String url) throws IOException {
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


            DataBind.add(new Course(code,sect,Title,chr,getElems(data,DAY),getElems(data,TIME),getElems(data,VENUE),getElems(data,LECTURERS)));
//        Elements subjectCodeRowRough = document
//                .select("body > table:nth-child(4) > tbody > tr:nth-child(n)");//Select the table that contains the Data List
////
//        //For loop eliminates the parts that don't contain the data we need
//        for (int i = 3; i < subjectCodeRowRough.size() - 1; i++) {
//            //I put here so that it resets the ArrayList for every iteration
//            List<String> DataBind = new ArrayList<>();
//            Element test = subjectCodeRowRough
//                    .get(i);
//
//            subjectCode = test
//                    .select("tr:nth-of-type(n) > td:nth-of-type(1)")
//                    .first()
//                    .text();
//
//            subjectTitle = test
//                    .select("tr:nth-of-type(n) >td:nth-of-type(3)")
//                    .first()
//                    .text();
//
//            section = test
//                    .select("tr:nth-of-type(n) >td:nth-of-type(2)")
//                    .first()
//                    .text();
//
//            List<String> data = elements.get(2 + i).getElementsByTag("td").get(4).getElementsByTag("tr").eachText();

//            Lecture1 = test
//                    .select(" tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)")
//                    .first()
//                    .text();
//
//            //check if the index value exist
//            if (!test.select(" tr:nth-child(n) > td:nth-child(5) > table > tbody > tr:nth-child(2) > td:nth-child(4)").isEmpty()) {
//                //check if its not empty
//                if (!(test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)").get(1).text() == null)) {
//                    Lecturer2 = test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)").get(1).text();
//                }
//            } else if (!test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr:nth-child(3) > td:nth-child(4)").isEmpty()) {
//                Lecturer3 = test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)").get(2).text();
//            }
//
//            String creditHour = test
//                    .select("tr:nth-of-type(n) >td:nth-of-type(4)")
//                    .first()
//                    .text();
//
//            String Venue = test
//                    .select(" tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(3)")
//                    .first()
//                    .text();

      /*      out.println("Code: " + subjectCode + "\n" + "Title: " + subjectTitle + "\n" + "Section: " + section);*/
            //Unnecessary but it makes life easier
//            String[] BundleList = {code,sect,Title,chr, String.valueOf(data)};
//            for (String element : BundleList)
//                DataBind.addAll(Collections.singleton(element));
//            GroupBind.add(DataBind);
            Log.d("TAG", "String.valueOf(GroupBind)");


        }
        return DataBind;
    }


}