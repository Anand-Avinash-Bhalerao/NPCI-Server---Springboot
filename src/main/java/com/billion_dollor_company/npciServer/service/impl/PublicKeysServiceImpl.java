package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.models.ListKeysInfo;
import com.billion_dollor_company.npciServer.models.projections.ListKeysInfoProjection;
import com.billion_dollor_company.npciServer.payloads.listKeys.ListKeysReqDTO;
import com.billion_dollor_company.npciServer.repository.ListKeysInfoRepository;
import com.billion_dollor_company.npciServer.service.interfaces.PublicKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PublicKeysServiceImpl implements PublicKeysService {

    @Autowired
    private ListKeysInfoRepository listKeysInfoRepository;
    @Override
    public List<ListKeysReqDTO> getListKeys() {
        System.out.println("Request reached service impl");
        List<ListKeysInfoProjection> keys =listKeysInfoRepository.getListKeys();
        List<ListKeysReqDTO> dtos = new ArrayList<>();
        for (ListKeysInfoProjection projection : keys) {
            ListKeysReqDTO dto = new ListKeysReqDTO(projection);
            dtos.add(dto);
        }

        return dtos;

    }


}
