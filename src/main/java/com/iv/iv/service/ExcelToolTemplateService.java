package com.iv.iv.service;

import com.iv.iv.dto.ExcelToolTemplateRequestDto;
import com.iv.iv.entity.ExcelImportExportToolTemplate;
import com.iv.iv.entity.ExcelImportExportToolTemplateColumn;
import com.iv.iv.entity.ExcelImportExportToolTemplateFormula;
import com.iv.iv.repository.ExcelToolTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelToolTemplateService {
    @Autowired
    private ExcelToolTemplateRepository templateRepository;
    public ExcelImportExportToolTemplate saveExcelImportExportToolTemplate(ExcelToolTemplateRequestDto dto) {
        ExcelImportExportToolTemplate template = new ExcelImportExportToolTemplate();
        template.setName(dto.name);

        List<ExcelImportExportToolTemplateColumn> columns = dto.columns.stream()
                .map(colName -> {
                    ExcelImportExportToolTemplateColumn col = new ExcelImportExportToolTemplateColumn();
                    col.setColumnName(colName);
                    col.setExcelImportExportToolTemplate(template);
                    return col;
                }).collect(Collectors.toList());

        List<ExcelImportExportToolTemplateFormula> formulas = dto.formulas.stream()
                .map(f -> {
                    ExcelImportExportToolTemplateFormula formula = new ExcelImportExportToolTemplateFormula();
                    formula.setOutputColumnName(f.outputColumnName);
                    formula.setFormulaString(f.formulaString);
                    formula.setVariableMappings(f.variableMappings);
                    formula.setExcelImportExportToolTemplate(template);
                    return formula;
                }).collect(Collectors.toList());

        template.setColumns(columns);
        template.setFormulas(formulas);

        return templateRepository.save(template);
    }

    public List<ExcelImportExportToolTemplate> getAllExcelImportExportToolTemplate() {
        return (List<ExcelImportExportToolTemplate>) templateRepository.findAll();
    }
}
