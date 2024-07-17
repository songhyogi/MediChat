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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.disease.vo.Response;



public class XUtil {
	
	public static Response getCountList(HttpServletRequest request) throws JAXBException,IOException{

		String serviceKey = "bBcw2bsbnZbe4NBgjrT8%2BVhedqNa8T3RaUHbuxCtnu4X6kiDo4KVAyGMWXZRwklSJba6SMoFiKmJwzYUcex6Gw%3D%3D"; //키 발급시 0, 캡챠 이미지 비교시 1로 세팅
		String key_apiURL = "http://apis.data.go.kr/1790387/vcninfo/getCondVcnCd?serviceKey=" + serviceKey  ;
		
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
	
	
	
	
	
	public static Response getDetailList(HttpServletRequest request,String selectCd) throws JAXBException,IOException{

		String serviceKey = "bBcw2bsbnZbe4NBgjrT8%2BVhedqNa8T3RaUHbuxCtnu4X6kiDo4KVAyGMWXZRwklSJba6SMoFiKmJwzYUcex6Gw%3D%3D"; //키 발급시 0, 캡챠 이미지 비교시 1로 세팅
		String vcnCd =  selectCd;
		String key_apiURL = "http://apis.data.go.kr/1790387/vcninfo/getVcnInfo?serviceKey=" + serviceKey +"&vcnCd="+vcnCd  ;
		
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
	
	public static Response getDiseaseDepart(HttpServletRequest request) throws JAXBException,IOException{

		String serviceKey = "bBcw2bsbnZbe4NBgjrT8%2BVhedqNa8T3RaUHbuxCtnu4X6kiDo4KVAyGMWXZRwklSJba6SMoFiKmJwzYUcex6Gw%3D%3D"; //키 발급시 0, 캡챠 이미지 비교시 1로 세팅
	
		String key_apiURL = "https://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList?sickType=1&medTp=1&numOfRows=2065&ServiceKey=" + serviceKey;
		
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
			e.printStackTrace();
			throw new RuntimeException("API 요청과 응답 실패");
			
		} finally {
			con.disconnect();
		}
		
		
	}
}
