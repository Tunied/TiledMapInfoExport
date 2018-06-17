package eran.analyze.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eran.data.TiledDataRoot;
import eran.data.tileset.TilesetEachTileEachProperty;
import eran.data.tileset.TilesetEachTileInfo;
import eran.data.tileset.TilesetInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class JsonTilesetInfoAnalyzeLogic {

    /**
     * 将给定TilesetInfo中的Json数据解析为对应的Java Object
     */
    public void AnalyzeTilesetInfo(String _filePath, int _firstGID) {
        System.out.println("start analyze tileset : " + _filePath);

        File file = new File(_filePath);

        try {
            String content = new Scanner(file).useDelimiter("\\Z").next();

            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parse(content).getAsJsonObject();

            TilesetInfo tilesetInfo = new TilesetInfo();
            tilesetInfo.name = rootObj.get("name").getAsString();
            tilesetInfo.firstGID = _firstGID;

            JsonObject tileproperties = rootObj.getAsJsonObject("tileproperties");
            JsonObject tilepropertytypes = rootObj.getAsJsonObject("tilepropertytypes");

            Set<Map.Entry<String, JsonElement>> entrySet = tileproperties.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String key = entry.getKey();
                TilesetEachTileInfo eachTileInfo = DoAnalyzeEachTileset(
                        tileproperties.getAsJsonObject(key),
                        tilepropertytypes.getAsJsonObject(key),
                        key
                );
                tilesetInfo.allTilesetEachTileList.add(eachTileInfo);
            }

            TiledDataRoot.GetInstance().allTilesetEachInfoList.add(tilesetInfo);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private TilesetEachTileInfo DoAnalyzeEachTileset(JsonObject _eachTileProperties, JsonObject _eachTilePropertytypes, String _key) {

        TilesetEachTileInfo eachTileInfo = new TilesetEachTileInfo();
        eachTileInfo.index = Integer.valueOf(_key);

        Set<Map.Entry<String, JsonElement>> entrySet = _eachTileProperties.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String key = entry.getKey();
            String value = _eachTileProperties.get(key).getAsString();
            String type = _eachTilePropertytypes.get(key).getAsString();

            TilesetEachTileEachProperty eachProperty = new TilesetEachTileEachProperty();
            eachProperty.key = key;
            eachProperty.keyType = type;
            eachProperty.value = value;
            eachTileInfo.allPropertyList.add(eachProperty);
        }

        return eachTileInfo;
    }


}
