package eran.data.tilemap;

import eran.data.tileset.TilesetEachTileEachProperty;

import java.util.ArrayList;

public class TileMapEachTileInfo {

    public int x;

    public int y;

    public String layer;

    public ArrayList<TilesetEachTileEachProperty> allPropertyList;

    public TileMapEachTileInfo() {
        this.allPropertyList = new ArrayList<>();
    }
}
