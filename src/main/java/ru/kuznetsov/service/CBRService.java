package ru.kuznetsov.service;

import ru.cbr.ArrayOfDouble;

import java.util.List;

public interface CBRService {
    ArrayOfDouble getInternalCodesFromBicCodes(List bicCodesList);
    List getCreditOrgInfoList(ArrayOfDouble internalCodes);
    void initCreditOrgInfoSoap();
}
