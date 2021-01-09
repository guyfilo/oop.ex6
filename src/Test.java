/**
 * הוראות שימוש (בעברית, כי נמאס כבר מתיעודים באנגלית)
 * שימו את הקובץ הנוכחי בתיקיית
 * src
 * שלכם בסמוך לחבילה
 * oop.ex6
 * כדי שיהיה אפשר להריץ ושהוא יכיר את התכנית שלכם
 *
 * יש שני שדות סטטים, אחד צריך להכיל את הנתיב לתיקייה עם כל הטסטים שהם נתנו (הקבצי
 * javac)
 * השדה נקרא
 * testDirPath
 * השדה השני צריך להכיל את הנתיב לקובץ סיכום שהם נתנו
 * sjavac_tests.txt
 * זהו השדה
 * exceptedFilePath
 *
 * מה שהטסט עושה זה מריץ את התכנית שלכם על כל הקבצים (היא אמורה להדפיס 0 או 1 בכל פעם)
 * ובסמוך לכל טסט להדפיס את השורה הרלוונטית בקובץ שלהם, כך תוכלו להשוות את הערך שמתקבל
 * ולבדוק שהתכנית שלכם נופלת באמת בגלל הסיבה הזאת
 *
 * שימו לב שיש קבצים שאין להם שורה מתאימה בקובץ ולידם לא יודפס כלום.
 *
 * המלצה שלי- בכל פעם שהתכנית מדפיסה 1 תדפיסו את הסיבה לכך אצלכם וכך יהיה נוח להשוות
 *
 */

import oop.ex6.main.Sjavac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Objects;

public class Test {
//     guy:
//     enter here the path to tests directory
    public static final String testDirPath = "/Users/guyfilo/IdeaProjects/EX6_FINAL/test_files";
    // enter here the path to sjavac_tests.txt
    public static final String exceptedFilePath = "/Users/guyfilo/IdeaProjects/EX6_FINAL/src/sjavac_tests.txt";

//    // noa:
//    // enter here the path to tests directory
//    public static final String testDirPath = "/Users/noaabarbanel/IdeaProjects/oop.ex6/test_files";
//    // enter here the path to sjavac_tests.txt
//    public static final String exceptedFilePath = "/Users/noaabarbanel/IdeaProjects/oop.ex6/src/sjavac_tests.txt";


    public static void main(String[] args) {
        File tesDir = new File(testDirPath);
        try {
            FileReader reader = new FileReader(exceptedFilePath);
            BufferedReader bReader = new BufferedReader(reader);
            HashMap<String, String> filesLines = new HashMap<>();
            String line = "";
            while (line != null) {
                line = bReader.readLine();
                while (line.equals("")) {
                    line = bReader.readLine();
                    if(line == null) {
                        break;
                    }
                }
                if(line == null) {
                    break;
                }
                String fileName = line.substring(0, 13);
                filesLines.put(fileName, line);
            }
            int countMistakes = 0;
            int countPassed = 0;
            for (File file : Objects.requireNonNull(tesDir.listFiles())) {
                String[] arguments = {file.getAbsolutePath()};
                System.out.println("_________\n" + file.getName());
                String result = Sjavac.main1(arguments);
                if (filesLines.containsKey(file.getName())) {
                    String fileResult = filesLines.get(file.getName());
                    String expectadResult = fileResult.split(" ")[1];
                    if (!expectadResult.equals(result)){
                        System.out.println("expected: " + expectadResult + ", actual : "
                                + result +  ",in file: " + fileResult);
                        countMistakes++;
                    }  else {
                        System.out.println("paassssed!!!");
                        countPassed++;
                    }
                }
                System.out.println("_________");
            }
            System.out.println("failed in :" + countMistakes);
            System.out.println("passed  in :" + countPassed);
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

    }
}
