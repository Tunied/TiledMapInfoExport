package eran.data;

import eran.data.object.TileObjectInfo;
import eran.data.tilemap.TileMapEachTileInfo;
import eran.data.tileset.TilesetEachTileInfo;
import eran.data.tileset.TilesetInfo;

import java.util.ArrayList;
import java.util.List;

public class TiledDataRoot {

    private static TiledDataRoot mInstance;

    public static TiledDataRoot GetInstance() {
        if (mInstance == null) {
            mInstance = new TiledDataRoot();
        }
        return mInstance;
    }

    public List<TilesetInfo> allTilesetEachInfoList;

    public List<TileMapEachTileInfo> allTileMapEachTileInfoList;

    public List<TileObjectInfo> allTileObjectInfoList;

    public TiledDataRoot() {
        allTilesetEachInfoList = new ArrayList<>();
        allTileMapEachTileInfoList = new ArrayList<>();
        allTileObjectInfoList = new ArrayList<>();
    }


    /**
     * 根据给定的GID信息,返回对应的TilesetInfo
     *
     * @param _gid
     * @return
     */
    public TilesetEachTileInfo GetTilesetInfoByGID(int _gid) {
        for (TilesetInfo tilesetInfo : allTilesetEachInfoList) {
            TilesetEachTileInfo info = tilesetInfo.GetTilesetInfoByGID(_gid);
            if (info != null) return info;
        }
        return null;
    }


}
