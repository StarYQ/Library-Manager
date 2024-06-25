public class Date{
    private int month;
    private int day;
    private int year;
    public Date(){
        this.month=1;
        this.day=1;
        this.year=2000;
    }
    public Date(int month, int day, int year){
        this.month=month;
        this.day=day;
        this.year=year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public int getYear(){
        return year;
    }
    public void setMonth(int month){
        this.month=month;
    }
    public void setDay(int day){
        this.day=day;
    }
    public void setYear(int year){
        this.year=year;
    }
    //The compare method below compares two date objects to order in chronological order - if x=y, it returns 0,
    //if x<y, it returns -1, and if x>y, it returns 1
    public static int compare(Date x, Date y){
        if (x.year!=y.year){
            return x.year<y.year?-1:1;
        } else if (x.month!=y.month){
            return x.month<y.month?-1:1;
        } else if (x.day!=y.day){
            return x.day<y.day?-1:1;
        }
        return 0;
    }
    public String toString(){
        return (month+"/"+day+"/"+year);
    }
}
