package kr.spring.util;



import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.spring.disease.vo.MedicalofficeVO;

public class HospitalParser implements Parser<MedicalofficeVO>{

    @Override
    public MedicalofficeVO parse(String str) {
        String[] splitted = str.split(",");
        MedicalofficeVO hospital = new MedicalofficeVO();
        hospital.setDsbjt_cd(Integer.parseInt(splitted[8]));
        hospital.setMain_sick(splitted[9]);
       
        return hospital;
    }
}