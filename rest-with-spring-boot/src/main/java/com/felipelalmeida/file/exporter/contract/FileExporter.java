package com.felipelalmeida.file.exporter.contract;

import com.felipelalmeida.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> people) throws Exception;
    Resource exportPerson(PersonDTO person) throws Exception;
}
