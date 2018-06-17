package old;

import com.google.gson.Gson;
import old.analyze.TiledMapFolderAnalyzeLogic;
import old.data.export.ExportMapFolderInfo;
import utils.FileUtils;

public class Main
{

    public static void main(String[] args)
    {
        TiledMapFolderAnalyzeLogic logic = new TiledMapFolderAnalyzeLogic();
        ExportMapFolderInfo folderInfo = logic.StartAnalyze(args[0]);

        Gson gson = new Gson();

        String json = gson.toJson(folderInfo);
        FileUtils.writeStringToFile(json, args[1]);

        System.out.println("Done , save file to " + args[1]);
    }
}
