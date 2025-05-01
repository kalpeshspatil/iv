package com.iv.iv.dto;

import java.util.List;
import java.util.Map;

public class ExcelToolTemplateRequestDto {
    public String name;
    public List<String> columns;
    public List<FormulaDto> formulas;

    public static class FormulaDto {
        public String outputColumnName;
        public String formulaString;
        public Map<String, String> variableMappings;
    }
}
