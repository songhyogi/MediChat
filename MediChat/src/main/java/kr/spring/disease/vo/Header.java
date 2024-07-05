package kr.spring.disease.vo;

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
public class Header {
	 @XmlElement(name = "resultCode")
    private String resultCode;
	   @XmlElement(name = "resultMsg")
    private String resultMsg;

   
}