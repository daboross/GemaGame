package net.daboross.games.gemagame.code;

import java.util.ArrayList;
import java.util.List;

public class PlatformList {

    public List<Integer> xPosList;
    public List<Integer> yPosList;
    public List<Integer> xLengthList;
    public List<Integer> yLengthList;

    public PlatformList() {
        xPosList = new ArrayList<Integer>();
        yPosList = new ArrayList<Integer>();
        xLengthList = new ArrayList<Integer>();
        yLengthList = new ArrayList<Integer>();
    }
}
