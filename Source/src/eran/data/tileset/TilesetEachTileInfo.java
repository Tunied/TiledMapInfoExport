package eran.data.tileset;

import java.util.ArrayList;

public class TilesetEachTileInfo
{

    public transient int index;

    public ArrayList<TilesetEachTileEachProperty> allPropertyList;

    public TilesetEachTileInfo()
    {
        allPropertyList = new ArrayList<>();
    }
}
