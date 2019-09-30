package edu.ufp.inf.projeto;

import java.io.Serializable;

public class Point implements Serializable {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x= x;
        this.y= y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //distanciax
    public double distX(Point p){
        return (this.x-p.x);
    }

    //distanciay
    public double distY(Point p){
        return (this.y-p.y);
    }

    //distancia(hipotenusa)
    public double dist(Point p){
        double dx=this.distX(p);
        double dy=this.distY(p);
        return Math.sqrt(dx*dx+dy*dy);
    }

    //xbetweenpoints
    public boolean xBetweenPoints(Point leftPt, Point rightPt){
        return (this.x > leftPt.x && this.x < rightPt.x);
    }

    //ybetweenpoints
    public boolean yBetweenPoints(Point upperPt, Point lowerPt){
        return (this.y < upperPt.y && this.y > lowerPt.y);
    }

    //tostring
    @Override
    public String toString() {
        return "Point: {" + this.x + ", " +this.y + "}";
    }
}
