package org.example.bsproject.dto.Param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceInfoParam {
    private Long id;

    private String title;

    private String content;

    private String extra;

//    //规定由Json中String转为的格式
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate date;

    public  boolean checkEmpty(){
        return this.getId()==null&&this.getTitle()==null;
    }
}
