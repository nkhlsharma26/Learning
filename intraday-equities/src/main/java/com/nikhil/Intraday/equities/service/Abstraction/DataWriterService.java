package com.nikhil.Intraday.equities.service.Abstraction;

import java.util.List;

public interface DataWriterService {
    void writeData(List<String[]> data);
}
