package eran.data.tilemap;

import eran.data.object.TileObjectInfo;

import java.util.List;

public class TileMapInfo {
    //当前地图的名称
    public String tileMapName;
    //瓦片信息
    public List<TileMapEachTileInfo> allTileMapEachTileInfoList;
    //ObjectGroup信息
    public List<TileObjectInfo> allTileObjectInfoList;
}
