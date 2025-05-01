package com.iv.iv.dto;

import java.util.List;

public class ExcelToolTemplateResponseDTO {
    public String name;
    public List<String> columns;
    public List<ExcelToolTemplateRequestDto.FormulaDto> formulas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<ExcelToolTemplateRequestDto.FormulaDto> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<ExcelToolTemplateRequestDto.FormulaDto> formulas) {
        this.formulas = formulas;
    }
}
