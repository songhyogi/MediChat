package kr.spring.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.spring.disease.vo.MedicalofficeVO;

@Configuration
public class ParserFactory {
    @Bean
    public ReadLineContext<MedicalofficeVO> mReadLineContextest(){
        return new ReadLineContext<MedicalofficeVO>(new HospitalParser());
    }
}