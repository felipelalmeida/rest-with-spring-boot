package com.felipelalmeida.file.importer.impl;

import com.felipelalmeida.data.dto.PersonDTO;
import com.felipelalmeida.file.importer.contract.FileImporter;

import java.io.InputStream;
import java.util.List;

public class CsvImporter implements FileImporter {
    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
