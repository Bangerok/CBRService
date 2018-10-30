package ru.kuznetsov.service.general;

import ru.kuznetsov.request.ServiceResponse;

public interface GeneralService {
    ServiceResponse processingData(String stringXML);
}
