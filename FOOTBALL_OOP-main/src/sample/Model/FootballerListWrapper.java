package sample.Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "footballers")
public class FootballerListWrapper {
    private List<Footballer> footballers;

    @XmlElement(name = "footballer")
    public List<Footballer> getFootballers() {
        return footballers;
    }

    public void setFootballers(List<Footballer> footballers) {
        this.footballers = footballers;
    }
}
