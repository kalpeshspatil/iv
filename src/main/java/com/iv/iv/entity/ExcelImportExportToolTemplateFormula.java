package com.iv.iv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Map;

@Entity
@Table(name = "iv_excel_tool_template_formulas")
public class ExcelImportExportToolTemplateFormula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "output_column_name")
    private String outputColumnName;

    @Column(name = "formula_string")
    private String formulaString;

    @ElementCollection
    @CollectionTable(name = "formula_variable_map", joinColumns = @JoinColumn(name = "formula_id"))
    @MapKeyColumn(name = "variable")
    @Column(name = "column_name")
    private Map<String, String> variableMappings; // e.g., {"A":"Price", "B":"Cost"}

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "template_id")
    private ExcelImportExportToolTemplate excelImportExportToolTemplate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputColumnName() {
        return outputColumnName;
    }

    public void setOutputColumnName(String outputColumnName) {
        this.outputColumnName = outputColumnName;
    }

    public String getFormulaString() {
        return formulaString;
    }

    public void setFormulaString(String formulaString) {
        this.formulaString = formulaString;
    }

    public Map<String, String> getVariableMappings() {
        return variableMappings;
    }

    public void setVariableMappings(Map<String, String> variableMappings) {
        this.variableMappings = variableMappings;
    }

    public ExcelImportExportToolTemplate getExcelImportExportToolTemplate() {
        return excelImportExportToolTemplate;
    }

    public void setExcelImportExportToolTemplate(ExcelImportExportToolTemplate excelImportExportToolTemplate) {
        this.excelImportExportToolTemplate = excelImportExportToolTemplate;
    }
}
