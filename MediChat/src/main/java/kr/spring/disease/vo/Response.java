package kr.spring.disease.vo;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
@ToString
@XmlRootElement(name = "response")
public class Response {
	@Autowired
    private Header header;
	@Autowired
    private Body body;

   
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}






