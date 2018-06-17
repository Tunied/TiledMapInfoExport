package eran.data.object;

import java.util.ArrayList;

public class TileObjectInfo {

    public float posX;

    public float posY;

    public String layer;

    public ArrayList<TileObjectEachProperty> allPropertyList;

    public TileObjectInfo() {
        allPropertyList = new ArrayList<>();
    }
}
