package com.iv.iv.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "iv_excel_tool_template")
public class ExcelImportExportToolTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "excelImportExportToolTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExcelImportExportToolTemplateColumn> columns;

    @OneToMany(mappedBy = "excelImportExportToolTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExcelImportExportToolTemplateFormula> formulas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExcelImportExportToolTemplateColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ExcelImportExportToolTemplateColumn> columns) {
        this.columns = columns;
    }

    public List<ExcelImportExportToolTemplateFormula> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<ExcelImportExportToolTemplateFormula> formulas) {
        this.formulas = formulas;
    }
// Getters and Setters
}
