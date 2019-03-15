package com.next.jiangzh.film.example.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterUserBO {

    private String uuid;
    private String username;
    private String userpwd;

}
