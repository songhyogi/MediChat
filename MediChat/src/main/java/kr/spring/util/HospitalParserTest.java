package kr.spring.util;



import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;

import kr.spring.disease.dao.HospitalDao;
import kr.spring.disease.service.HospitalService;
import kr.spring.disease.vo.MedicalofficeVO;


@SpringBatchTest
class HospitalParserTest {

    @Autowired
    ReadLineContext<MedicalofficeVO> hospitalReadLineContext;

    @Autowired
    HospitalDao hospitalDao;

    @Autowired
    HospitalService hospitalService;
    
 

}