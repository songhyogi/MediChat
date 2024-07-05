package kr.spring.crawling;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


@Component
public class HospitalsCrawling {
	
	@Value("${API.KSY.DATA-API-KEY}")
	private String KSY_KEY; /* Service Key */
	
	final static String TYPE = "xml"; /* xml(기본값), JSON */
	final static String DUTY_DIV = "B"; /* 병원 타입 B:병원, C:의원 */
	final static String NUMSOFROWS = "100"; /* 한 페이지 결과 수 */
	
	/*
	 * static String pageNo = "1"; 페이지번호 
	 */
	
	public void main() {
		for(int i=15; i<16; i++) {
			System.out.println("<<PageNum>> : " + i);
			try {
				getHospitals(urlMaker(i));
				 // 3초의 딜레이 추가
	            try {
	                Thread.sleep(3000);
	            } catch (InterruptedException e) {
	                // 현재 스레드가 인터럽트되면, 인터럽트 상태를 복원하고 예외를 기록합니다.
	                Thread.currentThread().interrupt();
	                System.err.println("Thread was interrupted, failed to complete operation");
	            }
			} catch(Exception e) {
				System.exit(0);
				e.printStackTrace();
			}
		}
	}
	
	public String urlMaker(int pageNo) {
		String url = "";
		try {
			url += "https://safemap.go.kr/openApiService/data/getTotHospitalData.do";
			url += "?serviceKey=" + URLEncoder.encode(KSY_KEY,"UTF-8"); /* Service Key */
			url += "&pageNo=" + URLEncoder.encode(String.valueOf(pageNo),"UTF-8"); /* 페이지번호 */
			url += "&numOfRows=" + URLEncoder.encode(NUMSOFROWS,"UTF-8"); /* 한 페이지 결과 수 */
			url += "&type=" + URLEncoder.encode(TYPE,"UTF-8"); /* xml(기본값), JSON */
			url += "&DutyDiv=" + URLEncoder.encode(DUTY_DIV,"UTF-8"); /* 병원 타입 B:병원, C:의원 */
		} catch(Exception e) {
		}
		System.out.println("<<URL>> : " + url);
		return url;
	}

	public void getHospitals(String urlString) {
		try {
			URL url = new URL(urlString);
            
            // URL에서 XML 데이터를 읽어오기
            InputStream inputStream = url.openStream();
            
            /* & -> &amp 오류 수정 시작 */
            // InputStream을 BufferedReader로 변환
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringWriter writer = new StringWriter();
            String line;

            // 한 줄씩 읽어서 "&"를 "&amp;"로 바꾸기
            while ((line = reader.readLine()) != null) {
                writer.write(line.replace("&", "&amp;"));
            }

            // 수정된 XML 데이터를 문자열로 가져오기
            String fixedXml = writer.toString();

            // 문자열을 InputStream으로 변환
            InputStream fixedInputStream = new ByteArrayInputStream(fixedXml.getBytes());

            /* &오류 수정 끝 */
            
            // DocumentBuilderFactory를 사용하여 DocumentBuilder 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();

            // InputStream을 사용하여 Document 객체 생성
            Document document = builder.parse(fixedInputStream);
            document.getDocumentElement().normalize();
            
            // 루트 엘리먼트 출력
            System.out.println("Root element: " + document.getDocumentElement().getNodeName());
            
            // 특정 태그 이름을 가진 모든 요소를 가져오기 (예: <item>)
            NodeList nodeList = document.getElementsByTagName("item");
            
            // 모든 <item> 요소를 순회하며 데이터 출력
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                System.out.println("일련번호 " + (i+1) + ": " + getElementValue(element, "NUM"));
                System.out.println("주소 " + (i+1) + ": " + getElementValue(element, "DUTYADDR"));
                System.out.println("병원분류 " + (i+1) + ": " + getElementValue(element, "DUTYDIV"));
                System.out.println("병원분류명 " + (i+1) + ": " + getElementValue(element, "DUTYDIVNAME"));
                System.out.println("응급의료기관코드 " + (i+1) + ": " + getElementValue(element, "DUTYEMCLS"));
                System.out.println("응급의료기관코드명 " + (i+1) + ": " + getElementValue(element, "DUTYEMCLSNAME"));
                System.out.println("응급실운영여부(1/2) " + (i+1) + ": " + getElementValue(element, "DUTYERYN"));
                System.out.println("비고 " + (i+1) + ": " + getElementValue(element, "DUTYETC"));
                System.out.println("간이약도 " + (i+1) + ": " + getElementValue(element, "DUTYMAPIMG"));
                System.out.println("기관명 " + (i+1) + ": " + getElementValue(element, "DUTYNAME"));
                System.out.println("대표전화 " + (i+1) + ": " + getElementValue(element, "DUTYTEL1"));
                System.out.println("응급실전화 " + (i+1) + ": " + getElementValue(element, "DUTYTEL3"));
                System.out.println("진료시간(월요일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME1C"));
                System.out.println("진료시간(화요일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME2C"));
                System.out.println("진료시간(수요일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME3C"));
                System.out.println("진료시간(목요일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME4C"));
                System.out.println("진료시간(금요일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME5C"));
                System.out.println("진료시간(토요일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME6C"));
                System.out.println("진료시간(일요일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME7C"));
                System.out.println("진료시간(공휴일)C " + (i+1) + ": " + getElementValue(element, "DUTYTIME8C"));
                System.out.println("진료시간(월요일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME1S"));
                System.out.println("진료시간(화요일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME2S"));
                System.out.println("진료시간(수요일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME3S"));
                System.out.println("진료시간(목요일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME4S"));
                System.out.println("진료시간(금요일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME5S"));
                System.out.println("진료시간(토요일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME6S"));
                System.out.println("진료시간(일요일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME7S"));
                System.out.println("진료시간(공휴일)S " + (i+1) + ": " + getElementValue(element, "DUTYTIME8S"));
                System.out.println("기관ID " + (i+1) + ": " + getElementValue(element, "HPID"));
                System.out.println("우편번호1 " + (i+1) + ": " + getElementValue(element, "POSTCDN1"));
                System.out.println("우편번호2 " + (i+1) + ": " + getElementValue(element, "POSTCDN2"));
                System.out.println("기관설명상세 " + (i+1) + ": " + getElementValue(element, "DUTYINF"));
                System.out.println("경도 " + (i+1) + ": " + getElementValue(element, "LON"));
                System.out.println("위도 " + (i+1) + ": " + getElementValue(element, "LAT"));
                System.out.println("X좌표 " + (i+1) + ": " + getElementValue(element, "X"));
                System.out.println("Y좌표 " + (i+1) + ": " + getElementValue(element, "Y"));
                System.out.println("주말진료여부 " + (i+1) + ": " + getElementValue(element, "DUTYWEEKENDAT"));
                System.out.println("---------------------------------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	// 특정 태그의 값을 가져오는 헬퍼 메소드
    public String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
