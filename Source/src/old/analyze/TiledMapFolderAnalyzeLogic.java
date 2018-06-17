package old.analyze;

import com.google.gson.Gson;
import old.data.export.ExportMapFolderInfo;
import old.data.export.ExportMapInfo;
import old.data.tiled.TiledMapData;
import utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Eran on 2017/6/5.
 */
public class TiledMapFolderAnalyzeLogic
{

    private ExportMapFolderInfo mapFolderInfo;

    public ExportMapFolderInfo StartAnalyze(String _jsonFolder)
    {
        mapFolderInfo = new ExportMapFolderInfo();
        mapFolderInfo.allMapInfoList = new ArrayList<>();
        File folder = new File(_jsonFolder);
        if (folder.isDirectory())
        {
            File[] allFiles = folder.listFiles(
                    (dir, name) -> {
                        return name.toLowerCase().endsWith(".json");
                    }
            );
            for (File eachFile : allFiles)
            {
                System.out.println("Start read file: " + eachFile.getName());
                DoAnalyzeEachFile(eachFile);
            }
        } else
        {
            System.out.println("Json folder : " + _jsonFolder + " is not directory");
        }
        return mapFolderInfo;
    }


    private void DoAnalyzeEachFile(File _eachFile)
    {
        try
        {
            String content = new Scanner(_eachFile).useDelimiter("\\Z").next();
            Gson gson = new Gson();
            TiledMapData mapData = gson.fromJson(content, TiledMapData.class);

            TiledMapFileAnalyzeLogic fileAnalyzeLogic = new TiledMapFileAnalyzeLogic();
            ExportMapInfo mapInfo = fileAnalyzeLogic.StartAnalyze(mapData, FileUtils.GetFileNameWithoutPrefix(_eachFile.getName()));
            if (mapInfo != null)
            {
                mapFolderInfo.allMapInfoList.add(mapInfo);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


}
