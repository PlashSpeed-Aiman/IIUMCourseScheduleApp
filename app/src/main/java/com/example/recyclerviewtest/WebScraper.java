package com.example.recyclerviewtest;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.System.out;

public class WebScraper {

    public WebScraper() {
    }

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

    public static List<List<String>> URLManipFunc(int kulliyyahSelectPosition, int pageSelectPosition, int semesterSelectPosition) throws IOException {

        List<List<String>> GroupBind2;

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

    public static List<List<String>> WebScrapper(String url) throws IOException {
        String subjectTitle;
        String section;
        String subjectCode;
        String Lecture1;
        String Lecturer2 = null;
        String Lecturer3 = null;
        //Tries to connect. Haven't implement the "if it fails" option
        Document document = Jsoup
                .connect(url)
                .userAgent("Google Chrome")
                .timeout(6000)
                .get();

        Elements subjectCodeRowRough = document
                .select("body > table:nth-child(4) > tbody > tr:nth-child(n)");//Select the table that contains the Data List
//
        //For loop eliminates the parts that don't contain the data we need
        for (int i = 3; i < subjectCodeRowRough.size() - 2; i++) {
            //I put here so that it resets the ArrayList for every iteration
            List<String> DataBind = new ArrayList<>();
            Element test = subjectCodeRowRough
                    .get(i);

            subjectCode = test
                    .select("tr:nth-of-type(n) > td:nth-of-type(1)")
                    .first()
                    .text();

            subjectTitle = test
                    .select("tr:nth-of-type(n) >td:nth-of-type(3)")
                    .first()
                    .text();

            section = test
                    .select("tr:nth-of-type(n) >td:nth-of-type(2)")
                    .first()
                    .text();

            Lecture1 = test
                    .select(" tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)")
                    .first()
                    .text();

            //check if the index value exist
            if (!test.select(" tr:nth-child(n) > td:nth-child(5) > table > tbody > tr:nth-child(2) > td:nth-child(4)").isEmpty()) {
                //check if its not empty
                if (!(test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)").get(1).text() == null)) {
                    Lecturer2 = test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)").get(1).text();
                }
            } else if (!test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr:nth-child(3) > td:nth-child(4)").isEmpty()) {
                Lecturer3 = test.select("tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(4)").get(2).text();
            }

            String creditHour = test
                    .select("tr:nth-of-type(n) >td:nth-of-type(4)")
                    .first()
                    .text();

            String Venue = test
                    .select(" tr:nth-child(n) > td:nth-child(5) > table > tbody > tr > td:nth-child(3)")
                    .first()
                    .text();

            out.println("Code: " + subjectCode + "\n" + "Title: " + subjectTitle + "\n" + "Section: " + section);
            //Unnecessary but it makes life easier
            String[] BundleList = {subjectTitle, section, subjectCode, Lecture1, Lecturer2, Lecturer3, Venue};
            for (String element : BundleList)
                DataBind.addAll(Collections.singleton(element));
            GroupBind.add(DataBind);
            Log.d("TAG", "String.valueOf(GroupBind)");


        }
        return GroupBind;
    }


}
