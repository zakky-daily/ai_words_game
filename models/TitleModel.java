package models;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class TitleModel {
    private ArrayList<ImageIcon> pages;
    private int pageIndex = 0;

    public TitleModel(){
        loadImages();
    }
    private void loadImages(){
        String[] imagePaths = {
            "res/Title/manual1.png",
            "res/Title/manual2.png",
            "res/Title/manual3.png"
        };
        pages = new ArrayList<>();
        for (String path : imagePaths) {
            ImageIcon icon = new ImageIcon(path);
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                // サイズ調整 (例: 800x500)
                Image img = icon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
                pages.add(new ImageIcon(img));
            }
        }
    }

    public void nextPage(){
        if(pageIndex < pages.size()-1){
            pageIndex++;
        }
    }
    public void prevPage(){
        if(pageIndex > 0){
            pageIndex--;
        }
    }

    public ImageIcon getCurrentPage(){
        if(pages.isEmpty()){ return null;}
        return pages.get(pageIndex);
    }

    public boolean isFirstPage(){
        return pageIndex == 0;
    }
    public boolean isLastPage(){
        return pageIndex == pages.size()-1 ;
    }
    public void resetIndex(){// ダイアログを開くたびに呼び出す
        pageIndex = 0;
    }

    public enum TitleAction {
        START,
        HOW_TO_PLAY,
        NONE
    }
}
