package kr.spring.disease.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
	 @XmlElement(name = "dataTime",defaultValue="")
    private String dataTime;
	    @XmlElement(name = "pageNo",defaultValue="")
    private int pageNo;
	    @XmlElement(name = "numOfRows",defaultValue="0")
    private int numOfRows;
	    @XmlElement(name = "totalCount",defaultValue="0")
    private int totalCount;
    @XmlElement(name = "items")
    private Items itemList;

   
    
    
    
}