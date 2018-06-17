package eran.analyze.xml;

import eran.analyze.json.JsonTilesetInfoAnalyzeLogic;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class XMLTileMapInfoAnalyzeLogic {

    private XMLTiledMapEachChunkAnalyzeLogic mChunkAnalyze;

    //根节点
    private Element mRootNode;
    private File mTileMapFile;

    public void AnalyzeTileMap(String _filePath) {
        try {

            mTileMapFile = new File(_filePath);

            SAXReader reader = new SAXReader();
            Document document = reader.read(_filePath);

            mRootNode = document.getRootElement();

            mChunkAnalyze = new XMLTiledMapEachChunkAnalyzeLogic();

            //分析TileMap
            boolean infinite = !mRootNode.attributeValue("infinite").equals("0");

            DoImportTilesetInfo();

            if (infinite) {
                //无尽模式
                DoAnalyzeAsInfiniteMap();
            } else {
                //Fix模式
                DoAnalyzeAsFixedMap();
            }

            System.out.println("Done analyze tile map: " + _filePath);
            System.out.println("");

            //分析ObjectGroup
            XMLTileObjectInfoAnalyzeLogic objectAnalyze = new XMLTileObjectInfoAnalyzeLogic();
            objectAnalyze.AnalyzeTilesetInfo(mRootNode);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将当前TileMap Json中所用到的TilesetInfo 读取出来
     */
    private void DoImportTilesetInfo() {
        for (Iterator<Element> it = mRootNode.elementIterator("tileset"); it.hasNext(); ) {
            Element eachTilesetEl = it.next();
            int firstGID = Integer.parseInt(eachTilesetEl.attributeValue("firstgid"));
            String subPath = eachTilesetEl.attributeValue("source");
            String fullPath = mTileMapFile.getParent() + File.separator + subPath;

            if (fullPath.endsWith(".json")) {
                JsonTilesetInfoAnalyzeLogic jsonAnalyzeLogic = new JsonTilesetInfoAnalyzeLogic();
                jsonAnalyzeLogic.AnalyzeTilesetInfo(fullPath, firstGID);
            } else if (fullPath.endsWith(".tsx")) {
                XMLTilesetInfoAnalyzeLogic xmlAnalyzeLogic = new XMLTilesetInfoAnalyzeLogic();
                xmlAnalyzeLogic.AnalyzeTilesetInfo(fullPath, firstGID);
            } else {
                System.out.println("Tileset format is not json or tsx ignore. Path:\n" + subPath);
            }
        }
    }


    private void DoAnalyzeAsInfiniteMap() {
        for (Iterator<Element> it = mRootNode.elementIterator("layer"); it.hasNext(); ) {
            Element eachLayerEl = it.next();
            String layerName = eachLayerEl.attributeValue("name");
            Element dataEl = eachLayerEl.element("data");


            for (Iterator<Element> chunkIT = dataEl.elementIterator("chunk"); chunkIT.hasNext(); ) {
                Element chunkEl = chunkIT.next();
                int startX = Integer.parseInt(chunkEl.attributeValue("x"));
                int startY = Integer.parseInt(chunkEl.attributeValue("y"));
                int width = Integer.parseInt(chunkEl.attributeValue("width"));
                mChunkAnalyze.AnalyzeTileMap(chunkEl, layerName, startX, startY, width);
            }

        }
    }

    private void DoAnalyzeAsFixedMap() {
        for (Iterator<Element> it = mRootNode.elementIterator("layer"); it.hasNext(); ) {
            Element eachLayerEl = it.next();
            String layerName = eachLayerEl.attributeValue("name");
            int width = Integer.parseInt(eachLayerEl.attributeValue("width"));
            mChunkAnalyze.AnalyzeTileMap(eachLayerEl, layerName, 0, 0, width);
        }
    }


}
