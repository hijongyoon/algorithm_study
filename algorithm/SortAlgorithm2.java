package algorithm;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Time implements Comparable<Time>{
    String day;
    String month;
    String reMonth;
    String year;
    String t;
    String remake;
    public Time(String time){
        String[]token1=time.split(":",2);
        String[]token2=token1[0].split("/");
        day=token2[0]; month=token2[1]; year=token2[2]; t=token1[1];
        reMonth=month;
        remake=year+"/"+change()+"/"+day+"/"+t;
    }
    @Override
    public int compareTo(Time t1){ return remake.compareTo(t1.remake);}
    private String change(){
        if(month.equals("Jan")) month="1"; else if(month.equals("Feb")) month="2"; else if(month.equals("Mar")) month="3";
        else if(month.equals("Nov")) month="11"; else if(month.equals("Dec")) month="12";
        return month;
    }
}

class Log implements Comparable<Log>{
    String ip;
    Time time;
    String url;
    String status;
    public Log(String ip,Time time,String url,String status){
        this.ip=ip; this.time=time; this.url=url; this.status=status;
    }
    @Override
    public int compareTo(Log l1){ return time.compareTo(l1.time);}
    public void showInfo(int num){
        if(num==0) {
            System.out.println(time.day+"/"+time.reMonth+"/"+time.year+":"+time.t); System.out.println("    IP: " + ip);
            System.out.println("    URL: " + url); System.out.println("    Status: " + status);
        }
        else if(num==1){
                System.out.println(ip); System.out.println("    Time: " + time.day+"/"+time.reMonth + "/" + time.year + ":" + time.t);
                System.out.println("    URL: " + url); System.out.println("    Status: " + status);
        }
    }
}

public class SortAlgorithm2 {
    static List<Log>list=new ArrayList<>();
    static int num=0;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in); String str;
        while(true){
            System.out.print("$ ");
            str=sc.nextLine();
            if(str.equals("read webLog.csv")) callOfWebLog();
            else if(str.equals("print")) printAll();
            else if(str.equals("sort -t")) {num=0; Collections.sort(list);}
            else if(str.equals("sort -ip")) sortByIp();
            else if(str.equals("exit")) break;
        }
    }
    private static void callOfWebLog(){
        Path fp= Paths.get("webLog.csv"); String str; String[]token;
        try(BufferedReader r= Files.newBufferedReader(fp)){
            str=r.readLine();
            while(true){
                str=r.readLine();
                if(str==null) break;
                token=str.split(",");//0 이 아이피, 1이 시간, 2가 url, 3이 상태.
                list.add(new Log(token[0],new Time(token[1].replace("[","")),token[2],token[3]));
            }
        }
        catch (IOException e){
            e.printStackTrace();;
        }
    }
    private static void printAll(){
        int i=0;
        while (i < list.size()) list.get(i++).showInfo(num);
    }
    private static void sortByIp(){
        num=1;
        Comparator<Log> cmp= Comparator.comparing(s -> s.ip);
        Collections.sort(list,cmp);
    }
}
