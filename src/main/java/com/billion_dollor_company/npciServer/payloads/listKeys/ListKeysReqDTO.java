package com.billion_dollor_company.npciServer.payloads.listKeys;

import com.billion_dollor_company.npciServer.models.ListKeysInfo;
import com.billion_dollor_company.npciServer.models.projections.ListKeysInfoProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListKeysReqDTO  {


    private Integer id;

    private String publicKey;

    public ListKeysReqDTO(ListKeysInfoProjection projection) {
        this.id = projection.getId();
        this.publicKey = projection.getPublicKey();
    }
}
