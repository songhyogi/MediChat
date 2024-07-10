package kr.spring.disease.service;



import kr.spring.disease.vo.MedicalofficeVO;
import kr.spring.util.ReadLineContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.spring.disease.dao.HospitalDao;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HospitalService {

    private final ReadLineContext<MedicalofficeVO> hospitalReadLineContext;

    private final HospitalDao hospitalDao;

    public HospitalService(ReadLineContext<MedicalofficeVO> hospitalReadLineContext, HospitalDao hospitalDao) {
        this.hospitalReadLineContext = hospitalReadLineContext;
        this.hospitalDao = hospitalDao;
    }

    @Transactional
    public int insertLargeVolumeHospitalData(String filename) {
        List<MedicalofficeVO> hospitalList;
        try {
            hospitalList = hospitalReadLineContext.readByLine(filename);
            System.out.println("파싱이 끝났습니다.");
            hospitalList.stream()
                    .parallel()
                    .forEach(hospital -> {
                        try {
                        		this.hospitalDao.insertH(hospital); // db에 insert하는 구간      
                            
                        } catch (Exception e) {
                            System.out.printf("id:%d 레코드에 문제가 있습니다.\n",hospital);
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!Optional.of(hospitalList).isEmpty()) {
            return hospitalList.size();
        } else {
            return 0;
        }
    }
}