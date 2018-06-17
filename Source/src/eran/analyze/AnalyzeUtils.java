package eran.analyze;

import eran.data.TiledDataRoot;
import eran.data.tilemap.TileMapEachTileInfo;
import eran.data.tileset.TilesetEachTileEachProperty;
import eran.data.tileset.TilesetEachTileInfo;

public class AnalyzeUtils {

    public static void PushTileMapEachTileInfo(String _layerName,
                                               TilesetEachTileInfo _tilesetInfo,
                                               int _startX,
                                               int _startY,
                                               int _index,
                                               int _width) {
        TileMapEachTileInfo tilemapEachInfo = new TileMapEachTileInfo();
        tilemapEachInfo.x = _startX + _index % _width;
        tilemapEachInfo.y = _startY + (int) Math.ceil(_index / _width);
        tilemapEachInfo.layer = _layerName;

        for (TilesetEachTileEachProperty property :
                _tilesetInfo.allPropertyList) {
            tilemapEachInfo.allPropertyList.add(property);
        }

        TiledDataRoot.GetInstance().allTileMapEachTileInfoList.add(tilemapEachInfo);
    }

}
