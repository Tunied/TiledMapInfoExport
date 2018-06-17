package eran.analyze.xml;

import eran.data.TiledDataRoot;
import eran.data.tileset.TilesetEachTileEachProperty;
import eran.data.tileset.TilesetEachTileInfo;
import eran.data.tileset.TilesetInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;

public class XMLTilesetInfoAnalyzeLogic {

    /**
     * 将给定TilesetInfo中的数据解析为对应的Java Object
     */
    public void AnalyzeTilesetInfo(String _filePath, int _firstGID) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(_filePath);

            Element rootEl = document.getRootElement();

            TilesetInfo tilesetInfo = new TilesetInfo();
            tilesetInfo.name = rootEl.attributeValue("name");
            tilesetInfo.firstGID = _firstGID;


            for (Iterator<Element> it = rootEl.elementIterator("tile"); it.hasNext(); ) {
                TilesetEachTileInfo eachTileInfo = new TilesetEachTileInfo();

                Element eachTileEl = it.next();
                eachTileInfo.index = Integer.valueOf(eachTileEl.attributeValue("id"));

                Element propertiesListEl = eachTileEl.element("properties");
                //当前Tileset没有任何属性信息
                if (propertiesListEl == null) return;

                tilesetInfo.allTilesetEachTileList.add(eachTileInfo);

                for (Iterator<Element> propertyIT = propertiesListEl.elementIterator("property"); propertyIT.hasNext(); ) {
                    Element eachPropertyEL = propertyIT.next();
                    String name = eachPropertyEL.attributeValue("name");
                    String type = eachPropertyEL.attributeValue("type");
                    if (type == null) {
                        type = "string";
                    }
                    String value = eachPropertyEL.attributeValue("value");

                    TilesetEachTileEachProperty eachProperty = new TilesetEachTileEachProperty();
                    eachProperty.key = name;
                    eachProperty.keyType = type;
                    eachProperty.value = value;
                    eachTileInfo.allPropertyList.add(eachProperty);
                }
            }

            TiledDataRoot.GetInstance().allTilesetEachInfoList.add(tilesetInfo);


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
