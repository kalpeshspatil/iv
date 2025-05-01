package com.iv.iv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "iv_excel_tool_template_columns")
public class ExcelImportExportToolTemplateColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "column_name")
    private String columnName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "template_id")
    private ExcelImportExportToolTemplate excelImportExportToolTemplate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ExcelImportExportToolTemplate getExcelImportExportToolTemplate() {
        return excelImportExportToolTemplate;
    }

    public void setExcelImportExportToolTemplate(ExcelImportExportToolTemplate excelImportExportToolTemplate) {
        this.excelImportExportToolTemplate = excelImportExportToolTemplate;
    }
}
