package me.vinhdo.androidsuppordesign.models;

import me.vinhdo.androidsuppordesign.utils.Log;

/**
 * Created by vinh on 10/15/15.
 */
public class Sub {
    private int id;
    private long start;
    private long end;
    private String text;

    public Sub(int id, long start, long end, String text) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.text = text;
    }

    public Sub(int id, String time, String text){
        this.id = id;
        this.text = text;
        setTime(time);
    }

    public Sub(int pos){
        this.start = pos;
        this.end = pos;
    }

    @Override
    public boolean equals(Object o) {
        Sub pom = (Sub) o;
//        Log.d("SUBSUB ss " + pom.start + " " + start + " " + end);
        if(pom.end <= end && pom.start >= start){
            return true;
        }
        return false;
    }

    public boolean equals(int time){
        if(time <= end && time >= start){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time){
        this.setStart(parseTime(time.split("-->")[0]));
        this.setEnd(parseTime(time.split("-->")[1]));
        Log.d("SUBSUBTIME " + time + " " + start + " " + end);
    }

    private long parseTime(String in) {
        long hours = Long.parseLong(in.split(":")[0].trim());
        long minutes = Long.parseLong(in.split(":")[1].trim());
        long seconds = Long.parseLong(in.split(":")[2].split(",")[0].trim());
        long millies = Long.parseLong(in.split(":")[2].split(",")[1].trim());

        return hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000 + millies;

    }
}
