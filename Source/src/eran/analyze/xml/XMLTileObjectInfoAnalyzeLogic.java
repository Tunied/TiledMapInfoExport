package eran.analyze.xml;

import eran.data.TiledDataRoot;
import eran.data.object.TileObjectEachProperty;
import eran.data.object.TileObjectInfo;
import org.dom4j.Element;

import java.util.Iterator;

public class XMLTileObjectInfoAnalyzeLogic {


    /**
     * 将给定TilesetInfo中的数据解析为对应的Java Object
     */
    public void AnalyzeTilesetInfo(Element _mapRootEl) {
        for (Iterator<Element> it = _mapRootEl.elementIterator("objectgroup"); it.hasNext(); ) {
            Element eachObjectGroup = it.next();
            String layerName = eachObjectGroup.attributeValue("name");
            for (Iterator<Element> objectIt = eachObjectGroup.elementIterator("object"); objectIt.hasNext(); ) {
                Element eachObject = objectIt.next();

                TileObjectInfo tileObjectInfo = new TileObjectInfo();
                tileObjectInfo.layer = layerName;
                tileObjectInfo.posX = Float.valueOf(eachObject.attributeValue("x"));
                tileObjectInfo.posY = Float.valueOf(eachObject.attributeValue("y"));

                Element propertiesListEl = eachObject.element("properties");
                //当前Object没有任何属性信息
                if (propertiesListEl == null) continue;

                for (Iterator<Element> propertyIT = propertiesListEl.elementIterator("property"); propertyIT.hasNext(); ) {
                    Element eachPropertyEL = propertyIT.next();
                    String name = eachPropertyEL.attributeValue("name");
                    String type = eachPropertyEL.attributeValue("type");
                    if (type == null) {
                        type = "string";
                    }
                    String value = eachPropertyEL.attributeValue("value");

                    TileObjectEachProperty eachProperty = new TileObjectEachProperty();
                    eachProperty.key = name;
                    eachProperty.keyType = type;
                    eachProperty.value = value;
                    tileObjectInfo.allPropertyList.add(eachProperty);
                }

                if (!tileObjectInfo.allPropertyList.isEmpty()) {
                    TiledDataRoot.GetInstance().allTileObjectInfoList.add(tileObjectInfo);
                }


            }
        }
    }
}
