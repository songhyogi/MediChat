package kr.spring.disease.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlElement(name = "title",defaultValue="")
    private String title;
    @XmlElement(name = "message",defaultValue="")
    private String message;
    @XmlElement(name = "srchCd",defaultValue="")
    private String srchCd;
    @XmlElement(name = "srchCdCmmt",defaultValue="")
    private String srchCdCmmt;
    @XmlElement(name = "srchCdNm",defaultValue="")
    private String srchCdNm;
    @XmlElement(name = "sickCd",defaultValue="")
    private String sickcd;
    @XmlElement(name = "sickNm",defaultValue="")
    private String sicknm;
    @XmlElement(name = "description",defaultValue="")
    private String description;
}