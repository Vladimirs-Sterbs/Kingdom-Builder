package application;

import java.awt.Polygon;
import javafx.scene.text.*;
import java.util.ArrayList;

public class Tile {
	private String type; 
	private int owner;
	private Polygon p;
	private Text text;
	private ArrayList<Integer> blacklist;

	public Tile() { 
		type = "";
		owner = -1;
		blacklist = new ArrayList<>();

	}
	public Tile(String t) {
		blacklist = new ArrayList<>();
		type = t;
		owner = -1;
	}
	public Tile(String t, int[] x, int[] y) {
		type = t;
		owner = -1;
		p = new Polygon(x, y, x.length);
		blacklist = new ArrayList<>();

	}
	public void setPoly(int[] x, int[] y){
		p = new Polygon(x, y, x.length);
	}
	public Polygon getPoly(){
		return p;
	}
	public void changeOwner(int i) {
		owner = i;
	}
	public String getType() {
		return type;
	}
	public int getOwner() {
		return owner;
	}
	public boolean hasOwner(){
		return owner!=-1;
	}
	public String toString() {
		return type+(owner>=0?"p"+owner:"");
	}
	public boolean contains(double x, double y){
		return p.contains(x, y);
	}
	public double[] getPolyline(int[] x, int[] y){
		double[] list = new double[12];
		for(int i = 0;i<12;i+=2){
			list[i] = x[i/2];
			list[i+1] = y[i/2];
		}
		return list;
	}
	public double[] getPolygonBounds(){
		return getPolyline(p.xpoints, p.ypoints);
	}
	public void addToBlacklist(int i){
        blacklist.add(i);	
		text.setText(""+(2-blacklist.size()));
    }
    public ArrayList<Integer> getBlackList(){
        return blacklist;
    }
    public boolean blackListContains(int p){
        for(int i: blacklist){
            if(p==i)
            	return true;
        }
        return false;
    }
	public void instantiateText(Text t){
		text = t;
	}
	public void setText(String s){
		text.setText(s);
	}
	public void nothing() {} // hehehehaha
}