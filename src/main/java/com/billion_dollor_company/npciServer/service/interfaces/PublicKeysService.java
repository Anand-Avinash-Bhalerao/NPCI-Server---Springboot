package com.billion_dollor_company.npciServer.service.interfaces;

import com.billion_dollor_company.npciServer.models.ListKeysInfo;
import com.billion_dollor_company.npciServer.payloads.listKeys.ListKeysReqDTO;

import java.util.List;

public interface PublicKeysService {
    public List<ListKeysReqDTO> getListKeys();
}
