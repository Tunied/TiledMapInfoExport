package eran.analyze.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eran.analyze.AnalyzeUtils;
import eran.data.TiledDataRoot;
import eran.data.tileset.TilesetEachTileInfo;

public class JsonTileMapEachChunkAnalyzeLogic
{

    public void AnalyzeTileMap(JsonObject _chunkRootObj, String _layerName)
    {
        JsonArray gidArray = _chunkRootObj.get("data").getAsJsonArray();
        int length = gidArray.size();
        int startX = _chunkRootObj.get("x").getAsInt();
        int startY = _chunkRootObj.get("y").getAsInt();
        int width = _chunkRootObj.get("width").getAsInt();

        for (int i = 0; i < length; i++)
        {
            JsonElement gid = gidArray.get(i);
            int tilesetInfoGID = gid.getAsInt();
            TilesetEachTileInfo tilesetInfo = TiledDataRoot.GetInstance().GetTilesetInfoByGID(tilesetInfoGID);
            if (tilesetInfo != null)
            {
                AnalyzeUtils.PushTileMapEachTileInfo(_layerName, tilesetInfo, startX, startY, i, width);
            }
        }

    }

}
