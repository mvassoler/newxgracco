package com.mvassoler.newxgracco.newxgracco.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomainAdDto {

    private String ldapUrl;
    private String ldapBaseDn;

}
