package kr.spring.disease.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
	
	@XmlElement(name = "item",required = false)
	private List<Item> iList;
   
}