package sample.Model.Beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "listCollections")
public class ListCollectionWrapper {
        private List<ListCollection> listCollection;

        @XmlElement(name = "listCollection")
        public List<ListCollection> getListCollection() {
            return listCollection;
        }

        public void setListCollection(List<ListCollection> listCollection) {
            this.listCollection = listCollection;
        }
}
