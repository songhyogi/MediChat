package kr.spring.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.disease.vo.Response;



public class XUtil {
	@Value("${API.SSH.SecretKey}")
	static
	String serviceKey1;
	public static Response getCountList(HttpServletRequest request) throws JAXBException,IOException{

	 
		String key_apiURL = "http://apis.data.go.kr/1790387/vcninfo/getCondVcnCd?serviceKey=" + serviceKey1  ;
		
		Map<String,String> requestHeaders = 
				           new HashMap<String,String>();
		requestHeaders.put("X-Naver-Client-Id", "PPJDiC3BNDi3uGhs0FV3");
		requestHeaders.put("X-Naver-Client-Secret", "bVFWG1tBcn");
		HttpURLConnection con = null;
		try {
			URL url = new URL(key_apiURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
		
			BufferedReader lineReader = new BufferedReader(streamReader);
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
			String path = request.getServletContext().getRealPath("/upload")+"/myXml.xml";
			byte[] bytes = new byte[con.getInputStream().available()];
			String xml = responseBody.toString();
			bytes = xml.getBytes();
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(bytes);
			File f=new File(path);
			FileInputStream fis = new FileInputStream(f);
			
			
		    // String 형식의 xml을 Java Object인 Response로 변환
		    JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			    // When
		    Response xmlListTag = (Response) unmarshaller.unmarshal(fis);
			fis.close();
			return xmlListTag ;
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
		
		
	}
	
	
	@Value("${API.SSH.SecretKey2}")
	static
	String serviceKey2;
	@Value("${API.SSH.X-Naver-Client-Id2}")
	private static String apiKey1;
	@Value("${API.SSH.X-Naver-Client-Secret2}")
	private static String apiKey2;
	
	public static Response getDetailList(HttpServletRequest request,String selectCd) throws JAXBException,IOException{

		
		String vcnCd =  selectCd;
		String key_apiURL = "http://apis.data.go.kr/1790387/vcninfo/getVcnInfo?serviceKey=" + serviceKey2 +"&vcnCd="+vcnCd  ;
		
		Map<String,String> requestHeaders = 
				           new HashMap<String,String>();
		requestHeaders.put("X-Naver-Client-Id", apiKey1);
		requestHeaders.put("X-Naver-Client-Secret", apiKey2);
		HttpURLConnection con = null;
		try {
			URL url = new URL(key_apiURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
		
			BufferedReader lineReader = new BufferedReader(streamReader);
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
			String path = request.getServletContext().getRealPath("/upload")+"/myXml.xml";
			byte[] bytes = new byte[con.getInputStream().available()];
			String xml = responseBody.toString();
			bytes = xml.getBytes();
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(bytes);
			File f=new File(path);
			FileInputStream fis = new FileInputStream(f);
			
			
		    // String 형식의 xml을 Java Object인 Response로 변환
		    JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			    // When
		    Response xmlListTag = (Response) unmarshaller.unmarshal(fis);
			fis.close();
			return xmlListTag ;
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
		
		
	}
	
	public static Response getDiseaseDepart(HttpServletRequest request) throws JAXBException,IOException{

		String serviceKey =  serviceKey1 ;
	
		String key_apiURL = "https://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList?sickType=1&medTp=1&numOfRows=2065&ServiceKey=" + serviceKey;
		
		Map<String,String> requestHeaders = 
				           new HashMap<String,String>();
		requestHeaders.put("X-Naver-Client-Id", apiKey1);
		requestHeaders.put("X-Naver-Client-Secret", apiKey2);
		HttpURLConnection con = null;
		try {
			URL url = new URL(key_apiURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
		
			BufferedReader lineReader = new BufferedReader(streamReader);
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
			String path = request.getServletContext().getRealPath("/upload")+"/myXml.xml";
			byte[] bytes = new byte[con.getInputStream().available()];
			String xml = responseBody.toString();
			bytes = xml.getBytes();
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(bytes);
			File f=new File(path);
			FileInputStream fis = new FileInputStream(f);
			
			
		    // String 형식의 xml을 Java Object인 Response로 변환
		    JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			    // When
		    Response xmlListTag = (Response) unmarshaller.unmarshal(fis);
			fis.close();
			return xmlListTag ;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("API 요청과 응답 실패");
			
		} finally {
			con.disconnect();
		}
		
		
	}
}
