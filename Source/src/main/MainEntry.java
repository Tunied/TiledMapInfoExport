package main;

import com.google.gson.Gson;
import eran.analyze.json.JsonTileMapInfoAnalyzeLogic;
import eran.analyze.xml.XMLTileMapInfoAnalyzeLogic;
import eran.data.TiledDataRoot;
import eran.data.tilemap.TileMapInfo;
import utils.FileUtils;

import java.io.File;

public class MainEntry {


    public static void main(String[] args) {
        System.out.println("");
        System.out.println("=======================");
        System.out.println("TileMap Info Export");
        System.out.println("");
        System.out.println("         Powered by Eran");
        System.out.println("=======================");
        System.out.println("");
        if (args.length == 2) {
            String sourcePath = args[0];
            String exportPath = args[1];

            File tileMapFile = new File(sourcePath);

            if (sourcePath.endsWith(".json")) {
                JsonTileMapInfoAnalyzeLogic mJsonAnalise = new JsonTileMapInfoAnalyzeLogic();
                mJsonAnalise.AnalyzeTileMap(sourcePath);
            } else if (sourcePath.endsWith(".tmx")) {
                XMLTileMapInfoAnalyzeLogic mTMXAnalise = new XMLTileMapInfoAnalyzeLogic();
                mTMXAnalise.AnalyzeTileMap(sourcePath);
            } else {
                System.out.println("the map file your want export is not json or tmx format");
                return;
            }

            TileMapInfo mapInfo = new TileMapInfo();
            mapInfo.tileMapName = FileUtils.GetFileNameWithoutPrefix(tileMapFile.getName());
            mapInfo.allTileMapEachTileInfoList = TiledDataRoot.GetInstance().allTileMapEachTileInfoList;
            mapInfo.allTileObjectInfoList = TiledDataRoot.GetInstance().allTileObjectInfoList;

            Gson gson = new Gson();
            String json = gson.toJson(mapInfo);
            FileUtils.writeStringToFile(json, exportPath);

            System.out.println("Save file to :" + exportPath);
            System.out.println("");
            System.out.println("Thanks for using TiledMapInfoExport");

        } else {
            System.out.println("HOW TO USE :");
            System.out.println("you should provide 2 arguments , first one specify the tile map file path , the second one specify the json file you want to export");
            System.out.println("");
            System.out.println("like: ");
            System.out.println("java -jar TilemapInfoExport.jar C:\\example.xls E:\\out\\example.json");
            System.out.println("");
        }

    }
}
