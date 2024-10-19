package org.example.bsproject.dto.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserRepairInfoParam {
        private Long id;
        private String content;

        public  boolean isEmpty(){
           return Objects.isNull(id)||Objects.isNull(content)||content.isEmpty();
        }
}
