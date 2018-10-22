package ru.kuznetsov.general;

import ru.kuznetsov.soap.ServiceResponse;

public interface GeneralService {
    ServiceResponse processingData(String stringXML);
}
