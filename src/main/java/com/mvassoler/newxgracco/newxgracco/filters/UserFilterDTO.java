package com.mvassoler.newxgracco.newxgracco.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 9031335952712640885L;

    private List<String> usernames;
    private List<String> emails;
    private List<String> firstNames;
    private List<String> lastNames;

}
