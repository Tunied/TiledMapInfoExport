package eran.data.tileset;

import java.util.ArrayList;

public class TilesetInfo {

    public int firstGID;

    public String name;

    public ArrayList<TilesetEachTileInfo> allTilesetEachTileList;

    public TilesetInfo() {
        allTilesetEachTileList = new ArrayList<>();
    }


    public TilesetEachTileInfo GetTilesetInfoByGID(int _gid) {
        for (TilesetEachTileInfo tilesetEachTileInfo : allTilesetEachTileList) {
            if (tilesetEachTileInfo.index + firstGID == _gid) {
                return tilesetEachTileInfo;
            }
        }
        return null;
    }
}
