package eran.analyze.xml;

import eran.analyze.AnalyzeUtils;
import eran.data.TiledDataRoot;
import eran.data.tileset.TilesetEachTileInfo;
import org.dom4j.Element;

public class XMLTiledMapEachChunkAnalyzeLogic
{

    public void AnalyzeTileMap(Element _dataEl, String _layerName, int _startX, int _startY, int _width)
    {
        String dataValue = _dataEl.getStringValue();
        String[] dataList = dataValue.split(",");
        int length = dataList.length;
        for (int i = 0; i < length; i++)
        {
            int gid = Integer.parseInt(dataList[i].trim());
            TilesetEachTileInfo tilesetInfo = TiledDataRoot.GetInstance().GetTilesetInfoByGID(gid);
            if (tilesetInfo != null)
            {
                AnalyzeUtils.PushTileMapEachTileInfo(_layerName, tilesetInfo, _startX, _startY, i, _width);
            }
        }
    }
}
