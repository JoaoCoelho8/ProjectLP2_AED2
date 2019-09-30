package edu.ufp.inf.projeto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date implements Serializable, Comparable<Date> {

    private int day;

    private int month;

    private int year;

    public Date(int day, int month, int year) {
        this.day=day;
        this.month=month;
        this.year=year;
    }

    public Date() {
        Calendar gregCalendar = new GregorianCalendar();
        this.day=gregCalendar.get(Calendar.DAY_OF_MONTH);
        this.month=gregCalendar.get(Calendar.MONTH)+1;
        this.year=gregCalendar.get(Calendar.YEAR);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean beforeDate(Date d) {
        if(this.year<d.year)
        {
            return true;
        }else if(this.year==d.year)
        {
            if(this.month<d.month)
            {
                return true;
            }
            else if(this.month==d.month)
            {
                if(this.day<d.day)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isLeapYear(int year) {
        //multiplo de 4 e n de 100
        //multiplo de 4 e 400
        return ((year % 4==0) && ((year%100 != 0)||(year%400==0)));
    }

    public boolean isLeapYear() {
        return isLeapYear(this.year);
    }

    public int daysMonth() {
        switch (month){
            case 11:
            case 9:
            case 6:
            case 4: return 30;
            case 2: if(isLeapYear(year)){
                return 29;
            }else{
                return 28;
            }
            default: return 31;
        }
    }

    public boolean afterDate(Date d) {
        if(this.year>d.year)
        {
            return true;
        }else if(this.year==d.year)
        {
            if(this.month>d.month)
            {
                return true;
            }
            else if(this.month==d.month)
            {
                if(this.day>d.day)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int differenceTwoDates(Date d){
        int difference;
        difference= d.year-this.year;
        if(d.month < this.month)
        {
            difference=difference-1;
        }else if(d.month == this.month){
            if(d.day < this.day)
            {
                difference=difference-1;
            }
        }
        return difference;
    }
    public int differenceYears(Date d) {
        return Math.abs(this.year - d.year);
    }

    public void incrementDate() {
        if(this.month==12 && this.day==31){
            this.day=1;
            this.month=1;
            this.year++;
        }
        else if(this.day==this.daysMonth()){
            this.day=1;
            this.month++;
        }else{
            this.day++;
        }
    }

    //diferença de meses entre 2 datas (no mesmo ano)
    public int monthDifferenceBetweenTwoDates(Date d){
        return d.month-this.month;
    }

    //diferencça de dias entre 2 datas (no mesmo ano)
    public int dayDifferenceBetweenTwoDates(Date d){
        if(this.beforeDate(d)){
            int dayCounter=0;
            while (this!=d){
                dayCounter++;
                this.incrementDate();
            }
            return dayCounter;
        }else{
            return -1;
        }
    }

    public int compareTo(Date d){
        if(day==d.day && month==d.month && year==d.year){
            return 0;
        }
        else if(beforeDate(d)){
            return -1;
        }
        else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return this.day + "/" + this.month + "/" + this.year;
    }
}