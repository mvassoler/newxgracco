package com.mvassoler.newxgracco.newxgracco.filters;

import com.mvassoler.newxgracco.newxgracco.enums.PersonalType;
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
public class DomainFilterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6014415589218794811L;

    private List<String> names;
    private List<String> identificationNumbers;
    private Boolean enabled;
    private PersonalType personalType;
}
