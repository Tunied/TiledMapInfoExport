package eran.analyze.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JsonTileMapInfoAnalyzeLogic
{

    private String mTileMapPath;
    private File mTileMapFile;

    private JsonObject mRootObj;

    private JsonTileMapEachChunkAnalyzeLogic mChunkAnalyze;

    public void AnalyzeTileMap(String _filePath)
    {
        mTileMapPath = _filePath;
        mTileMapFile = new File(mTileMapPath);

        mChunkAnalyze = new JsonTileMapEachChunkAnalyzeLogic();

        try
        {
            String content = new Scanner(mTileMapFile).useDelimiter("\\Z").next();
            JsonParser parser = new JsonParser();
            mRootObj = parser.parse(content).getAsJsonObject();

            //读取所有的TilesetInfo
            DoImportTilesetInfo();

            //分析TileMap
            boolean infinite = mRootObj.get("infinite").getAsBoolean();
            if (infinite)
            {
                //无尽模式
                DoAnalyzeAsInfiniteMap();
            } else
            {
                //Fix模式
                DoAnalyzeAsFixedMap();
            }

            System.out.println("Done analyze tile map: " + _filePath);
            System.out.println("");

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 将当前TileMap Json中所用到的TilesetInfo 读取出来
     */
    private void DoImportTilesetInfo()
    {
        JsonTilesetInfoAnalyzeLogic tilesetInfoAnalyzeLogic = new JsonTilesetInfoAnalyzeLogic();

        JsonArray tilesets = mRootObj.getAsJsonArray("tilesets");

        for (JsonElement tileInfo : tilesets)
        {
            //Json表中的TilesetInfo的相对路径
            String subPath = tileInfo.getAsJsonObject().get("source").getAsString();
            //TilesetInfo的绝对路径
            String fullPath = mTileMapFile.getParent() + File.separator + subPath;

            int firstGID = tileInfo.getAsJsonObject().get("firstgid").getAsInt();
            tilesetInfoAnalyzeLogic.AnalyzeTilesetInfo(fullPath, firstGID);
        }
    }

    /**
     * 按照无尽地图模式进行分析
     */
    private void DoAnalyzeAsInfiniteMap()
    {
        JsonArray layersArray = mRootObj.getAsJsonArray("layers");
        for (JsonElement eachLayertData : layersArray)
        {
            JsonObject eachLayerObj = eachLayertData.getAsJsonObject();
            String layerType = eachLayerObj.get("type").getAsString();
            String layerName = eachLayerObj.get("name").getAsString();
            if (layerType.equals("tilelayer"))
            {
                System.out.println("Start analyze layer " + layerName);
                JsonArray chunksArraly = eachLayerObj.getAsJsonArray("chunks");
                for (JsonElement eachChunkData : chunksArraly)
                {
                    JsonObject eachChunkObj = eachChunkData.getAsJsonObject();
                    mChunkAnalyze.AnalyzeTileMap(eachChunkObj, layerName);
                }
            } else
            {
                System.out.println("Ignore layer " + layerName + " as it type not 'tilelayer' ");
            }
        }
    }

    /**
     * 按照FixMap进行分析
     */
    private void DoAnalyzeAsFixedMap()
    {
        JsonArray chunksArray = mRootObj.getAsJsonArray("layers");
        for (JsonElement eachChunkData : chunksArray)
        {
            JsonObject eachChunkObj = eachChunkData.getAsJsonObject();

            String layerType = eachChunkObj.get("type").getAsString();
            String layerName = eachChunkObj.get("name").getAsString();

            if (layerType.equals("tilelayer"))
            {
                System.out.println("Start analyze layer " + layerName);
                mChunkAnalyze.AnalyzeTileMap(eachChunkObj, layerName);
            } else
            {
                System.out.println("Ignore layer " + layerName + " as it type not 'tilelayer' ");
            }
        }
    }


}
