package ru.kuznetsov.general;

import ru.kuznetsov.request.ServiceResponse;

import java.io.IOException;

public interface GeneralService {
    ServiceResponse processingData(String stringXML) throws IOException;
}
