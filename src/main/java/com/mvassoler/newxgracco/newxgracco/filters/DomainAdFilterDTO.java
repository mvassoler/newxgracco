package com.mvassoler.newxgracco.newxgracco.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainAdFilterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7844114877847310882L;

    private List<String> ldapBaseDns;
    private List<String> ldapUrls;
    private List<String> ldapUserNames;
    private List<UUID> domainIds;
    private List<String> domainIdentificationNames;
    private List<String> domainIdentificationNumbers;

}
