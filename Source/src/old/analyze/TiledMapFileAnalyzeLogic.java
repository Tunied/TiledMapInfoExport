package old.analyze;


import old.data.export.ExportMapInfo;
import old.data.export.ExportTileInfo;
import old.data.tiled.TiledMapData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eran on 2017/6/5.
 */
public class TiledMapFileAnalyzeLogic
{

    private ExportMapInfo mMapInfo;

    private TiledMapData mTiledMapData;

    private HashMap<String, String> tilePropertyDic;

    public ExportMapInfo StartAnalyze(TiledMapData _tiledMapData, String _fileName)
    {
        mTiledMapData = _tiledMapData;
        AnalyzeTileProperty();
        AnalyzeTileMap();
        mMapInfo.name = _fileName;
        return mMapInfo;
    }

    private void AnalyzeTileProperty()
    {
        tilePropertyDic = new HashMap<>();
        //将Tile属性Push到一个Dic中,属性中的Key需要+1才能和data中的ID对应上
        for (Map.Entry<String, HashMap<String, String>> entry : mTiledMapData.tilesets[0].tileproperties.entrySet())
        {
            String strKey = entry.getKey();
            int intKey = Integer.parseInt(strKey);
            intKey++;
            tilePropertyDic.put("" + intKey, entry.getValue().get("key"));
        }
    }

    private void AnalyzeTileMap()
    {
        mMapInfo = new ExportMapInfo();
        mMapInfo.allTileInfoList = new ArrayList<>();

        int mapWidth = mTiledMapData.layers[0].width;
        int mapHeight = mTiledMapData.layers[0].height;

        mMapInfo.width = mapWidth;
        mMapInfo.height = mapHeight;

        for (int index = 0; index < mTiledMapData.layers[0].data.length; index++)
        {
            int tileID = mTiledMapData.layers[0].data[index];
            if (tilePropertyDic.containsKey("" + tileID))
            {
                int tileX = index % mapWidth;
                int tileY = (int) Math.floor(index / mapWidth);
                //翻转Y值,因为U3D坐标是从左下,Tiled坐标是从左上
                tileY = mapHeight - 1 - tileY;

                ExportTileInfo tileInfo = new ExportTileInfo();
                tileInfo.key = tilePropertyDic.get("" + tileID);
                tileInfo.tileX = tileX;
                tileInfo.tileY = tileY;
                mMapInfo.allTileInfoList.add(tileInfo);
            }
        }
    }


}
